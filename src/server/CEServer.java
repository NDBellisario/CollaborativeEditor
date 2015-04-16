package server;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;
import model.*;

public class CEServer
{
	private ChatAssistant theChats;
	private ArrayList chatLog;
	private EditAssistant theEdits;
	private ArrayList editsLog;
	private UserAssistant theUsers;
	private ArrayList userLog;

	public static void main(String[] args)
	{
		//Turtles I like them.....
		JOptionPane.showMessageDialog(null, "This Should Pop Up When Ran!");
	}

	public void start(int port)
	{
	}

	public void login()
	{
	}

	public void updateChats(ArrayList arg)
	{
	}

	public void updateEdits(ArrayList arg)
	{
	}

	public void updateUsers(ArrayList arg)
	{
	}
	private class ClientHandler implements Runnable
	{
		public ClientHandler(Socket arg)
		{
		}

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
		}
	}
}
