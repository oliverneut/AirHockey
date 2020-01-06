package field;

public class Scores {

    private transient int player1;
    private transient int player2;

    public Scores() {
        this.player1 = 0;
        this.player2 = 0;
    }

    public void Goal1() {
        this.player1 ++;
    }

    public void Goal2() {
        this.player2++;
    }

    public int getPlayer1() {
        return this.player1;
    }

    public int getPlayer2() {
        return this.player2;
    }
}
