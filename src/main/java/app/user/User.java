package app.user;

public class User {

    transient String username;
    transient String password;
    private transient int userid;

    User(int userid, String username, String password) {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

    public int getUserid() {
        return this.userid;
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
