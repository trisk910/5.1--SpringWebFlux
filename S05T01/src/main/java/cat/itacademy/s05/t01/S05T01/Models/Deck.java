package cat.itacademy.s05.t01.S05T01.Models;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        this.cards = new Stack<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.push(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.pop();
    }

    // Getters and setters
}