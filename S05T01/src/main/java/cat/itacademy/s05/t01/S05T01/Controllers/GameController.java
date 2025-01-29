package cat.itacademy.s05.t01.S05T01.Controllers;

import cat.itacademy.s05.t01.S05T01.Models.Game;
import cat.itacademy.s05.t01.S05T01.Services.Game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/new")
    public Mono<Game> createGame(@RequestBody String playerName) {
        return gameService.createGame(playerName);
    }

    @GetMapping("/{id}")
    public Mono<Game> getGameDetails(@PathVariable String id) {
        return gameService.getGameDetails(id);
    }

    @PostMapping("/{id}/play")
    public Mono<Game> playGame(@PathVariable String id, @RequestBody String playData) {
        return gameService.playGame(id, playData);
    }

    @DeleteMapping("/{id}/delete")
    public Mono<Void> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }
}