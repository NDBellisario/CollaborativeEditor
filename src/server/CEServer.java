package server;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import networking.LoginPacket;
import view.ServerView;
import model.*;

/* 
 * Written By Taylor Cox
 * Here's what Happens
 * 
 * We create a new ServerView which asks for a port
 * If the port is valid, we use it, if not, default to 9001.
 * Boom new thread for accepting
 * 
 * Begin accepting clients, we read in a LoginPacket that we execute
 * True means username and password = cool, False = they're not.
 * (This is written out to the Client's stream so they can act appropriately!)
 * If true, add to our server's client list in view, and update it.
 * 
 * Then create two new threads.  Editor and Chat which are bare now
 * 
 */
public class CEServer extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, ObjectOutputStream> outputs;
	private ChatAssistant theChats;
	private ArrayList<String> chatLog;
	private EditAssistant theEdits;
	private ArrayList<String> editsLog;
	private UserAssistant theUsers;
	private ArrayList<String> activeUsers;
	private ServerView ourView;
	private ServerSocket ourServer;

	public static void main(String[] args)
	{

		new CEServer();
	}
	/*
	 * Initializes the variables Gets a port, and jamborees with the view as
	 * needed If a failed attempt to setup, uses a default port If we get
	 * someone, we spawn an acceptor
	 */
	public CEServer()
	{

		this.chatLog = new ArrayList<String>(); // create the chat log
		this.editsLog = new ArrayList<String>(); // log of edits
		this.activeUsers = new ArrayList<String>(); // log of edits
		this.outputs = new HashMap<String, ObjectOutputStream>(); // setup the
																	// map
		ourView = new ServerView(theUsers);
		int portNumber = ourView.getPortNumber();
		try
		{
			ourServer = new ServerSocket(portNumber);
			ourView.roundTwo();
			new Thread(new ClientAccepter()).start();
		} catch (IOException e)
		{
			ourView.youScrewedUp();
			portNumber = ourView.getPortNumber();
			new Thread(new ClientAccepter()).start();
			try
			{
				ourServer = new ServerSocket(portNumber);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ourView.roundTwo();
		}

	}

	public void updateChats(ArrayList<String> arg)
	{
	}

	public void updateEdits(ArrayList<String> arg)
	{
	}

	public void updateUsers(ArrayList<String> arg)
	{
	}
	/*
	 * This gets out input/output stream Reads in a login packed containing
	 * username and password Executes and gets a boolean. True means we are
	 * good, false means no If true, update list on server side and spawn an
	 * edit and chat thread If false, let the client know so they can fix it.
	 */
	private class ClientAccepter implements Runnable
	{

		String userName;
		@Override
		public void run()
		{
			while (true)
			{
				Socket temp;
				try
				{
					temp = ourServer.accept();
					ObjectOutputStream output = new ObjectOutputStream(temp.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(temp.getInputStream());

					// read if clients username/password are correct
					LoginPacket userLogin = (LoginPacket) input.readObject();
					// TODO: Check to see if username and password are correct
					// TODO: Instead of username, client name?
					boolean correctInfo = userLogin.execute();
					if (correctInfo)
					{
						userName = userLogin.getName();
						outputs.put(userName, output);
						// spawn a thread to handle communication with this
						// client
						clientInit();
						new Thread(new ClientHandlerEditor(input, output)).start();
						new Thread(new ClientHandlerChat(input, output)).start();
					} else
					{
						// Client gets a false boolean, knows that the user
						// account
						// isn't valid.
						output.writeObject(correctInfo);
					}

				} catch (IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}

			}

		}
		/*
		 * Simply adds our client to our list and calls update in view Also
		 * popup saying someone connected
		 */
		private void clientInit()
		{
			// Updating the client list!
			activeUsers.add(userName);
			ourView.updateClients(activeUsers);
			ourView.userConnect();
			// TODO: Send the clients cool stuff

		}
	}
	/*
	 * Bare bones now, just stores the input and output stream of a client
	 */
	private class ClientHandlerEditor implements Runnable
	{

		private ObjectInputStream editorInput;
		private ObjectOutputStream editorOutput;
		public ClientHandlerEditor(ObjectInputStream inputArg, ObjectOutputStream outputArg)
		{
			editorInput = inputArg;
			editorOutput = outputArg;
		}

		@Override
		public void run()
		{
			// TODO This is going to handle making sure the client is fully
			// updated right!
		}
	}
	/*
	 * Bare bones now, just stores the input and output stream of a client
	 */
	private class ClientHandlerChat implements Runnable
	{

		private ObjectInputStream chatInput;
		private ObjectOutputStream chatOutput;
		public ClientHandlerChat(ObjectInputStream inputArg, ObjectOutputStream outputArg)
		{
			chatInput = inputArg;
			chatOutput = outputArg;
		}

		@Override
		public void run()
		{
			// TODO This is going to handle making sure the client is fully
			// updated right!
		}
	}

}
