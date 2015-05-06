package server;
import model.Doc;
import model.DocumentAssistant;
import model.RevisionAssistant;
import model.User;
import model.UserAssistant;
import networking.*;
import view.ServerView;

import javax.swing.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/* Test Username: cat Password = meow */
/*
 * Written By Taylor Cox
 * 
 * This class handles several things.
 * 1: Connecting to the server
 * 2. Account Login/Creation/Recovery
 * 3. Communication to the server for text/chat updates
 * 
 */

public class CEServer extends JFrame implements Serializable {
	/*
	 * Private Instance Variables
	 */
	private static final long serialVersionUID = 1L;
	public DocumentAssistant masterList;
	private transient static ServerSocket ourServer;
	private List<String> allChatMessages;
	public transient HashMap<String, ObjectOutputStream> outputs;
	public transient HashMap<String, ObjectInputStream> inputs;
	public transient HashMap<String, Thread> clients;
	private UserAssistant theUsers;
	private ArrayList<String> activeUsers;
	private transient ServerView ourView;
	private RevisionAssistant serverRevassist;

	/*
	 * Initializes the variables Gets a port, and jamborees with the view as
	 * needed If a failed attempt to setup, uses a default port.
	 */
	public CEServer() {
		readFromFile();
		// initVariables();
		startServer();
	}

	public void readFromFile() {
		boolean noPreviousConfig = false;

		String loadFileName = JOptionPane.showInputDialog("Enter The Name Of The Previous Saved Server State\nLeave Blank For a New Server");

		if (loadFileName != null && !loadFileName.equals("")) {
			ObjectInputStream loadStream = null;

			try {
				loadStream = new ObjectInputStream(new FileInputStream(loadFileName));
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "There is no file by that name. Starting new server.");
				noPreviousConfig = true;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Bad file.");
				noPreviousConfig = true;
			}

			CEServer loadedController = null;

			try {
				if (loadStream != null)
					loadedController = (CEServer) loadStream.readObject();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "The class's ID has changed.");
				noPreviousConfig = true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ClassCastException cce) {
				JOptionPane.showMessageDialog(null, "That file does not contain a saved CEServer. Starting New");
				noPreviousConfig = true;
			}

			if (loadStream != null) {
				try {
					loadStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (loadedController != null) {
				this.activeUsers = loadedController.activeUsers;
				this.ourView = new ServerView(this); // New Server View
				this.outputs = new HashMap<String, ObjectOutputStream>();
				this.theUsers = loadedController.theUsers;
				this.allChatMessages = loadedController.allChatMessages;
				this.masterList = loadedController.masterList;
				this.inputs = new HashMap<String, ObjectInputStream>();
				this.clients = new HashMap<String, Thread>();
				this.serverRevassist = loadedController.serverRevassist;

			}
		} else {
			noPreviousConfig = true;
		}
		if (noPreviousConfig) {
			this.activeUsers = new ArrayList<String>();
			this.ourView = new ServerView(this); // New Server View
			this.outputs = new HashMap<String, ObjectOutputStream>();
			this.theUsers = new UserAssistant();
			this.allChatMessages = new ArrayList<String>();
			this.masterList = new DocumentAssistant(); // List of text panel
			this.theUsers.addUser("cat", "meow"); // A Default account to use.
			this.inputs = new HashMap<String, ObjectInputStream>();
			this.clients = new HashMap<String, Thread>();
			this.serverRevassist = new RevisionAssistant();

		}

	}

	public void startServer() {
		// Setting up the Server.
		int portNumber = ourView.getPortNumber();
		try { // Tries to start the server on the given port
			ourServer = new ServerSocket(portNumber);
			ourView.roundTwo();
			// Now time to read from a file!
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

	public void stopServer() {
		LogoutPacket lGP = new LogoutPacket();

		for (String usr : activeUsers) {
			ObjectOutputStream out = outputs.get(usr);
			try {
				out.writeObject(lGP);
				this.save();
				System.exit(0);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	public void userLeft(String usr) {
		Thread threadtoKill = clients.get(usr);
		activeUsers.remove(usr);
		ourView.updateClients(activeUsers);

		threadtoKill.stop();

	}

	public void kickUser(String user) throws InterruptedException {
		LogoutPacket lGP = new LogoutPacket();
		ObjectOutputStream out = outputs.get(user);
		Thread threadtoKill = clients.get(user);
		try {
			out.writeObject(lGP);
			threadtoKill.stop();
			activeUsers.remove(user);
			ourView.updateClients(activeUsers);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void initVariables() {
	// if (!previousConfig) {
	// this.activeUsers = new ArrayList<String>();
	// this.ourView = new ServerView(this); // New Server View
	// this.outputs = new HashMap<String, ObjectOutputStream>();
	// this.theUsers = new UserAssistant();
	//
	// allChatMessages = new ArrayList<String>();
	// masterList = ""; // List of text panel
	// this.theUsers = new UserAssistant(); // TODO: Read from file
	// theUsers.addUser("cat", "meow"); // A Default account to use.
	// }
	//
	// }

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
				int executeValue = userLogin.execute(theUsers);
				User toPass = null; // The user whom the controller is to be set
									// up for
				// Writes login success to client
				output.writeObject(executeValue);
				if (executeValue == 0) {
					// We can login the user
					toPass = theUsers.getUser(userLogin.getName());
				} else if (executeValue == 1) {
					boolean success = false;
					while (!success) { // Means we have a wrong password
						RecoverPacket toRecover = (RecoverPacket) input.readObject();
						success = toRecover.execute(theUsers);
						output.writeObject(success);
						if (success) {
							toPass = theUsers.getUser(toRecover.getUserName());
							break;

						}
					}

				}
				if (executeValue == 2) {
					NewUserPacket userToAdd = (NewUserPacket) input.readObject();
					toPass = userToAdd.execute(theUsers);

				}
				userName = userLogin.getName();
				// Writes out the User Object
				output.writeObject(toPass);
				// output.writeObject(new Doc(userName, executeValue,
				// executeValue, null)); // Writes
				// out
				// the
				// current
				// List
				ChatPacket toWrite = new ChatPacket(allChatMessages);
				output.writeObject(toWrite);
				outputs.put(userLogin.getName(), output); // Puts on output map
				inputs.put(userLogin.getName(), input); // Puts on Input Map

				clientInit(); // Adds user to session so server can keep track

				// New thread to deal with user
				Thread temp = new Thread(new ClientHandler(input, output, toPass));
				temp.start();
				clients.put(userName, temp);

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
	private class ClientHandler implements Runnable, Serializable {
		/**
         *
         */
		private static final long serialVersionUID = 1L;
		private ObjectInputStream clientInputStream;
		private ObjectOutputStream clientOutputStream;
		private User mainUser;

		public ClientHandler(ObjectInputStream inputArg, ObjectOutputStream outputArg, User user) {
			clientInputStream = inputArg;
			clientOutputStream = outputArg;
			mainUser = user;
		}

		@Override
		public void run() {

			while (true) {
				try {

					Object temp = clientInputStream.readObject();
					// Reads packet from the controller
					if (temp instanceof EditPacket) {
						EditPacket readPacket = (EditPacket) temp;
						// Executes the packet
						readPacket.execute(masterList);
						masterList.getList().get(readPacket.getDocID()-1).setRevision(readPacket.getRev());
						readPacket.setMaster(masterList);
						// Checks to see if we even have something aka not null.
						// if (readPacket.getNewText().equals("")) {
						// Writes it out to ALL of the Client's
						for (ObjectOutputStream OPtemp : outputs.values()) {
							OPtemp.reset();
							OPtemp.writeObject(readPacket);
						}
						// }
					}
					// If the packet is a chat packet
					else if (temp instanceof ChatPacket) {
						ChatPacket newChat = (ChatPacket) temp;
						newChat.setCurrent(allChatMessages);
						allChatMessages = newChat.execute();
						try {
							for (ObjectOutputStream out : outputs.values()) {
								out.reset();
								out.writeObject(newChat);

							}
						} catch (Exception e) {
							e.printStackTrace();

						}
					} else if (temp instanceof CreateNewDocument) {
						CreateNewDocument newPacket = (CreateNewDocument) temp;
						DocumentAssistant tempV = masterList;
						masterList = newPacket.execute(tempV);
						RevisionAssistant newDocRev = new RevisionAssistant();
						EditPacket newEdit = new EditPacket(mainUser, newPacket.getDocID(), newPacket.getName(), newDocRev);
						masterList.getList().get(newEdit.getDocID() - 1).setRevision(newEdit.getRev());
						newEdit.setDocName(newPacket.getName());
						newEdit.setMaster(masterList);
						clientOutputStream.reset();
						clientOutputStream.writeObject(newPacket);
						clientOutputStream.writeObject(newEdit);
					} else if (temp instanceof LogoutPacket) {
						LogoutPacket userQuitPacket = (LogoutPacket) temp;
						userQuitPacket.quit(CEServer.this);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void save() {
		ObjectOutputStream saveStream = null;
		String fileName = "";

		while (fileName.equals("")) {
			fileName = JOptionPane.showInputDialog("Enter the name of the file you'd like to save to.");
			if (fileName == null) {
				return;
			}
		}

		// Create the stream and file to save to.
		try {
			saveStream = new ObjectOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Write the model to the file.
		try {

			CEServer toWrite = this;
			saveStream.writeObject(this);

			JOptionPane.showMessageDialog(this, "File Successfully Saved!");
			saveStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new CEServer();
	}
}
