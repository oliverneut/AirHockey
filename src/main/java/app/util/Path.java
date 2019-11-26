package app.util;

import lombok.Getter;

public class Path {

    // The @Getter methods are needed in order to access
    public static class Web {
        @Getter
        public static final String MATCH = "/match/";
        @Getter
        public static final String LOGIN = "/login/";
        @Getter
        public static final String LOGOUT = "/logout/";
    }
}