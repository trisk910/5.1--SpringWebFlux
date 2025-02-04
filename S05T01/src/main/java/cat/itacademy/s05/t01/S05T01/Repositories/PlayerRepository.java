package cat.itacademy.s05.t01.S05T01.Repositories;

import cat.itacademy.s05.t01.S05T01.Models.Player;
import org.springframework.data.r2dbc.repository.R2dbcRepository;


public interface PlayerRepository extends R2dbcRepository<Player, String> {
}