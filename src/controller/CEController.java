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

import javax.swing.*;

import model.*;
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

/*
 * Packet Order!
 * 
 * 1.	Sent: LogInPacket
 * 			Username and Password of the User
 * 2.	Recieved: Boolean Value
 * 			True = Login Successful, False = Info doesn't match
 * 3.	Recieved: The User object of who connected
 * 			The Client then should create GUI based on this User
 * 4.	Sent
 * 			New Text Packet
 * 5.	Recieved:
 * 			New updated master list
 * 
 * Second Packet 
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
	private User user;
	private JPanel chatView;
	private EditView editView;
	JDialog serverconnect;
	private Socket serversoc;
	private ObjectOutputStream outputStrm;
	private ObjectInputStream inputStrm;
	private String serverAddress;
	private String port;
	public CEController(ChatAssistant theChat, EditAssistant editAs, UserAssistant theUser) {
		initUserModels();
	}
	/* Connects to the server and makes sure the login info matches an account */
	private void initUserModels() {
		String userName = JOptionPane.showInputDialog("Username");
		String passWord = JOptionPane.showInputDialog("Password");
		LoginPacket lPK = new LoginPacket(userName, passWord);
		enterCredentials();
		try {
			serversoc = new Socket(serverAddress, Integer.parseInt(port));
			outputStrm = new ObjectOutputStream(serversoc.getOutputStream());
			inputStrm = new ObjectInputStream(serversoc.getInputStream());
			outputStrm.writeObject(lPK);
			if ((boolean) inputStrm.readObject()) {
				user = (User) inputStrm.readObject();
				
				setupGui();
				new Thread(new ServerRevisionWrite()).start();
				new Thread(new ServerRevisionRead()).start();
			} else {
				user = (User) inputStrm.readObject();
				JOptionPane.showMessageDialog(this, "Non Exisiting Acocunt!\n New Account Made!");
				setupGui();
				new Thread(new ServerRevisionWrite()).start();
				new Thread(new ServerRevisionRead()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* Deals with server connecting from user input */
	private void enterCredentials() {
		boolean enteredVariables = false;
		serverAddress = null;
		port = null;
		while (!enteredVariables) {
			serverAddress = JOptionPane.showInputDialog("IPAddress");
			port = JOptionPane.showInputDialog("Port ID");
			if (serverAddress.length() >= 8 && port.length() >= 4) {
				return;
			}
			JOptionPane.showMessageDialog(null, "No Server information Try again");
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
		chatView = new ChatView(user.getUserName());
		editView = new EditView(user.getUserName() + " Client");
		this.setLayout(new BorderLayout());
		this.add(chatView, BorderLayout.EAST);
		this.add(editView, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// pack and create!
		this.pack();
		this.setVisible(true);
		JOptionPane.showMessageDialog(this, "Welcome Back!");
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
	/* Once connection is set up, this deals with the server comms */
	private class ServerRevisionWrite implements Runnable {
		Timer timer = new Timer(3000, null);

		public void run() {
			/*
			 * This class needs to use a .sleep() function instead of a timer.
			 * 
			 * Also it needs to use packets and write that out to the server, in
			 * which the server will then execute
			 * 
			 * Every 5 seconds when it autosaves, send a packet to the server,
			 * which the server executes (creates new Revison on the stack) and
			 * will send back a String of the new master document view.
			 * 
			 * What happens when 2+ clients are all trying to send to the server
			 * and stuck on read. How does the serve prioritize? The server only
			 * "reads" input from one client then moves on in code execution. So
			 * if two clients write a packet at the same time, only one will be
			 * picked up server side.
			 * 
			 * My Solution:
			 * 
			 * One thread every 5 seconds sends an updated view to Server via
			 * packet, Server executes the code and adds it to the master list,
			 * Server then writes out new master list which the second thread
			 * picks up and updates GUI of all connected clients.
			 */
			while (true) {
				/* Following Code Is A Test, Feel Free To Change */
				EditPacket newTimedRevision = new EditPacket(editView);

				try {
					outputStrm.writeObject(newTimedRevision);
					Thread.sleep(5000);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// if (!timer.isRunning()) {
				// try {
				// outputStrm.writeObject(editView.getText());
				// timer.restart();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// System.out.println("hey");
				// }
				// try {
				// editView.setText((String) inputStrm.readObject());
				// } catch (ClassNotFoundException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}
	}
	/* This class will get new revisions and update GUI */
	private class ServerRevisionRead implements Runnable {

		@Override
		public void run() {
			try {
				editView.setText((String) inputStrm.readObject());
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	public static void main(String[] args) {
		CEController CEC = new CEController(null, null, null);
	}
}
