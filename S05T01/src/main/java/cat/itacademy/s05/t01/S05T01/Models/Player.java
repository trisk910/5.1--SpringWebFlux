package cat.itacademy.s05.t01.S05T01.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double score;
    private Hand hand;

    // Default constructor
    public Player() {
        this.hand = new Hand();
    }

    // Parameterized constructor
    public Player(String name) {
        this.name = name;
        this.score = 0.0;
        this.hand = new Hand();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Double.compare(player.score, score) == 0 && Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(hand, player.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, score, hand);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", hand=" + hand +
                '}';
    }
}