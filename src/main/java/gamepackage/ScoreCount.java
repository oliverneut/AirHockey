package gamepackage;

public class ScoreCount {
    private transient int scoreA;
    private transient int scoreB;

    /**
     * Instantiates the ScoreCount of a game.
     * With each player's score starting at 0.
     */
    public ScoreCount() {
        scoreA = 0;
        scoreB = 0;
    }

    /**
     * Increments the score of player A.
     */
    public void incrementScoreA() {
        scoreA += 1;
    }

    /**
     * Increments the score of player B.
     */
    public void incrementScoreB() {
        scoreB += 1;
    }

    /**
     * Gets the current score of player A.
     *
     * @return the current score of player A.
     */
    public int getScoreA() {
        return scoreA;
    }

    /**
     * Gets the current score of player B.
     *
     * @return the current score of player B.
     */
    public int getScoreB() {
         return scoreB;
    }
}
