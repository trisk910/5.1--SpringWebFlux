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
        game.getDealerCards().add(deck.drawCard());
        game.setStatus("IN_PROGRESS");
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        if (playerHandValue > 21) {
            game.setStatus("LOOSE");
            updatePlayerScore(game.getPlayerName(), 0);
        } else if (playerHandValue == 21) {
            game.setStatus("WIN");
            updatePlayerScore(game.getPlayerName(), 1);
        }
        return gameRepository.save(game);
    }

    /*private Mono<Game> handleStandAction(Game game, Deck deck) {
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        do
            game.getDealerCards().add(deck.drawCard());
        while (CardUtils.calculateHandValue(game.getDealerCards()) < 17);
        int dealerHandValue = CardUtils.calculateHandValue(game.getDealerCards());
        if (playerHandValue > 21) {
            game.setStatus("LOOSE");
            updatePlayerScore(game.getPlayerName(), -1);
        } else if (dealerHandValue > 21 || playerHandValue > dealerHandValue) {
            game.setStatus("WIN");
            updatePlayerScore(game.getPlayerName(), 1);
        } else if (playerHandValue < dealerHandValue) {
            game.setStatus("LOOSE");
            updatePlayerScore(game.getPlayerName(), -1);
        } else {
            game.setStatus("TIE");
        }
        return gameRepository.save(game);
    }*/

    private Mono<Game> handleStandAction(Game game, Deck deck) {
        int playerHandValue = CardUtils.calculateHandValue(game.getPlayerCards());
        do {
            game.getDealerCards().add(deck.drawCard());
        }while (shouldDealerDraw(game.getDealerCards()));
        int dealerHandValue = CardUtils.calculateHandValue(game.getDealerCards());
        if (playerHandValue > 21) {
            game.setStatus("LOOSE");
            updatePlayerScore(game.getPlayerName(), 0);
        } else if (dealerHandValue > 21 || playerHandValue > dealerHandValue) {
            game.setStatus("WIN");
            updatePlayerScore(game.getPlayerName(), 1);
        } else if (playerHandValue < dealerHandValue) {
            game.setStatus("LOOSE");
            updatePlayerScore(game.getPlayerName(), 0);
        } else {
            game.setStatus("TIE");
        }
        return gameRepository.save(game);
    }

    private boolean shouldDealerDraw(List<Card> dealerCards) {
        int handValue = CardUtils.calculateHandValue(dealerCards);
        boolean hasSoft17 = handValue == 17 && dealerCards.stream().anyMatch(card -> card.getRank().equals("ACE"));
        return handValue < 17 || hasSoft17;
    }

    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepository.deleteById(id);
    }

    private void updatePlayerScore(String playerName, int score) {
        playerService.createNewPlayer(playerName)
                .flatMap(player -> playerService.updatePlayerScore(player, score))
                .subscribe();
    }
}