package model;
import java.io.Serializable;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    byte[] password;
    String userName;
    int permission;
    int identification;

    public User(String userName, String password, int permission, int id) {
        setUserName(userName);
        try {
			setPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setPermission(permission);
        setIdentification(id);
    }

    /**
     * Sets the User Name to the given User Name if the length is greater than 4
     *
     * @param userName
     */
    //Sketchy to return a boolean?
    private boolean setUserName(String userName) {
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
     * 
     */
    public boolean setPassword(String password) throws NoSuchAlgorithmException {
        //if (password.length() > 4) {
        MessageDigest hashSha = MessageDigest.getInstance("SHA-1");
    	this.password = hashSha.digest(password.getBytes());
        return true;
        //}
        //else {
        //	return false;
        //}

    }
    
    public void setIdentification(int id) {
    	this.identification = id;
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
    public byte[] getPassword() {
        return this.password;
    }

    public int getPermission() {
        return this.permission;
    }

    /**
     * Change the permissions of the users on the page.  
     * 1 is owner and the boss, add people and change permissions
     * 2 can edit/chat but can't add people
     * 3 can read and chat
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

