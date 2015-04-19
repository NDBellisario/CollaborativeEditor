package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setupGui();
		
	}
	public void serverConnect()
	{

	}
	private void setupGui() {
		
		//Initializing graphic user interface variables 
		mainProgPanel = new JPanel();
		menuBarCore = new JMenuBar();
		fileContainer = new JMenu("File");
		editContainer = new JMenu("Edit");
		userContainer = new JMenu("User");
		save = new JMenuItem("Save");
		saveLocal = new JMenuItem("Save Local");
		quitOption = new JMenuItem("Quit");
		undo = new JMenuItem("Undo");
		redo = new JMenuItem("Redo");
		version = new JMenuItem("Version");
		addUser = new JMenuItem("Add User");
		removeUser = new JMenuItem("Remove User");
		changePermission = new JMenuItem("Permissions Options");
		
	
		
		this.setTitle("Collaborative Editor");
		
		//Adding action listeners for File
		quitOption.addActionListener(new ExitListener());
		save.addActionListener(new SaveListener());
		saveLocal.addActionListener(new SaveLocalListener());
	
		//Adding Action Listeners for Edit
		undo.addActionListener(new UndoListener());
		redo.addActionListener(new RedoListener());
		version.addActionListener(new VersionListener());
		
		//Adding Action Listener for User
		addUser.addActionListener(new AddUserListener());
		removeUser.addActionListener(new RemoveUserListener());
		changePermission.addActionListener(new PermissionListener());
		
		//add main menu buttons to bar
		menuBarCore.add(fileContainer);
		menuBarCore.add(editContainer);
		menuBarCore.add(userContainer);
		
		//fileContainer sub menu buttons
		fileContainer.add(save);
		fileContainer.add(saveLocal);
		fileContainer.add(quitOption);
		
		//editContainer sub menu buttons
		editContainer.add(undo);
		editContainer.add(redo);
		editContainer.add(version);
		
		//userContainer sub menu buttons
		userContainer.add(addUser);
		userContainer.add(removeUser);
		userContainer.add(changePermission);
		
		//Add menu bar
		this.setJMenuBar(menuBarCore);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Add JPanels 
		
		
		
		//pack and create!
		this.pack();
		this.setVisible(true);
	}
	

	//Listener Private Classes
	private class ExitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	private class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class SaveLocalListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	private class UndoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	private class RedoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	private class VersionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class AddUserListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class RemoveUserListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class PermissionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
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
		CEController CEC = new CEController(null, null, null);
		
		

	}
}
