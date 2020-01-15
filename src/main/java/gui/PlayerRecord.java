    package gui;

    public class PlayerRecord {


        private String username;
        private int numGames;
        private int gamesWon;
        private int goalsScored;

        /**
         * Constructor default.
         *
         * @param username    .
         * @param numGames    .
         * @param gamesWon    .
         * @param goalsScored .
         */
        public PlayerRecord(String username, int numGames, int gamesWon, int goalsScored) {
            this.username = username;
            this.numGames = numGames;
            this.gamesWon = gamesWon;
            this.goalsScored = goalsScored;
        }


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getNumGames() {
            return numGames;
        }

        public void setNumGames(int numGames) {
            this.numGames = numGames;
        }

        public int getGamesWon() {
            return gamesWon;
        }

        public void setGamesWon(int gamesWon) {
            this.gamesWon = gamesWon;
        }

        public int getGoalsScored() {
            return goalsScored;
        }

        public void setGoalsScored(int goalsScored) {
            this.goalsScored = goalsScored;
        }
    }
