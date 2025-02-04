package cat.itacademy.s05.t01.S05T01.Services.Game;

import cat.itacademy.s05.t01.S05T01.Models.Game;
import cat.itacademy.s05.t01.S05T01.Repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Mono<Game> createGame(String playerName) {
        Game game = new Game();
        game.setPlayerName(playerName);

        return gameRepository.save(game);
    }

    @Override
    public Mono<Game> getGameDetails(String gameId) {
        return gameRepository.findById(gameId);
    }

    @Override
    public Mono<Game> playGame(String gameId, String playData) {
        return gameRepository.findById(gameId)
                .flatMap(game -> {

                    return gameRepository.save(game);
                });
    }

    @Override
    public Mono<Void> deleteGame(String gameId) {
        return gameRepository.deleteById(gameId);
    }
}