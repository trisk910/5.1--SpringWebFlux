package cat.itacademy.s05.t01.n01.Models;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "player")
public class Player {
    @Getter
    @Setter
    @Id
    private int id;
    @NotNull
    private String name;
    @Getter
    @Setter
    private double score;
    @Setter
    @Getter
    private int gamesPlayed;

    public Player(String name) {
        this.name = name;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

}