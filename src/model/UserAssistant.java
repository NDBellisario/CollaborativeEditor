package model;
import java.util.*;

public class UserAssistant<UserAssitant> extends Observable
{
	UserAssistant theUser;
	ArrayList<UserAssitant> userList;

	public UserAssistant()
	{
	}	
	
	/**
	 * Change the permissions of the users on the page
	 * @param arg, depending on the integer, thats the new permission
	 */
	public void changePermissions(int arg)
	{
	}
	/**
	 * add a new user to the document
	 * @param name
	 * @param password
	 * @param basePermissions
	 */
	public void addUser(String name, String password, String basePermissions)
	{
	}

	/**
	 * Give the user a new password
	 * @param username
	 */
	public void recoverPassword(String username)
	{
	}
	
	/**
	 * Set the users password
	 * @param arg
	 */
	public void setPassword(String arg)
	{
	}
	
	/**
	 * When the user creates his ID, he creates a UserName
	 * @param arg
	 */
	public void setUsername(String arg)
	{
	}
	
	/**
	 * Get the password from the user
	 * @return
	 */
	public String getPassword()
	{
		return null;
	}
	
	/**
	 * Get a user's username
	 * @return
	 */
	public String getUsername()
	{
		return null;
	}
	
	/**
	 * Get all the users
	 * @return
	 */
	public ArrayList getUsers()
	{
		return userList;
	}
}
