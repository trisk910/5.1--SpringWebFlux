package cat.itacademy.s05.t01.n01.service.Game;

/*
public interface GameService {
    Mono<Game> createGame(String name);
    Mono<Game> getGame(String id);
    Mono<Void> deleteGame(String gameId);
    Mono<Game> playGame(String gameId, Action action, int betAmount);
    Mono<Game> updatePlayerName(String gameId, Player player);
}
*/
import cat.itacademy.s05.t01.n01.Models.Game;
import reactor.core.publisher.Mono;

public interface GameService {
    Mono<Game> createGame(String playerName);
    Mono<Game> getGame(String id);
    Mono<Game> playGame(String id, String action, double betAmount);
    Mono<Void> deleteGame(String id);
}