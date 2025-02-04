package cat.itacademy.s05.t01.S05T01.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "game")
public class Game {
    @Id
    private String id;
    private Player player;
    private Dealer dealer;
    private Deck deck;


    public Game() {
        this.dealer = new Dealer();
        this.deck = new Deck();
    }


    public Game(String playerName) {
        this.player = new Player(playerName);
        this.dealer = new Dealer();
        this.deck = new Deck();
        startGame();
    }

    public void startGame() {
        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayerName(String playerName) {
        this.player = new Player(playerName);
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", player=" + player +
                ", dealer=" + dealer +
                ", deck=" + deck +
                '}';
    }
}