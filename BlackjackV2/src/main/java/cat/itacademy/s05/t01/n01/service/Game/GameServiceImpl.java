package cat.itacademy.s05.t01.n01.service.Game;
import cat.itacademy.s05.t01.n01.Models.*;
import cat.itacademy.s05.t01.n01.repository.GameRepository;
import cat.itacademy.s05.t01.n01.service.Player.PlayerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    private static Logger logger = LogManager.getLogger(GameServiceImpl.class);
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerService playerService;

    private static final int dealerSoft = 17;


    @Override
    public Mono<Game> createGame(String playerName) {
        logger.info("Creating new game for player: {}", playerName);
        Game game = new Game(playerName);
        Deck deck = new Deck();
        game.getPlayerCards().add(deck.drawCard());
        game.getPlayerCards().add(deck.drawCard());
        game.getDealerCards().add(deck.drawCard());
        return playerService.createNewPlayer(playerName)
                .flatMap(player -> {
                    player.setGamesPlayed(player.getGamesPlayed() + 1);
                    return playerService.updatePlayer(player);
                })
                .then(gameRepository.save(game));
    }

    @Override
    public Mono<Game> getGame(String id) {
        return gameRepository.findById(id);
    }

    @Override
    public Mono<Game> playGame(String id, String action, double betAmount) {
        logger.info("Playing game with Action: {}, betAmount: {}", action, betAmount);
        return gameRepository.findById(id)
                .flatMap(game -> {
                    if (isGameOver(game))
                        return Mono.just(game);
                    return handleAction(game, action, betAmount);
                });
    }

    private boolean isGameOver(Game game) {
        return "LOOSE".equals(game.getStatus()) || "WIN".equals(game.getStatus());
    }

    private Mono<Game> handleAction(Game game, String action, double betAmount) {
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
        game.setStatus("BET_PLACED");
        return gameRepository.save(game);
    }

    private Mono<Game> handleHitAction(Game game, Deck deck) {
        game.getPlayerCards().add(deck.drawCard());
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        logger.info("Player hand value: {}", playerHandValue);
        if (playerHandValue > 21) {
            game.setStatus("LOSE");
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        }else {
            game.setStatus("IN_PROGRESS");
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

    private Mono<Game> updateGameStatus(Game game) {
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        int dealerHandValue = CardUtils.calculateHandValue(game.getDealerCards());
        logger.info("Player hand value: {}", playerHandValue);
        logger.info("Dealer hand value: {}", dealerHandValue);
        if (playerHandValue > 21) {
            game.setStatus("LOSE");
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        } else if (playerHandValue == 21 || dealerHandValue > 21 || playerHandValue > dealerHandValue) {
            game.setStatus("WIN");
            updatePlayerScore(game.getPlayerName(), game.getBetAmount());
        } else if (playerHandValue < dealerHandValue) {
            game.setStatus("LOSE");
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        } else {
            game.setStatus("TIE");
        }
        return gameRepository.save(game);
    }

    private boolean shouldDealerDraw(List<Card> dealerCards) {
        int handValue = CardUtils.calculateHandValue(dealerCards);
        return handValue < dealerSoft;
    }

    private void updatePlayerScore(String playerName, double score) {
        playerService.createNewPlayer(playerName)
                .flatMap(player -> playerService.updatePlayerScore(player, score))
                .subscribe();
    }

    /*private Mono<Game> handleAction(Game game, String action, double betAmount) {
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
        game.setStatus("BET_PLACED");
        return gameRepository.save(game);
    }

    private Mono<Game> handleHitAction(Game game, Deck deck) {
        game.getPlayerCards().add(deck.drawCard());
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        if (playerHandValue > 21) {
            game.setStatus("LOSE");
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        } else if (playerHandValue == 21) {
            game.setStatus("WIN");
            updatePlayerScore(game.getPlayerName(), game.getBetAmount());
        } else {
            game.setStatus("IN_PROGRESS");
        }
        return gameRepository.save(game);
    }

    private Mono<Game> handleStandAction(Game game, Deck deck) {
        do {
            game.getDealerCards().add(deck.drawCard());
        } while (shouldDealerDraw(game.getDealerCards()));
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        int dealerHandValue = CardUtils.calculateHandValue(game.getDealerCards());
        if (dealerHandValue > 21 || playerHandValue > dealerHandValue) {
            game.setStatus("WIN");
            updatePlayerScore(game.getPlayerName(), game.getBetAmount());
        } else if (playerHandValue < dealerHandValue) {
            game.setStatus("LOSE");
            updatePlayerScore(game.getPlayerName(), -game.getBetAmount());
        } else {
            game.setStatus("TIE");
        }
        return gameRepository.save(game);
    }

    private boolean shouldDealerDraw(List<Card> dealerCards) {
        int handValue = CardUtils.calculateHandValue(dealerCards);
        return handValue < dealerSoft;
    }

    private void updatePlayerScore(String playerName, double score) {
        playerService.createNewPlayer(playerName)
                .flatMap(player -> playerService.updatePlayerScore(player, score))
                .subscribe();
    }*/


    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepository.deleteById(id);
    }
}