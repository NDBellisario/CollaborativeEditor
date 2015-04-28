package server;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import networking.*;
import view.*;
import model.*;
/* Test Username: cat Password = meow */
/*
 * Written by Taylor Cox
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

/*
 * Packet Order!
 * 
 * 1.	Recieved: LogInPacket
 * 			.Execute() which returns a boolean value if info matched on file
 * 2.	Sent: Boolean Value
 * 			True = Login Successful, False = Info doesn't match
 * 3.	Sent: The User object of who connected
 * 			The Client then should create GUI based on this User
 * 4.	Recieved:
 * 			Packet with text to add
 * 5.	Sent:
 * 			Updated master text field
 * 
 * Second Packet 
 */
public class CEServer extends JFrame {
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
	private static ServerSocket ourServer;
	private String masterList;
	public static void main(String[] args) {
		new CEServer();
	}
	/*
	 * Initializes the variables Gets a port, and jamborees with the view as
	 * needed If a failed attempt to setup, uses a default port If we get
	 * someone, we spawn an acceptor
	 */
	public CEServer() {

		this.chatLog = new ArrayList<String>(); // create the chat log
		this.editsLog = new ArrayList<String>(); // log of edits
		this.activeUsers = new ArrayList<String>(); // log of edits
		this.outputs = new HashMap<String, ObjectOutputStream>(); // setup the
		masterList = "This is coming from the server"; // map
		RevisionAssistant revStack = new RevisionAssistant();
		this.theUsers = new UserAssistant();
		theUsers.addUser("cat", "meow", 3);
		ourView = new ServerView(theUsers);
		int portNumber = ourView.getPortNumber();
		try {
			ourServer = new ServerSocket(portNumber);
			ourView.roundTwo();
			new Thread(new ClientAccepter()).start();
		} catch (IOException e) {
			ourView.youScrewedUp();
			portNumber = ourView.getPortNumber();
			new Thread(new ClientAccepter()).start();
			try {
				ourServer = new ServerSocket(portNumber);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ourView.roundTwo();
		}
	}
	public void updateChats(ArrayList<String> arg) {
	}
	public void updateEdits(ArrayList<String> arg) {
	}
	public void updateUsers(ArrayList<String> arg) {
	}
	/*
	 * This class accepts connections from the server, gathers the streams of
	 * the socket, and passes it onto a new thread who's goal is to initialize
	 * the connection and have the user login
	 */
	private class ClientAccepter implements Runnable {
		String userName;
		@Override
		public void run() {
			while (true) {
				Socket temp;
				try {
					temp = ourServer.accept();
					ObjectOutputStream output = new ObjectOutputStream(temp.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(temp.getInputStream());
					new Thread(new ClientListener(temp, output, input)).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*
	 * This gets out input/output stream Reads in a login packed containing
	 * username and password Executes and gets a boolean. True means we are
	 * good, false means no If true, update list on server side and spawn an
	 * edit and chat thread If false, let the client know so they can fix it.
	 */
	private class ClientListener implements Runnable {
		private ObjectOutputStream output;
		private ObjectInputStream input;
		private Socket tempSocket;
		private String userName;
		public ClientListener(Socket socketArg, ObjectOutputStream argOutput, ObjectInputStream argInput) {
			tempSocket = socketArg;
			input = argInput;
			output = argOutput;
		}
		@Override
		public void run() {
			try {
				// read if clients username/password are correct
				LoginPacket userLogin = (LoginPacket) input.readObject();
				// TODO: Check to see if username and password are correct
				// TODO: Instead of username, client name?
				boolean correctInfo = userLogin.execute(theUsers);
				User toPass;
				if (correctInfo == false) {
					toPass = theUsers.addUser(userLogin.getName(), userLogin.getPassword(), 3);
					

				}
				else
				{
					toPass = theUsers.getUser(userName);
				}
				userName = userLogin.getName();
				
				output.writeObject(correctInfo);
				output.writeObject(toPass);
				outputs.put(userLogin.getName(), output);
				clientInit();
				new Thread(new ClientHandler(input, output, toPass)).start();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		/*
		 * Simply adds our client to our list and calls update in view Also
		 * popup saying someone connected
		 */
		private void clientInit() {
			// Updating the client list!
			activeUsers.add(userName);
			ourView.updateClients(activeUsers);
			ourView.userConnect();
			// TODO: Send the clients cool stuff
		}
	}
	/*
	 * Bare bones now, working with the beginning functionality of revisions and
	 * how those are sent to the Users via packets
	 */
	private class ClientHandler implements Runnable {
		private ObjectInputStream inputStream;
		private ObjectOutputStream outputStream;
		private User mainUser;
		public ClientHandler(ObjectInputStream inputArg, ObjectOutputStream outputArg, User user) {
			inputStream = inputArg;
			outputStream = outputArg;
			mainUser = user;
		}
		/*
		 * What's happening? Client sends a packet with the new text to add The
		 * packet's execute creates a new revision instance Updates master list,
		 * and writes it out!
		 */
		@Override
		public void run() {
			while (true) {
				try {
					/*
					 * TODO: Note server throwing massive End of File exception
					 * the reporting in this run TODO: any ideas?
					 */
					EditPacket readPacket = (EditPacket) inputStream.readObject();
					masterList = readPacket.execute(mainUser);
					// Done in .execute
					// Revision revision = new Revision(mainUser, (String)
					// inputStream.readObject());
					// RevisionAssistant.revisionStack.add(revision);
					// String revText = revision.getText();
					outputStream.writeObject(masterList);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
