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
    private static final Logger logger = LogManager.getLogger(GameServiceImpl.class);

    @Autowired
    private GameRepository gameRepository;
    private HandleActions handleActions;
    @Autowired
    private PlayerService playerService;


    @Autowired
    public void setHandleActions(GameRepository gameRepository, PlayerService playerService) {
        this.handleActions = new HandleActions(gameRepository, playerService);
    }

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
                    if (isGameOver(game)) {
                        return Mono.just(game);
                    }
                    return handleActions.handleAction(game, action, betAmount);
                });
    }

    private boolean isGameOver(Game game) {
        return "LOSE".equals(game.getStatus()) || "WIN".equals(game.getStatus());
    }

    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepository.deleteById(id);
    }
}