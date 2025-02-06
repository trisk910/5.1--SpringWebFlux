package cat.itacademy.s05.t01.n01.Models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

}