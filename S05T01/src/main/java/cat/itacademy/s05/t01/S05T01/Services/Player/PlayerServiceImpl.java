package cat.itacademy.s05.t01.S05T01.Services.Player;

import cat.itacademy.s05.t01.S05T01.Models.Player;
import cat.itacademy.s05.t01.S05T01.Repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Flux<Player> getRanking() {
        return playerRepository.findAll()
                .sort((p1, p2) -> Double.compare(p2.getScore(), p1.getScore()));
    }

    @Override
    public Mono<Player> updatePlayerName(String playerId, String newName) {
        return playerRepository.findById(playerId)
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepository.save(player);
                });
    }
}