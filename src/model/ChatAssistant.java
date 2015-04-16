package model;
import java.util.*;

public class ChatAssistant extends Observable
{
	private ArrayList messages;
	private String toSend;

	public ChatAssistant()
	{
	}

	public void sendMessage(String toSend, UserAssistant arg)
	{
	}

	public boolean isTyping(UserAssistant arg)
	{
		return false;
	}
}
