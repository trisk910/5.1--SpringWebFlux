// src/main/java/cat/itacademy/s05/t01/n01/Models/Game.java
package cat.itacademy.s05.t01.n01.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Getter
@Document(collection = "games")
public class Game {
    @Id
    @Getter
    @Setter
    private String id;
    @Setter
    private String playerName;
    @Setter
    private String status;
    @Setter
    private double betAmount;
    @Setter
    private List<Card> playerCards;
    private List<Card> dealerCards;

    public Game(String playerName) {
        this.playerName = playerName;
        this.status = "NEW";
        this.betAmount = 0;
        this.playerCards = new ArrayList<>();
        this.dealerCards = new ArrayList<>();
    }
}