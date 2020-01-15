package app.util;

import spark.Request;

public class RequestUtil {

    public static String getQueryUser(Request request) {
        return request.queryParams("user");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }

    public static int getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    /**
     * Remove the loggedOut attribute from the session.
     *
     * @param request Request.
     * @return if loggedOut attribute was present.
     */
    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    /**
     * Remove the loginRedirect attribute from the session.
     *
     * @param request Request.
     * @return The redirection.
     */
    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }

}