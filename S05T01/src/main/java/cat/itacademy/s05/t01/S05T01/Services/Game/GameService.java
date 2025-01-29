package cat.itacademy.s05.t01.S05T01.Services.Game;

import cat.itacademy.s05.t01.S05T01.Models.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService {
    Mono<Game> createGame(String playerName);
    Mono<Game> getGameDetails(String gameId);
    Mono<Game> playGame(String gameId, String playData);
    Mono<Void> deleteGame(String gameId);
}