package app.user;

import lombok.Value;

@Value
public class User {

    int userid;
    String username;
    String emailAddress;
    String password;

    User(int userid, String username, String emailAddress, String password) {
        this.userid = userid;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof User) {
            return this.userid == ((User) obj).userid;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.userid);
    }
}
