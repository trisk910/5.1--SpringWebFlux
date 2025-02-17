// src/main/java/cat/itacademy/s05/t01/n01/Models/Game.java
package cat.itacademy.s05.t01.n01.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Document(collection = "games")
public class Game {
    @Id
    private String id;

    private String playerName;

    private String status;

    private double betAmount;

    private List<Card> playerCards;
    private List<Card> dealerCards;

    public Game(String playerName) {
        this.playerName = playerName;
        this.status = "NEW";
        this.betAmount = 0;
        this.playerCards = new ArrayList<>();
        this.dealerCards = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(List<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }
}