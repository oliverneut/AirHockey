package basis;

import static game.MatchSocketHandler.sendScoreUpdate;

public class ScoreCount {

    private static ScoreCount instance = new ScoreCount();
    private transient int player1;
    private transient int player2;
    private transient int gameOver;

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
        sendScoreUpdate = true;
    }

    /**
     * Increments goals for second player.
     */
    public void goal2() {
        this.player2++;
    }

    /**
     * Fetches the goal count for the first player.
     *
     * @return The first score count.
     */
    public int getPlayer1() {
        return this.player1;
    }

    /**
     * Fetches the goal count of the second player.
     *
     * @return The second score count.
     */
    public int getPlayer2() {
        return this.player2;
    }

    /**
     * Grants access to the only instance of ScoreCount.
     * @return the only instance of ScoreCount
     */
    public static ScoreCount getInstance() {
        return instance;
    }

    /**
     * Updates the value of the winner.
     */
    public void winOne() {
        gameOver = 1;
    }

    /**
     * Updates the value of the winner.
     */
    public void winTwo() {
        gameOver = 2;
    }

    public int getWinner() {
        return gameOver;
    }

    /**
     * Added a reset score option.
     */
    public void resetScore() {
        this.player1 = 0;
        this.player2 = 0;
        this.gameOver = 0;
    }
}
