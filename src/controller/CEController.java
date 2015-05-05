package controller;
import model.*;
import networking.*;
import view.ChatView;
import view.DocumentView;
import view.EditView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;


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
    private JMenuItem changePW;
    private JMenuItem removeUser;
    private JMenuItem changePermission;
    private JMenuItem showOptions;
    private User mainUser;
    private ChatView chatView;
    private EditView editView;
    private Socket serversoc;
    private ObjectOutputStream outputStrm;
    private ObjectInputStream inputStrm;
    private int currentSelectedDoc;
    private ArrayList<Doc> ourDocs;

    public CEController(ChatAssistant theChat, EditAssistant editAs, UserAssistant theUser) {
        initUserModels();
    }

    public static void main(String[] args) {
        new CEController(null, null, null);
    }

    /* Connects to the server and makes sure the login info matches an account */
    private void initUserModels() {
        // Setting up hashing


        // Setting up the main data entry fields, un/pw/server stuff
        ourDocs = new ArrayList<Doc>();
        JTextField ipField = new JTextField();
        JTextField portField = new JTextField();
        JTextField nameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        JPasswordField passwordConfirm = new JPasswordField();
        passwordConfirm.setEchoChar('*');
        String userName = "cat"; // TODO: Delete this+4 lines down
        String passWord = "meow";
        String serverAddress = "localhost";
        String port = "9001";
        String confirmedPW = "";
        // The popup asking for credentials
        // Also deals with getting the text and setting it
        Object[] message = {"Please Enter The Required Credentials\n\n", "Server:", ipField, "Port:", portField, "Username:", nameField, "Password:", passwordField, "Confirm Password\n(Or Fill Out To Create Account)", passwordConfirm};
        int option = JOptionPane.showConfirmDialog(this, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (!ipField.getText().equals(""))
                if (!ipField.getText().equals(""))
                    serverAddress = ipField.getText();
            if (!portField.getText().equals(""))
                port = portField.getText();
            if (!nameField.getText().equals(""))
                userName = nameField.getText();
            if (!(new String(passwordField.getPassword()).equals("")))
                passWord = new String(passwordField.getPassword());
            //
            confirmedPW = new String(passwordField.getPassword());
            if (!confirmedPW.equals("")) {
                NewUserPacket newUSR = new NewUserPacket(userName, passWord);
                //                if(!confirmedPW.equals(passWord)){
                //
                //                    JOptionPane.showMessageDialog(this, "Passwords Do Not Match!");
                //                }
            }
        }
        // Login packet based off of the previously fields
        byte[] passByte = null;

        try {
            MessageDigest hashSha = MessageDigest.getInstance("SHA-1");

            passByte = hashSha.digest(passWord.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        LoginPacket lPK = new LoginPacket(userName, passByte);
        try {
            // Connects to server, gets Streams, and writes out the packet
            serversoc = new Socket(serverAddress, Integer.parseInt(port));
            outputStrm = new ObjectOutputStream(serversoc.getOutputStream());
            inputStrm = new ObjectInputStream(serversoc.getInputStream());
            outputStrm.writeObject(lPK);
            // Reads whether or not the login was a succcess/
            int toTest = (int) inputStrm.readObject();

            if (toTest == 1) {
                boolean endLoop = false;
                while (!endLoop) {
                    JTextField unField = new JTextField();
                    JPasswordField pwField = new JPasswordField();
                    pwField.setEchoChar('*');
                    Object[] recoverAcct = {"Invalid Password\n", "Please Enter Username And Desired New Password", unField, pwField};
                    int option2 = JOptionPane.showConfirmDialog(this, recoverAcct, "ERROR", JOptionPane.OK_CANCEL_OPTION);

                    if (option2 == JOptionPane.OK_OPTION) {
                        RecoverPacket toPass = new RecoverPacket(unField.getText(), new String(pwField.getPassword()));
                        outputStrm.writeObject(toPass);
                        boolean recoverStatus = (boolean) inputStrm.readObject();
                        if (recoverStatus) {
                            JOptionPane.showMessageDialog(this, "Password Successfully Set, Logging In!!");
                            endLoop = true;
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid Username\nPlease Try Again.");
                        }
                    }
                    //TODO: If cancel?
                }
            }
            if (toTest == 2) {
                JOptionPane.showMessageDialog(this, "Account Doesn't Exist!\nClick 'OK' To Create Account With Previous Input");

                NewUserPacket newUser = new NewUserPacket(userName, passWord);
                outputStrm.writeObject(newUser);

            }

            mainUser = (User) inputStrm.readObject();
            setupGui();
            // Sets text field with default values and starts thread
            Doc doc2Set = (Doc) inputStrm.readObject();
            editView.setText(doc2Set.getDocContents());//new Doc("Test Doc",12345, 1, null))
            ChatPacket temp = (ChatPacket) inputStrm.readObject();
            List<String> toSet = temp.getChats();
            updateChat(toSet);
            new Thread(new ServerRevisionWrite()).start();
            new Thread(new ServerCommunicator()).start();
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Sets up le chat.

    }

    /* Setups the GUI */
    private void setupGui() {
        // Permissions Pop up
        // Initializing graphic user interface variables
        menuBarCore = new JMenuBar();
        fileContainer = new JMenu("File");
        editContainer = new JMenu("Edit");
        userContainer = new JMenu("User");
        documentContainer = new JMenu("Doc Editor");
        save = new JMenuItem("Save");
        saveLocal = new JMenuItem("Save Local");
        quitOption = new JMenuItem("Quit");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        version = new JMenuItem("Version");
        addUser = new JMenuItem("Add User");
        changePW = new JMenuItem("Change Password");
        removeUser = new JMenuItem("Remove User");
        changePermission = new JMenuItem("Permissions Options");
        showOptions = new JMenuItem("Show Documents");
        this.setTitle("Collaborative Editor");
        // Adding action listeners for File
        quitOption.addActionListener(new ExitListener());
        save.addActionListener(new SaveListener());
        saveLocal.addActionListener(new SaveLocalListener());
        // Adding Action Listeners for Edit
        undo.addActionListener(new UndoListener());
        redo.addActionListener(new RedoListener());
        version.addActionListener(new VersionListener());
        // Adding Action Listener for User
        changePW.addActionListener(new ChangePWListener());
        addUser.addActionListener(new AddUserListener());
        removeUser.addActionListener(new RemoveUserListener());
        changePermission.addActionListener(new PermissionListener());
        // Adding Action Listener for Doc Editor
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
        // userContextBox.setText("<p style=\"color:red\">This is a paragraph.</p>");tainer sub menu buttons
        userContainer.add(changePW);
        userContainer.add(addUser);
        userContainer.add(removeUser);
        userContainer.add(changePermission);
        // Add menu bar
        this.setJMenuBar(menuBarCore);
        // Add ChatView
        chatView = new ChatView(mainUser, outputStrm);
        editView = new EditView(mainUser);
        //editView.setText("<p style=\"color:red\">This is a paragraph.</p>");
        this.setLayout(new BorderLayout());
        this.add(chatView, BorderLayout.EAST);
        this.add(editView, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // pack and create!
        this.pack();
        this.setVisible(true);

    }

    public void updateChat(List<String> allMessages) {

        chatView.updateChatPanel(allMessages);
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
            Doc temp = new Doc("Test Doc",12345, 1, null);
            ourDocs.add(temp);
            //currentSelectedDoc = temp;
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

    private class ChangePWListener implements ActionListener {
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
                    if(mainUser.selectedDoc != 0) {
                        EditPacket newTimedRevision = new EditPacket(editView, mainUser, currentSelectedDoc);
                        outputStrm.writeObject(newTimedRevision);
                        Thread.sleep(777); // Only want to send every 2000 ms.
                    }
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
    private class ServerCommunicator implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // Sets text to the ReadIn
                    // Object unknown = inputStrm.readObject();
                    Object unknown = inputStrm.readObject();

                    if ((unknown instanceof EditPacket)) {
                        EditPacket newPacket = (EditPacket) unknown;
                        if ((newPacket.getDocID() == currentSelectedDoc) && newPacket.getDocID() !=0) {

                            editView.setText(newPacket.getNewText());
                        }
                    } else if (unknown instanceof ChatPacket)

                    {
                        @SuppressWarnings("unchecked") List<String> toSet = (ArrayList<String>) ((ChatPacket) unknown).getChats();
                        updateChat(toSet);

                    }
                    else if(unknown instanceof GetDocsPacket){
                        GetDocsPacket newPacket = (GetDocsPacket)  unknown;
                        displayCurrentDocs(newPacket);
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

            GetDocsPacket toSend = new GetDocsPacket(mainUser);
            try {
				outputStrm.writeObject(toSend);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

        }
    }
    private void displayCurrentDocs(GetDocsPacket arg){

        ourDocs = arg.getList();
        DefaultListModel docList = new DefaultListModel();
        for (int i = 0; i < ourDocs.size(); i++) {
            docList.addElement(ourDocs.get(i).getDocName());
        }

        JList currentDocTemp = new JList<String>(docList);
        JScrollPane currentDocs= new JScrollPane(currentDocTemp);

        JFrame frame = new JFrame();

        frame.add(currentDocs, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
