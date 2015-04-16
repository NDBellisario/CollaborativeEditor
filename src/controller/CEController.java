package controller;

import java.net.Socket;
import javax.swing.*;
import model.*;

public class CEController extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainProgPanel;
	private JMenuBar menuBarCore;
	private JMenu fileContainer;
	private JMenu editContainer;
	private JMenu userContainer;
	private JMenuItem save;
	private JMenuItem saveLocal;
	private JMenuItem quitOption;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem version;
	private JMenuItem addUser;
	private JMenuItem removeUser;
	private JMenuItem changePermission;
	public CEController(ChatAssistant theChat, EditAssistant editAs, UserAssistant theUser)
	{

	}
	public void serverConnect()
	{

	}
	private class ServerFirstContact implements Runnable
	{

		public ServerFirstContact(Socket arg)
		{
		}

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
		}
	}
	private class ServerListener implements Runnable
	{

		public ServerListener(Socket arg)
		{
		}

		@Override
		public void run()
		{
			// TODO Auto-generated method stub
		}
	}
	public static void main(String[] args)
	{

	}
}
