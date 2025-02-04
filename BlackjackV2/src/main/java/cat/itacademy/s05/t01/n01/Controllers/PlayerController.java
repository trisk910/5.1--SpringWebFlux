package cat.itacademy.s05.t01.n01.Controllers;

import cat.itacademy.s05.t01.n01.Models.Player;
import cat.itacademy.s05.t01.n01.service.Player.PlayerServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    private PlayerServiceImpl playerService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player name successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request in updating player name"),
    })
    @PutMapping(value = {"/player/{playerId}"})
    public Mono<ResponseEntity<Player>> updatePlayerName(@Parameter(description = "Enter player id") @PathVariable Integer playerId,
                                                         @RequestBody @Schema(description = "Enter new player name") String newName){
        return playerService.updatePlayerName(playerId, newName).map(player ->
                ResponseEntity.status(HttpStatus.OK).body(player));
    }

    @GetMapping(value = {"/ranking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking of players successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request in retrieving player list"),
    })
    public Mono<ResponseEntity<List<Player>>> getPlayersRanking(){
        return playerService.getPlayersSorted().collectList().map(players ->
                ResponseEntity.status(HttpStatus.OK).body(players));
    }
}
