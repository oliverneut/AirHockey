package app.userstats;

import app.user.UserDAO;
import com.github.cliftonlabs.json_simple.JsonObject;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserStatsController {

    transient UserDAO userDAO;
    transient UserStatsDAO userStatsDAO;

    public Route getUserStats = (Request request, Response response) -> {

        int userid = userDAO.getByUsername(request.params("user")).getUserid();

        JsonObject result = new JsonObject();

        int matchesPlayed = userStatsDAO.retrieveMatchesPlayed(userid);
        result.put("Matches Played", matchesPlayed);

        int matchesWon = userStatsDAO.retrieveMatchesWon(userid);
        result.put("Matches Won", matchesWon);

        int goalsScored = userStatsDAO.retrieveGoalsScored(userid);
        result.put("Goals Scored", goalsScored);

        int goalsAgainst = userStatsDAO.retrieveGoalsAgainst(userid);
        result.put("Goals Against", goalsAgainst);

        return result.toJson();

    };
}
