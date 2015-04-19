package model;
import java.util.*;

public class ChatAssistant extends Observable
{
	private ArrayList messages;
	private String toSend;
	
	/**
	 * Constructor for chat assistant
	 * 
	 */
	public ChatAssistant()
	{
		
	}
	
	/**
	 * 
	 * Sends a message to some other user
	 * @param toSend - The message that is sent to another user
	 * @param arg
	 */
	
	public void sendMessage(String toSend, UserAssistant arg)
	{
		
	}
	/**
	 * Checks to see if some user is typing in the chat
	 * @param arg
	 * @return boolean
	 */
	public boolean isTyping(UserAssistant arg)
	{
		return false;
	}
}
