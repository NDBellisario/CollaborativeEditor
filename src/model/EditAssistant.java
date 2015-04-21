package model;
import java.util.*;

public class EditAssistant extends Observable
{
	private ArrayList<User> theData;
	private User currUser;


	/**
	 * 
	 * The typing mechanism for the document itself. Allows you to add characters
	 * @param toAdd
	 */
	public void addText(String toAdd)
	{
		if (currUser.getPermission() < 3) {
			
		}
		
	}
	
	/**
	 * 
	 * Delete a string in the text
	 * @param toDelete
	 */
	public void deleteText(String toDelete)
	{
	}

	/**
	 * Undos any change made to the document
	 * @return null
	 */
	public void undoText()
	{
		return null;
	}
	
	public User getUser(String name) {
		User toReturn = null;
		for (User user : theData) {
			if (user.userName.equals(name)) {
				toReturn = user;
			}
		} 
		return toReturn;
	}
	
	/**
	 * 
	 * Redo's the last change made to the document
	 */
	public void redoText()
	{
	}
}
