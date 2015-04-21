package model;
import java.util.*;

public class UserAssistant extends Observable
{
	User theUser;
	ArrayList<User> userList;
	
	//When would we ever create an instance of UserAssistant?
	/*
	public UserAssistant(String userName, String password, int permission)
	{	
		theUser = new User(userName, password, permission);
		userList.add(theUser);
	}	
	*/

	public void changePermissions(int permission) 
	{
		theUser.setPermission(permission);
	}
	/**
	 * add a new user to the document
	 * @param name
	 * @param password
	 * @param basePermissions
	 */
	//This is the equivalent of adding to the arraylist?
	public void addUser(String name, String password, int basePermissions)
	{
		userList.add(theUser);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Give the user a new Password
	 * @param username - User whose password is to be changed
	 * @param newPassword - New password that user wants
	 */
	public void recoverPassword(String username, String newPassword)
	{
		for (User user : userList) {
			if (user.userName.equals(username)) {
				user.setPassword(newPassword);
				setChanged();
				notifyObservers();
			}
		}
	}

	/**
	 * Get all the users
	 * @return The list of Users
	 */
	public ArrayList<User> getUsers()
	{
		return userList;
	}
}
