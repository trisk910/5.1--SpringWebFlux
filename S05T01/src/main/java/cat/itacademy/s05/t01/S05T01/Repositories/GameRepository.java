package cat.itacademy.s05.t01.S05T01.Repositories;

import cat.itacademy.s05.t01.S05T01.Models.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
}