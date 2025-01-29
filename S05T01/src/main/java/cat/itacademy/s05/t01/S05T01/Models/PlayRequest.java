package cat.itacademy.s05.t01.S05T01.Models;

public class PlayRequest {
    private String move;
    private double betAmount;

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }
}