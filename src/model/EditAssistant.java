package model;
import java.util.*;

public class EditAssistant extends Observable
{
	private ArrayList theData;

	public EditAssistant()
	{
	}

	/**
	 * 
	 * The typing mechanism for the document itself. Allows you to add characters
	 * @param toAdd
	 */
	public void addText(String toAdd)
	{
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
	public EditAssistant undoText()
	{
		return null;
	}
	
	/**
	 * 
	 * Redo's the last change made to the document
	 */
	public void redoText()
	{
	}
}
