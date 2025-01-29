package cat.itacademy.s05.t01.S05T01.Controllers;

import cat.itacademy.s05.t01.S05T01.Models.Player;
import cat.itacademy.s05.t01.S05T01.Services.Player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/ranking")
    public Flux<Player> getRanking() {
        return playerService.getRanking();
    }

    @PutMapping("/{playerId}")
    public Mono<Player> updatePlayerName(@PathVariable String playerId, @RequestBody String newName) {
        return playerService.updatePlayerName(playerId, newName);
    }
}