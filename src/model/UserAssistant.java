package model;
import java.io.Serializable;
import java.util.ArrayList;

public class UserAssistant implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<User> userList;

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
    public User addUser(String userName, String password) {
        User toReturn = new User(userName, password, userList.size() + 1, 0); //creates new user sets their unique ID# to how many users we have plus 1
        userList.add(toReturn);
        return toReturn;
        //setChanged();
        //notifyObservers();
    }

    /**
     * Call getUser and returns a specific User
     * @param identificationNumber
     * @return User instance from list
     */
    public User getUser(int identificationNumber) {
    	return userList.get(identificationNumber - 1);
    }
    
    /**
     * get's User based on name rather and ID number
     * @param username
     * @return User instance from list
     */
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
     * Returns entire list of User's inside
     * @return returns and Arraylist of users
     */
    public ArrayList<User> getUsers() {
        return userList;
    }
}
