package cat.itacademy.s05.t01.S05T01.Repositories;

import cat.itacademy.s05.t01.S05T01.Models.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
}