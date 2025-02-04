package cat.itacademy.s05.t01.n01.Exceptions;

public class DeckEmptyException extends RuntimeException {
    public DeckEmptyException(String message) {
        super("No cards left in the deck.");
    }
}
