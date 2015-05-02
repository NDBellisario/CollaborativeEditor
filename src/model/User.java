package model;
import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String password;
    String userName;
    int permission;
    //In case we need the user's IP address?
    InetAddress ip;

    public User(String userName, String password, int permission) {
        setUserName(userName);
        setPassword(password);
        setPermission(permission);
    }

    /**
     * Sets the User Name to the given User Name if the length is greater than 4
     *
     * @param userName
     */
    //Sketchy to return a boolean?
    public boolean setUserName(String userName) {
        //if (userName.length() > 4) {
        this.userName = userName;
        return true;
        //}
        //return false;
    }

    //sketch

    /**
     * Sets password to be 4 or more characters
     *
     * @param password
     * @return true if the password was set correctly, false otherwise
     */
    public boolean setPassword(String password) {
        //if (password.length() > 4) {
        this.password = password;
        return true;
        //}
        //else {
        //	return false;
        //}

    }

    /**
     * @return Username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return Password
     */
    public String getPassword() {
        return this.password;
    }

    public int getPermission() {
        return this.permission;
    }

    /**
     * Change the permissions of the users on the page. 0 is all permissions as in owner that can shut down with permissions 1, 2, 3 1 is moderator with everything but ability to shut down document 2
     * can edit/chat but can't add people 3 can read and chat
     *
     * @param permission, depending on the integer, thats the new permission
     */
    public void setPermission(int permission) {
        this.permission = permission;
    }

    public int compareTo(User toComp) {
        if (this.userName.compareTo(toComp.userName) > 0) {
            return 1;
        } else if (this.userName.compareTo(toComp.userName) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}

