package cat.itacademy.s05.t01.n01.Models;

import cat.itacademy.s05.t01.n01.Models.*;
import cat.itacademy.s05.t01.n01.repository.GameRepository;
import cat.itacademy.s05.t01.n01.service.Player.PlayerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

import java.util.List;

public class HandleActions {
    private static final Logger logger = LogManager.getLogger(HandleActions.class);
    private static final String STATUS_LOSE = "LOSE";
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_BET_PLACED = "BET_PLACED";

    private final GameRepository gameRepository;
    private final PlayerService playerService;

    public HandleActions(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    public Mono<Game> handleAction(Game game, String action, double betAmount) {
        Deck deck = new Deck();
        switch (action.toUpperCase()) {
            case "BET":
                return handleBetAction(game, betAmount);
            case "HIT":
                return handleHitAction(game, deck);
            case "STAND":
                return handleStandAction(game, deck);
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    private Mono<Game> handleBetAction(Game game, double betAmount) {
        game.setBetAmount(betAmount);
        game.setStatus(STATUS_BET_PLACED);
        return gameRepository.save(game);
    }

    private Mono<Game> handleHitAction(Game game, Deck deck) {
        game.getPlayerCards().add(deck.drawCard());
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        logger.info("Player hand value: {}", playerHandValue);
        if (playerHandValue > 21) {
            game.setStatus(STATUS_LOSE);
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        } else {
            game.setStatus(STATUS_IN_PROGRESS);
        }
        return gameRepository.save(game);
    }

    private Mono<Game> handleStandAction(Game game, Deck deck) {
        do {
            Card drawnCard = deck.drawCard();
            game.getDealerCards().add(drawnCard);
            logger.info("Dealer draws card: {} of {}", drawnCard.getRank(), drawnCard.getSuit());
        } while (shouldDealerDraw(game.getDealerCards()));
        return updateGameStatus(game);
    }

    private boolean shouldDealerDraw(List<Card> dealerCards) {
        int handValue = CardUtils.calculateHandValue(dealerCards);
        return handValue < 17;
    }

    private Mono<Game> updateGameStatus(Game game) {
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        int dealerHandValue = CardUtils.calculateHandValue(game.getDealerCards());
        logger.info("Player hand value: {}", playerHandValue);
        logger.info("Dealer hand value: {}", dealerHandValue);
        if (playerHandValue > 21) {
            game.setStatus(STATUS_LOSE);
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        } else if (playerHandValue == 21 || dealerHandValue > 21 || playerHandValue > dealerHandValue) {
            game.setStatus("WIN");
            updatePlayerScore(game.getPlayerName(), game.getBetAmount());
        } else if (playerHandValue < dealerHandValue) {
            game.setStatus(STATUS_LOSE);
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        } else {
            game.setStatus("TIE");
        }
        return gameRepository.save(game);
    }

    private void updatePlayerScore(String playerName, double score) {
        playerService.createNewPlayer(playerName)
                .flatMap(player -> playerService.updatePlayerScore(player, score))
                .subscribe();
    }
}