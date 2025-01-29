package cat.itacademy.s05.t01.S05T01.Models;

public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}