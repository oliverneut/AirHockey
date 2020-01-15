package gui;

public class LeaderBoardPlayerRecord implements Comparable<LeaderBoardPlayerRecord> {


    private String username;
    private double score;

    /**
     * Constructor default.
     *
     * @param username    .
     * @param score .
     */
    public LeaderBoardPlayerRecord(String username, double score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(LeaderBoardPlayerRecord o) {
        if (o.score >= this.score) {
            return 1;
        }
        return -1;
    }
}
