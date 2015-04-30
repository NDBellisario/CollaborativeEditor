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
 * 
 * This class handles several things.
 * 1: Connecting to the server
 * 2. Account Login/Creation/Recovery
 * 3. Communication to the server for text/chat updates
 * 
 */

public class CEServer extends JFrame {
	/*
	 * Private Instance Variables
	 */
	private static final long serialVersionUID = 1L;
	public HashMap<String, ObjectOutputStream> outputs;
	private UserAssistant theUsers;
	private ArrayList<String> activeUsers;
	private ServerView ourView;
	private static ServerSocket ourServer;
	public static String masterList;
	/*
	 * The constructor that starts the server
	 */
	public static void main(String[] args) {
		new CEServer();
	}
	/*
	 * Initializes the variables Gets a port, and jamborees with the view as
	 * needed If a failed attempt to setup, uses a default port.
	 */
	public CEServer() {

		// Time to Initialize Variables!
		initVariables();

		// Setting up the Server.
		int portNumber = ourView.getPortNumber();
		try { // Tries to start the server on the given port
			ourServer = new ServerSocket(portNumber);
			ourView.roundTwo();
			new Thread(new ClientAccepter()).start();
		} catch (IOException e) { // If it can't it defaults to 9002
			ourView.youScrewedUp();
			portNumber = ourView.getPortNumber();

			try {
				ourServer = new ServerSocket(portNumber); // Sets up the server.
				new Thread(new ClientAccepter()).start(); // Starts accepting
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ourView.roundTwo(); // Adds everything to view frame
		}
	}
	public void initVariables() {
		this.activeUsers = new ArrayList<String>(); // log of connected users
		this.outputs = new HashMap<String, ObjectOutputStream>(); // the outputs
		RevisionAssistant revStack = new RevisionAssistant(); // TODO: Revision
		masterList = ""; // List of text panel
		this.theUsers = new UserAssistant(); // TODO: Read from file
		theUsers.addUser("cat", "meow", 3); // A Default account to use.
		ourView = new ServerView(theUsers); // New Server View
	}

	/*
	 * This class accepts connections from the server, gathers the streams of
	 * the socket, and passes it onto a new thread who's goal is to initialize
	 * the connection and have the user login
	 */
	private class ClientAccepter implements Runnable {

		@Override
		public void run() {
			while (true) {
				Socket temp;
				try {
					temp = ourServer.accept();
					ObjectOutputStream output = new ObjectOutputStream(temp.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(temp.getInputStream());
					new Thread(new ClientFirstContact(output, input)).start();
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
	// TODO: Account creation button
	private class ClientFirstContact implements Runnable {
		private ObjectOutputStream output;
		private ObjectInputStream input;
		private String userName;
		public ClientFirstContact(ObjectOutputStream argOutput, ObjectInputStream argInput) {
			input = argInput;
			output = argOutput;
		}
		@Override
		public void run() {
			try {
				// Read from controller the info of the user trying to log in
				LoginPacket userLogin = (LoginPacket) input.readObject();
				// Executes it, false means wrong info, true means good to go
				boolean correctInfo = userLogin.execute(theUsers);
				User toPass; // The user whom the controller is to be set up for
				// Writes login success to client
				output.writeObject(correctInfo);
				if (correctInfo == false) {
					// Here means log in un/pw didn't match
					// Reads "" or "username" from the client
					String inputArg = (String) input.readObject();

					if (!inputArg.equals("")) {
						// Here means we are tasked with recovering an account
						// Gets user recovery info
						String toSend = userLogin.getRecovery(inputArg, theUsers);
						output.writeObject(toSend); // Writes out
						if (toSend.length() > 40) {
							// Here means that the UN didn't exist!, so we will
							// give default value
							toPass = theUsers.addUser(inputArg, "0000", 3);
						} else {
							// Here means they do exist so let's set toPass to
							// our user
							toPass = theUsers.getUser(userLogin.getName());
						}
					} else {
						// Get here know that we had "" and OP wants new account
						// Adds the new User
						theUsers.addUser(userLogin.getName(), userLogin.getPassword(), 3);
						// Write out this String to CE
						output.writeObject("Created User With Previously Entered Username/Password Combo");
						// Grabs the suer we want to write
						toPass = theUsers.getUser(userLogin.getName());
					}
				} else
					// If login boolean was True, then just log them in
					toPass = theUsers.getUser(userLogin.getName());

				userName = userLogin.getName();
				output.writeObject(toPass); // Writes out the User Object
				output.writeObject(masterList); // Writes out the current List
				outputs.put(userLogin.getName(), output); // Puts on output map
				clientInit(); // Adds user to session so server can keep track

				// New thread to deal with user
				new Thread(new ClientHandler(input, output, toPass)).start();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		/*
		 * Let's the server know we have a new Client
		 */
		private void clientInit() {
			activeUsers.add(userName);
			ourView.updateClients(activeUsers);
			ourView.userConnect();
		}
	}
	/*
	 * This is actually what deals with communicating with the Client for
	 * updates!
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

		@Override
		public void run() {

			while (true) {
				try {
					// Reads packet from the controller
					EditPacket readPacket = (EditPacket) inputStream.readObject();
					// Executes the packet
					String newText = readPacket.execute();
					// Checks to see if we even have something aka not null.
					if (!newText.equals("")) {
						// Writes it out to ALL of the Client's
						for (ObjectOutputStream temp : outputs.values()) {
							masterList = newText;
							temp.writeObject(masterList);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
