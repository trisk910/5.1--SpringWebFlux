package cat.itacademy.s05.t01.n01.Models;

import java.util.List;

public class CardUtils {

    public static int calculateHandValue(List<Card> cards) {
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
                case TEN, JACK, QUEEN, KING: value += 10; break;
                case ACE: aceCount++; value += 11; break;
            }
        }
        do{
            value -= 10;
            aceCount--;
        }while (value > 21 && aceCount > 0);
        return value;
    }
}