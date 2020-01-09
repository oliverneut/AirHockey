package field;

public class Scores {

    private transient int player1;
    private transient int player2;

    /**
     * Creates the score object.
     */
    public Scores() {
        this.player1 = 0;
        this.player2 = 0;
    }

    /**
     * Increments goals for first player.
     */
    public void goal1() {
        this.player1++;
    }

    /**
     * Increments goals for second player.
     */
    public void goal2() {
        this.player2++;
    }

    /**
     * Fetches the goal count for the first player.
     * @return The first score count.
     */
    public int getPlayer1() {
        return this.player1;
    }


    /**
     * Fetches the goal count of the second player.
     * @return The second score count.
     */
    public int getPlayer2() {
        return this.player2;
    }
}
