package cat.itacademy.s05.t01.S05T01.Services.Player;

import cat.itacademy.s05.t01.S05T01.Models.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {
    Flux<Player> getRanking();
    Mono<Player> updatePlayerName(String playerId, String newName);
}