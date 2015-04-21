package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.*;

import model.*;
import view.*;
import networking.*;
public class CEController extends JFrame implements Serializable
{

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
	private JPanel editView;
	JDialog serverconnect;
	private Socket serversoc;
	private ObjectOutputStream outputStrm;
	private ObjectInputStream inputStrm;
	private String serverAddress;
	private String port;
	public CEController(ChatAssistant theChat, EditAssistant editAs, UserAssistant theUser)
	{
		initUserModels();
		
		
	}
	private void initUserModels(){
		String userName=JOptionPane.showInputDialog("Username");
		String passWord=JOptionPane.showInputDialog("Password");
		
		LoginPacket lPK = new LoginPacket(userName, passWord);
		enterCredentials();
		try{
			serversoc = new Socket(serverAddress, Integer.parseInt(port));
			outputStrm = new ObjectOutputStream(serversoc.getOutputStream());
			inputStrm = new ObjectInputStream(serversoc.getInputStream());
			
			outputStrm.writeObject(lPK);
			
			if((boolean)inputStrm.readObject()){
				setupGui();
				new Thread(new ServerListener(serversoc)).start();
			}else{
				JOptionPane.showMessageDialog(null, "Invald Account!");
				JOptionPane.showInternalConfirmDialog(null, "Make Account");
				
			}
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	
		
	}
	private void enterCredentials(){
		boolean enteredVariables=false;
		serverAddress = null;
		port = null;
	
		while(!enteredVariables){
			serverAddress = JOptionPane.showInputDialog("IPAddress");
			port = JOptionPane.showInputDialog("Port ID");
			if (serverAddress.length()>=8 && port.length()>=4){
				return;
			}
		JOptionPane.showMessageDialog(null, "No Server information Try again");
		}
	}
	
	private void setupGui() {
		//Permissions Pop up

		
		//Initializing graphic user interface variables 
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
		
		
		this.setTitle("brahhh");
		
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
		
		//Adding Action Listener for Document Editor
		showOptions.addActionListener(new showOptionsListener());
		
		//add main menu buttons to bar
		menuBarCore.add(fileContainer);
		menuBarCore.add(editContainer);
		menuBarCore.add(userContainer);
		menuBarCore.add(documentContainer);

		//add document menu buttons
		documentContainer.add(showOptions);
		
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
		
		
		//Add ChatView
		 chatView = new ChatView(user.getUserName());
		 editView = new EditView(user.getUserName() + " Client");
		 this.setLayout(new BorderLayout());
		 this.add(chatView, BorderLayout.EAST);
		 this.add(editView, BorderLayout.CENTER);
		 

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
	private class showOptionsListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			String[] arr = new String[7];
			for(int i=0;i<7;i++){
				arr[i] = "String"+i;
			}
			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());
			DocumentView doc = new DocumentView(arr);
			frame.add(doc, BorderLayout.CENTER);
			frame.setVisible(true);
			frame.setResizable(true);
			frame.pack();
			frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			
		}
		
	}
	public static void main(String[] args)
	{	
		CEController CEC = new CEController(null, null, null);
		
		

	}
}
