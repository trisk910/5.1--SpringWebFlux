package cat.itacademy.s05.t01.n01.service.Game;

import cat.itacademy.s05.t01.n01.Models.Game;
import cat.itacademy.s05.t01.n01.Models.Deck;
import cat.itacademy.s05.t01.n01.Models.Card;
import cat.itacademy.s05.t01.n01.Models.Player;
import cat.itacademy.s05.t01.n01.repository.GameRepository;
import cat.itacademy.s05.t01.n01.service.Player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    @Override
    public Mono<Game> createGame(String playerName) {
        Game game = new Game(playerName);
        Deck deck = new Deck();
        game.getPlayerCards().add(deck.drawCard());
        game.getPlayerCards().add(deck.drawCard());
        return gameRepository.save(game);
    }

    @Override
    public Mono<Game> getGame(String id) {
        return gameRepository.findById(id);
    }

    @Override
    public Mono<Game> playGame(String id, String action, double betAmount) {
        return gameRepository.findById(id)
                .flatMap(game -> {
                    if ("LOOSE".equals(game.getStatus()) || "WIN".equals(game.getStatus())) {
                        return Mono.just(game);
                    }
                    Deck deck = new Deck();
                    switch (action.toUpperCase()) {
                        case "BET":
                            game.setBetAmount(betAmount);
                            game.setStatus("BET_PLACED");
                            break;
                        case "HIT":
                            game.getPlayerCards().add(deck.drawCard());
                            game.setStatus("IN_PROGRESS");
                            int handValue = calculateHandValue(game.getPlayerCards());
                            if (handValue > 21) {
                                game.setStatus("LOOSE");
                                updatePlayerScore(game.getPlayerName(), -1);
                            } else if (handValue == 21) {
                                game.setStatus("WIN");
                                updatePlayerScore(game.getPlayerName(), 1);
                            }
                            break;
                        case "STAND":
                            game.setStatus("STAND");
                            int finalHandValue = calculateHandValue(game.getPlayerCards());
                            if (finalHandValue > 21) {
                                game.setStatus("LOOSE");
                                updatePlayerScore(game.getPlayerName(), -1);
                            } else if (finalHandValue == 21) {
                                game.setStatus("WIN");
                                updatePlayerScore(game.getPlayerName(), 1);
                            } else {
                                game.setStatus("WIN");
                                updatePlayerScore(game.getPlayerName(), 1);
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid action: " + action);
                    }
                    return gameRepository.save(game);
                });
    }

    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepository.deleteById(id);
    }

    private int calculateHandValue(List<Card> cards) {
        int value = 0;
        int aceCount = 0;
        for (Card card : cards) {
            switch (card.getRank()) {
                case TWO: value += 2; break;
                case THREE: value += 3; break;
                case FOUR: value += 4; break;
                case FIVE: value += 5; break;
                case SIX: value += 6; break;
                case SEVEN: value += 7; break;
                case EIGHT: value += 8; break;
                case NINE: value += 9; break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING: value += 10; break;
                case ACE: aceCount++; value += 11; break;
            }
        }
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }

    private void updatePlayerScore(String playerName, int score) {
        playerService.createNewPlayer(playerName)
                .flatMap(player -> playerService.updatePlayerScore(player, score))
                .subscribe();
    }
}