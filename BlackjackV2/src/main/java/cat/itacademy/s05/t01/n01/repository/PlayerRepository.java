package cat.itacademy.s05.t01.n01.repository;

import cat.itacademy.s05.t01.n01.Models.Player;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, Integer> {
    @Query("SELECT * FROM player WHERE name = :name LIMIT 1")
    Mono<Player> findFirstByName(String name);
}
