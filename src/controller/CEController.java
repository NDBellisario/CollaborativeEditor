package controller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

import javax.swing.*;

import model.*;
import server.CEServer;
import view.*;
import networking.*;

/* Author: Nick Bellisario
 * 
 * This class deals with the client connecting to the server via a 
 * username and password (which is checked against registered users.
 * 
 * Also sends/recieves packets when the user performs an action on the document
 * 
 */

public class CEController extends JFrame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBarCore;
	private JMenu fileContainer;
	private JMenu editContainer;
	private JMenu userContainer;
	private JMenu documentContainer;
	private JMenuItem save;
	private JMenuItem saveLocal;
	private JMenuItem quitOption;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem version;
	private JMenuItem addUser;
	private JMenuItem removeUser;
	private JMenuItem changePermission;
	private JMenuItem showOptions;
	private User mainUser;
	private ChatView chatView;
	private EditView editView;
	private Socket serversoc;
	private ObjectOutputStream outputStrm;
	private ObjectInputStream inputStrm;

	public static void main(String[] args) {
		new CEController(null, null, null);
	}

	public CEController(ChatAssistant theChat, EditAssistant editAs, UserAssistant theUser) {
		initUserModels();
	}
	

	
	/* Connects to the server and makes sure the login info matches an account */
	private void initUserModels() {
		// Setting up the main data entry fields, un/pw/server stuff
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field4 = new JTextField();
		String userName = "cat"; // TODO: Delete this+4 lines down
		String passWord = "meow";
		String serverAddress = "localhost";
		String port = "9001";
		// The popup asking for credentials
		// Also deals with getting the text and setting it
		Object[] message = {"Please Enter The Required Credentials\nTest Only, Leave Blank for Defaults\n\n", "Server:", field1, "Port:", field2, "Username:", field3, "Password:", field4,};
		int option = JOptionPane.showConfirmDialog(this, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			if (!field1.getText().equals(""))
				if (!field1.getText().equals(""))
					serverAddress = field1.getText();
			if (!field2.getText().equals(""))
				port = field2.getText();
			if (!field3.getText().equals(""))
				userName = field3.getText();
			if (!field4.getText().equals(""))
				passWord = field4.getText();
		}
		// Login packet based off of the previously fields
		LoginPacket lPK = new LoginPacket(userName, passWord);
		try {
			// COnnects to server, gets Streams, and writes out the packet
			serversoc = new Socket(serverAddress, Integer.parseInt(port));
			outputStrm = new ObjectOutputStream(serversoc.getOutputStream());
			inputStrm = new ObjectInputStream(serversoc.getInputStream());
			outputStrm.writeObject(lPK);
			// Reads whether or not the login was a succcess/
			boolean toTest = (boolean) inputStrm.readObject();
			// If sm = grab the User, set up GUI w EditView, welcome back!
			if (toTest) {
				mainUser = (User) inputStrm.readObject();
				setupGui();
				JOptionPane.showMessageDialog(this, "Welcome Back!");
				editView.setText((String) inputStrm.readObject());
				// Starts threads to read and write
				new Thread(new ServerRevisionWrite()).start();
				new Thread(new ServerRevisionRead()).start();
			} else {
				// This means that the user's info wasn't right
				field1.setText(""); // Default value
				// Deals with the popup that comes, can recover pw or create
				// account from previous data.
				Object[] invalidAccount = {"Invalid Account! What Would You Like To Do?\nLeave Blank To Create New Account\n\n", "OR:\tRecover (Enter Username):", field1};
				int option2 = JOptionPane.showConfirmDialog(this, invalidAccount, "ERROR", JOptionPane.OK_CANCEL_OPTION);
				if (option2 == JOptionPane.OK_OPTION) {
					// Writes to the Server what we chose and they send back
					// response
					outputStrm.writeObject(field1.getText());
					String recovery = (String) inputStrm.readObject();
					if (recovery.length() > 30)
						// In here means that the user account didn't already
						// exist
						JOptionPane.showMessageDialog(this, recovery + "\nLogging You In!");
					else if (!recovery.equals("404"))
						// Here means that the user account existed already
						JOptionPane.showMessageDialog(this, "Your Password:\n" + recovery + "\nLogging You In!");
				}
				// Reads in the main user from server
				mainUser = (User) inputStrm.readObject();
				setupGui();
				// Sets text field with default values and starts thread
				editView.setText((String) inputStrm.readObject());
				new Thread(new ServerRevisionWrite()).start();
				new Thread(new ServerRevisionRead()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Setups the GUI */
	private void setupGui() {
		// Permissions Pop up
		// Initializing graphic user interface variables
		menuBarCore = new JMenuBar();
		fileContainer = new JMenu("File");
		editContainer = new JMenu("Edit");
		userContainer = new JMenu("User");
		documentContainer = new JMenu("Document Editor");
		save = new JMenuItem("Save");
		saveLocal = new JMenuItem("Save Local");
		quitOption = new JMenuItem("Quit");
		undo = new JMenuItem("Undo");
		redo = new JMenuItem("Redo");
		version = new JMenuItem("Version");
		addUser = new JMenuItem("Add User");
		removeUser = new JMenuItem("Remove User");
		changePermission = new JMenuItem("Permissions Options");
		showOptions = new JMenuItem("Show Documents");
		this.setTitle("CE Checkpoint 1");
		// Adding action listeners for File
		quitOption.addActionListener(new ExitListener());
		save.addActionListener(new SaveListener());
		saveLocal.addActionListener(new SaveLocalListener());
		// Adding Action Listeners for Edit
		undo.addActionListener(new UndoListener());
		redo.addActionListener(new RedoListener());
		version.addActionListener(new VersionListener());
		// Adding Action Listener for User
		addUser.addActionListener(new AddUserListener());
		removeUser.addActionListener(new RemoveUserListener());
		changePermission.addActionListener(new PermissionListener());
		// Adding Action Listener for Document Editor
		showOptions.addActionListener(new showOptionsListener());
		// add main menu buttons to bar
		menuBarCore.add(fileContainer);
		menuBarCore.add(editContainer);
		menuBarCore.add(userContainer);
		menuBarCore.add(documentContainer);
		// add document menu buttons
		documentContainer.add(showOptions);
		// fileContainer sub menu buttons
		fileContainer.add(save);
		fileContainer.add(saveLocal);
		fileContainer.add(quitOption);
		// editContainer sub menu buttons
		editContainer.add(undo);
		editContainer.add(redo);
		editContainer.add(version);
		// userContainer sub menu buttons
		userContainer.add(addUser);
		userContainer.add(removeUser);
		userContainer.add(changePermission);
		// Add menu bar
		this.setJMenuBar(menuBarCore);
		// Add ChatView
		chatView = new ChatView(mainUser,outputStrm);
		editView = new EditView(mainUser);
		this.setLayout(new BorderLayout());
		this.add(chatView, BorderLayout.EAST);
		this.add(editView, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// pack and create!
		this.pack();
		this.setVisible(true);

	}
	// Listener Private Classes
	private class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	private class SaveListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class SaveLocalListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class UndoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class RedoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class VersionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class AddUserListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class RemoveUserListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	private class PermissionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}


	/* Once connection is set up, this deals writing out updates */
	private class ServerRevisionWrite implements Runnable {

		public void run() {
			while (true) {

				try {
					// New edit packet and write it out!
					EditPacket newTimedRevision = new EditPacket(editView, mainUser);
					outputStrm.writeObject(newTimedRevision);
					Thread.sleep(2000); // Only want to send every 2000 ms.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
	/* This class will get new revisions and update GUI */
	private class ServerRevisionRead implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					// Sets text to the ReadIn
					Object unknown = inputStrm.readObject());
					if(unknown instanceof String)
					{
						String toAdd = (String) unknown;
						editView.setText(unknown)
					}
					
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private class showOptionsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] arr = new String[7];
			for (int i = 0; i < 7; i++) {
				arr[i] = "String" + i;
			}
			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());
			DocumentView doc = new DocumentView(arr);
			frame.add(doc, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.setResizable(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	public void updateChat(List<String> allMessages){
		chatView.updateChatPanel(allMessages);
	}

}
