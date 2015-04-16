package model;
import java.util.*;

public class UserAssistant<UserAssitant> extends Observable
{
	UserAssistant theUser;
	ArrayList<UserAssitant> userList;

	public UserAssistant()
	{
	}

	public void changePermissions(int arg)
	{
	}

	public void addUser(String name, String password, String basePermissions)
	{
	}

	public void recoverPassword(String username)
	{
	}

	public void setPassword(String arg)
	{
	}

	public void setUsername(String arg)
	{
	}

	public String getPassword()
	{
		return null;
	}

	public String getUsername()
	{
		return null;
	}

	public ArrayList getUsers()
	{
		return userList;
	}
}
