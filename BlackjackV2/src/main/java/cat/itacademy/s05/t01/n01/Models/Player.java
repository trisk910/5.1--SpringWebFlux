package cat.itacademy.s05.t01.n01.Models;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "players")
public class Player {
    @Id
    private int id;
    @NotNull
    private String name;
    private double score;


    public Player(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}