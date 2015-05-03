package server;
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
    public static String masterList;
    private static ServerSocket ourServer;
    private static List<String> allChatMessages;
    public HashMap<String, ObjectOutputStream> outputs;
    private UserAssistant theUsers;
    private ArrayList<String> activeUsers;
    private ServerView ourView;

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



    public void initVariables() {
        this.activeUsers = new ArrayList<String>(); // log of connected users
        this.outputs = new HashMap<String, ObjectOutputStream>(); // the outputs
        RevisionAssistant revStack = new RevisionAssistant(); // TODO: Revision
        allChatMessages = new ArrayList<String>();
        masterList = ""; // List of text panel
        this.theUsers = new UserAssistant(); // TODO: Read from file
        theUsers.addUser("cat", "meow"); // A Default account to use.
        ourView = new ServerView(theUsers, this); // New Server View

    }

    /*
     * This class accepts connections from the server, gathers the streams of
     * the socket, and passes it onto a new thread who's goal is to initialize
     * the connection and have the user login
     */

    public void updateConnected() {
        try {
            for (ObjectOutputStream out : outputs.values()) {
                out.reset();
                out.writeObject(allChatMessages);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

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
                User toPass = null; // The user whom the controller is to be set up for
                // Writes login success to client
                output.writeObject(executeValue);
                if (executeValue == 0) {
                    // We can login the user
                    toPass = theUsers.getUser(userLogin.getName());
                } else if (executeValue == 1) {
                    boolean success = false;
                   while(!success) { // Means we have a wrong password
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
                output.writeObject(masterList); // Writes out the current List
                output.writeObject(allChatMessages);
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
    private class ClientHandler implements Runnable, Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
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
                    Object temp = inputStream.readObject();
                    // Reads packet from the controller
                    if (temp instanceof EditPacket) {
                        EditPacket readPacket = (EditPacket) temp;
                        // Executes the packet
                        String newText = readPacket.execute();
                        // Checks to see if we even have something aka not null.
                        if (!newText.equals("")) {
                            // Writes it out to ALL of the Client's
                            for (ObjectOutputStream OPtemp : outputs.values()) {
                                masterList = newText;
                                OPtemp.writeObject(masterList);
                            }
                        }
                    }
                    // If the packet is a chat packet
                    else if (temp instanceof ChatPacket) {
                        ChatPacket newChat = (ChatPacket) temp;
                        newChat.setCurrent(allChatMessages);
                        allChatMessages = newChat.execute();
                        updateConnected();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void save(){
        ObjectOutputStream saveStream = null;
        String fileName = "";

        while(fileName.equals("")){
            fileName = JOptionPane.showInputDialog("Enter the name of the file you'd like to save to.");
            if(fileName == null){
                return;
            }
        }

        //Create the stream and file to save to.
        try {
            saveStream = new ObjectOutputStream(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write the model to the file.
        try {
            saveStream.writeObject(this+".ser");
            JOptionPane.showMessageDialog(this, "File Successfully Saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Make sure to close the stream!!!1!
        try {
            saveStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
