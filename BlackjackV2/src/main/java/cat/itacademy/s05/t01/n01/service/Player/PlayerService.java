package cat.itacademy.s05.t01.n01.service.Player;

import cat.itacademy.s05.t01.n01.Models.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {
    Mono<Player> save(Player player);
    Flux<Player> getPlayersSorted();
    Mono<Player> updatePlayerName(int id, String newName);
    Mono<Player> updatePlayerScore(Player player, double prizeAmount);
    Mono<Player> createNewPlayer(String name);
    Mono<Player> findPlayerById(int id);
}
