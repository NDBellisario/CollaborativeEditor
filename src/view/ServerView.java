package view;
import model.UserAssistant;
import server.CEServer;
import sun.awt.WindowClosingListener;

import javax.swing.*;

import controller.CEController;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;

/*
 * Written by Taylor Cox
 * This is the server view
 * 
 * Essentially instead of annoying popups, all the server's
 * stuff can be found here.  Server controller also has access to boot users,
 * or see their documents.  Kind of like a suepr cool admin
 */
public class ServerView extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    DefaultListModel<String> userList;
    private JPanel mainStuff, userStuff, buttons;
    private JMenuBar theMenuBar;
    //private UserAssistant passedUserAssistant;
    private String portNumber;
    private CEServer ourServer;
    private JList<String> currentUsersTemp;
    

    /*
     * This sets up the menu, and out blank document before asking for a port to
     * use
     */
    public ServerView(CEServer serverArg) {
        //passedUserAssistant = arg;
        setUpMenu();
        setPref();
        //TODO: portNumber = JOptionPane.showInputDialog(this, "Please Enter A Port To Host On");
        portNumber = "9001";
        ourServer = serverArg;

    }

    /*
     * This means our server was successfully able to start up, so now we can
     * set up all of the view and controls
     */
    public void roundTwo() {
        setUpStats();
        this.add(mainStuff, BorderLayout.NORTH);
        this.add(userStuff, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        setPref();
    }

    /*
     * This tells a user that the port they gave was bad and defaults to one to
     * use
     */
    public void youScrewedUp() {
        JOptionPane.showMessageDialog(this, "Somethings Wrong With That Port\nDefaulting To 9002");
        portNumber = "9002";
    }

    /*
     * This occurs when a user connects TODO: Make it say their actual name.
     */
    public void userConnect() {
        //JOptionPane.showMessageDialog(this, "User Connected!");
    }

    /*
     * This lays out the display of the server GUI
     */
    public void setPref() {

        this.setJMenuBar(theMenuBar);
        this.setTitle("CollaborativeEditor Server Control");
        this.setResizable(false);
        this.pack();
        this.setSize(600, 400);
        this.setVisible(true);// display this JFrame
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// kill the application
        this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent evt){
			ourServer.stopServer();
			}
		});
        // when the window is
        // closed
        getContentPane().setBackground(Color.BLACK);
    }
    
    

    /*
     * Menu settings TODO: Make more and make them work
     */
    public void setUpMenu() {

        // setup the JMenu
        theMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);
        saveItem.addActionListener(new SaveActionListener());
        JMenuItem aboutItem = new JMenuItem("About");// create our about menu
        // item
        // aboutItem.addActionListener(new AboutActionListener());//action
        // listener that will display our "about" dialog
        fileMenu.add(aboutItem);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new QuitActionListener());// action
        // listener to quit the application
        fileMenu.add(quitItem);

        theMenuBar.add(fileMenu);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*
     * This sets up the info of who is on our server!
     */
    public void setUpStats() {
        mainStuff = new JPanel();
        userStuff = new JPanel();
        JLabel serverStats = new JLabel("Server now started on port: " + portNumber);
        serverStats.setFont(new Font("Ariel", Font.PLAIN, 30));
        serverStats.setForeground(Color.RED);

        JLabel userStats = new JLabel("Connected Users:");
        userStats.setFont(new Font("Ariel", Font.BOLD, 25));

        userStats.setForeground(Color.WHITE);
        userList = new DefaultListModel<String>();
       
        // TODO: Uncommment
        // ArrayList<String> theUsers = passedUserAssistant.getUsers();
        ArrayList<String> theUsers = new ArrayList<String>();
        // ^^
        for (String temp : theUsers)
            userList.addElement(temp);
        currentUsersTemp = new JList<String>(userList);
        currentUsersTemp.setFont(new Font("Arial", Font.BOLD, 20));
        JScrollPane currentUsers = new JScrollPane(currentUsersTemp);
        currentUsers.setPreferredSize(new Dimension(400, 200));

        // Making buttons
        JButton viewDocuments = new JButton("View User's Documents");
        viewDocuments.addActionListener(new ButtonListener());
        JButton kickFromSession = new JButton("Kick User(s) From Session");
        kickFromSession.addActionListener(new ButtonListener());
        JButton endServer = new JButton("Stop The Server");
        endServer.addActionListener(new ButtonListener());
        // Adding Stuff to Panel
        mainStuff.add(serverStats, BorderLayout.NORTH);
        userStuff.add(userStats);
        userStuff.add(currentUsers);

        // Adding Buttons
        buttons = new JPanel();
        buttons.add(viewDocuments);
        buttons.add(kickFromSession);
        buttons.add(endServer);
        // Setting Colors
        mainStuff.setBackground(Color.BLACK);
        buttons.setBackground(Color.BLACK);
        userStuff.setBackground(Color.BLACK);

    }

    /*
     * How the CEServer gets the port
     */
    public int getPortNumber() {

        if (portNumber.equals("")) {
            portNumber = "9001";
            return 9001;
        } else
            return Integer.parseInt(portNumber);

    }

    /*
     * Called when a new client is connected
     */
    public void updateClients(ArrayList<String> activeUsers) {
        userList.clear();
        for (String temp : activeUsers)
            userList.addElement(temp);
    }
 

    // quits Server connection
    private class QuitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
        	ourServer.stopServer();

            
        }

    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            String s = ((JButton) arg0.getSource()).getText();
            if (s.equals("View User's Documents")) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(mainStuff), "Unimplemented But Will Show User's Docs!");
            } else if (s.equals("Kick User(s) From Session")) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(mainStuff), "Kicking " + currentUsersTemp.getSelectedValue());
                try {
					ourServer.kickUser(currentUsersTemp.getSelectedValue());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            } else if (s.equals("Stop The Server")) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(mainStuff), "Clicking 'OK' Will Close The Server");
                //				for(ObjectOutputStream arg : CEServer.outputs.values())
                //						{
                //							try {
                //								arg.close();
                //								arg.
                //							} catch (IOException e) {
                //								// TODO Auto-generated catch block
                //								e.printStackTrace();
                //							}
                //						}
                
                ourServer.stopServer();
                System.exit(0);

            }
        }
    }

    private class SaveActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
           ourServer.save();

        }
    }
}
