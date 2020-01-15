package field;

public class ScoreCount {

    private transient int player1;
    private transient int player2;
    private static ScoreCount instance = new ScoreCount();

    /**
     * Creates the score object.
     */
    private ScoreCount() {
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

    public static ScoreCount getInstance() {
        return instance;
    }
}
