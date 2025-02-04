package cat.itacademy.s05.t01.n01.Controllers;

/*
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameServiceImpl gameService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request, error while creating the game"),
    })
    @PostMapping("/new")
    public Mono<ResponseEntity<Game>> createNewGame(
            @Schema(description = "Enter new player name", example = "Juan")
            @RequestBody String playerName) {
        return gameService.createGame(playerName)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request in performing the action"),
            @ApiResponse(responseCode = "409", description = "Existing conflict in the state of the game"),

    })

    @PostMapping("/{id}/play")
    Mono<ResponseEntity<Game>> playGame(@PathVariable String id,
                                        @Valid @RequestBody GameAction gameAction){
        return gameService.playGame(id, gameAction.getAction(), gameAction.getBetAmount())
                .map(game -> ResponseEntity.status(HttpStatus.OK).body(game));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game info successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request, cannot retrieve game info"),
    })
    @GetMapping("/{id}")
    Mono<ResponseEntity<Game>> getGameInfo(@PathVariable String id) {
        return gameService.getGame(id)
                .map(game -> ResponseEntity.status(HttpStatus.OK).body(game));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request in deleting the game"),
    })
    @DeleteMapping("/{id}/delete")
    Mono<ResponseEntity<Void>> deleteGame(@PathVariable String id){
        return gameService.deleteGame(id).thenReturn(ResponseEntity.noContent().build());
    }
}*/
