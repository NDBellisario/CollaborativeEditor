package model;
import java.util.*;

public class UserAssistant extends Observable {
    ArrayList<User> userList;

    // When would we ever create an instance of UserAssistant?

    public UserAssistant() {

        userList = new ArrayList<User>();
    }

    /**
     * add a new user to the document
     *
     * @param name
     * @param password
     * @param basePermissions
     */
    // This is the equivalent of adding to the arraylist?
    public User addUser(String userName, String password, int permission) {
        User toReturn = new User(userName, password, permission);
        userList.add(toReturn);
        return toReturn;
        //setChanged();
        //notifyObservers();
    }

    public User getUser(String username) {
        for (User user : userList) {
            if (user.getUserName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        //If user doesn't exist
        return null;
    }

    /**
     * Give the user a new Password
     *
     * @param username    - User whose password is to be changed
     * @param newPassword - New password that user wants
     */
    public String recoverPassword(String username) {
        for (User user : userList) {
            if (user.userName.equals(username)) {
                return user.getPassword();

            }
        }
        return "NO USERNAME EXISTS";
    }

    /**
     * Get all the users
     *
     * @return The list of Users
     */
    public ArrayList<User> getUsers() {
        return userList;
    }
}
