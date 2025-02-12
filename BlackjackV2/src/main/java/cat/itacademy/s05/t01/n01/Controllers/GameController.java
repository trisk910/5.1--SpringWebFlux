package cat.itacademy.s05.t01.n01.Controllers;

    import cat.itacademy.s05.t01.n01.Models.Game;
    import cat.itacademy.s05.t01.n01.Models.GameAction;
    import cat.itacademy.s05.t01.n01.service.Game.GameService;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;
    import io.swagger.v3.oas.annotations.media.Schema;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import reactor.core.publisher.Mono;

    @RestController
    @RequestMapping("/game")
    public class GameController {

        @Autowired
        private GameService gameService;

       @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Game successfully created"),
                @ApiResponse(responseCode = "400", description = "Bad request, error while creating the game"),
        })
        @PostMapping("/new")
        public Mono<ResponseEntity<Game>> createNewGame(
                @Schema(description = "Enter new player name", example = "Marc")
                @RequestBody String playerName) {
            return gameService.createGame(playerName)
                    .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
        }

        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Game info successfully retrieved"),
                @ApiResponse(responseCode = "400", description = "Bad request, cannot retrieve game info"),
        })
        @GetMapping("/{id}")
        public Mono<ResponseEntity<Game>> getGameInfo(@PathVariable String id) {
            return gameService.getGame(id)
                    .map(game -> ResponseEntity.status(HttpStatus.OK).body(game));
        }

       @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Game successfully updated"),
                @ApiResponse(responseCode = "400", description = "Bad request in performing the action"),
                @ApiResponse(responseCode = "409", description = "Existing conflict in the state of the game"),
        })
        @PostMapping("/{id}/play")
        public Mono<ResponseEntity<Game>> playGame(@PathVariable String id,
                                                   @Valid @RequestBody GameAction gameAction) {
            return gameService.playGame(id, gameAction.getAction(), gameAction.getBetAmount())
                    .map(game -> ResponseEntity.status(HttpStatus.OK).body(game));
        }

        @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Game successfully deleted"),
                @ApiResponse(responseCode = "400", description = "Bad request in deleting the game"),
        })
        @DeleteMapping("/{id}/delete")
        public Mono<ResponseEntity<Void>> deleteGame(@PathVariable String id) {
            return gameService.deleteGame(id).thenReturn(ResponseEntity.noContent().build());
        }
    }