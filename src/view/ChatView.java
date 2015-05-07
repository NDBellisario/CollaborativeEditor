package view;
import model.*;
import networking.ChatPacket;
import networking.EditPacket;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Stack;

//import model.AddMessageCommand;
//import view.ChatPanel.EnterListener;


public class ChatView extends JPanel implements Serializable{

/**
 * Chat View 
 * @author NDBellisario
 * Main view or Chat and Revision panel for displaying current
 * revisions to user and as well the current message in the chat 
 */
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     *
     */

    private JPanel chatPanel;
    private JPanel typePanel;
    private JPanel revisionPanel;
    private JList revisions;


    private JButton loadRevision;
    private JButton sendButton;

    private JTextPane groupText, personalTest;
    private JTextArea textArea; // chat log displayed here
    private JTextArea textField; // field where user enters text
    private JTextArea whoIsTyping;
    private String username;
    private RevisionAssistant revView;
    private DefaultListModel<String> displayofRevisions;

    private ObjectOutputStream output;
/**
 * Constructor for panel 
 * @param user - User
 * @param outputSet - ObjectOutputStream
 * @param newRev - RevisionAssistant 
 */
    public ChatView(User user, ObjectOutputStream outputSet, RevisionAssistant newRev) {
 
        this.username = user.getUserName();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(400, 600));
        this.displayofRevisions = new DefaultListModel<String>();

        revView = newRev;
        revisionPanel = new JPanel(new BorderLayout());
        
        displayofRevisions.clear();
        if(revView != null){
        for(int i = 0; i < revView.revisionList.size(); i++){
        	EditPacket tempRev = revView.getStack().elementAt(i);
        	String temp = ("Revision by: " + tempRev.getUser().getUserName() + "At: " + tempRev.getTime().toString());
        	displayofRevisions.addElement(temp);
        }
        }
        revisions = new JList<String>(displayofRevisions);

        revisionPanel.add(revisions,BorderLayout.CENTER);
        
        loadRevision = new JButton("Load Revision");
        loadRevision.addActionListener(new loadRevisionListener());
        revisionPanel.add(loadRevision,BorderLayout.SOUTH);
        
        revisionPanel.add(new JLabel("Revisions", SwingConstants.CENTER), BorderLayout.NORTH);
        
        revisionPanel.setPreferredSize(new Dimension(280,250));
        
        this.add(revisionPanel,BorderLayout.NORTH);
        

        //Text area to carry the conversation text.
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        //Text field to
        textField = new JTextArea();
        textField.setEditable(true);
        textField.setLineWrap(true);
        JButton enterButton = new JButton("Send");

         textField.setPreferredSize(new Dimension(100, 250));
        // enterButton.setPreferredSize(new Dimension(100, 40));

        // create a listener for writing messages to server
        ActionListener listener = new chatSendListener();

        // attach listener to field & button
        //textField.addActionListener(listener);
        enterButton.addActionListener(listener);

        //Sets out stream
        this.output = outputSet;

        //Text area to show who is typing. NOT currently networked.
        whoIsTyping = new JTextArea();
        whoIsTyping.setEditable(false);
        whoIsTyping.setText(username + " is typing....");

        //Type panel at the bottom of the ChatView.
        typePanel = new JPanel();
        typePanel.setLayout(new BorderLayout());
        typePanel.setPreferredSize(new Dimension(280, 100));
        //typePanel.add(textField, BorderLayout.CENTER);
        typePanel.add(new JScrollPane(textField), BorderLayout.CENTER);
        typePanel.add(enterButton, BorderLayout.SOUTH);
        typePanel.add(whoIsTyping, BorderLayout.NORTH);

        //Adding the type and chat areas, and the Chat title.
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);
        //this.add(textArea,BorderLayout.CENTER);
        this.add(typePanel, BorderLayout.SOUTH);
       // this.add(new JLabel("Chat", SwingConstants.CENTER), BorderLayout.NORTH);
    }

    /**
     * Grab's new message, Creates chat packet and sends message to server 
     * @param text2Send
     */
    public void ServerChatWrite(String text2Send) {

        ChatPacket message = new ChatPacket(text2Send);
        try {
            output.writeObject(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
/**
 * Updates chat panel for user to see latest message
 * @param arg
 */
    public void updateChatPanel(List<String> arg) {
        String s = "";
        for (String message : arg)
            s = s + message;

        textArea.setText(s);
        textArea.setCaretPosition(s.length());
        revisionPanel.repaint();
    }

    //Action listener class to update the chat conversation
    //when a user clicks send.
    /**
     * @class ChatSendListener 
     * @author Nicholas,Omri,Cameron,Taylor,Eric
     * Listens for new messages from the server then updates accordingly
     */
    private class chatSendListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            String s = textField.getText();
            String temp = username + ": " + s;
            //	textArea.append(username+": "+s);
            //	textArea.append("\n\n");
            textField.setText("");
            ServerChatWrite(temp);

        }
    }
    /**
     * UpdateRevisionPanel with new information as it arrives 
     * @param rev - RevisionAssitant
     */
    public void UpdateRevisionPanel(RevisionAssistant rev){
    	revView = rev;
    	displayofRevisions.clear();
        for(int i = 0;i < revView.revisionList.size(); i++){
        	EditPacket tempRev = revView.getStack().elementAt(i);
        	String temp = ("Revision by: " + tempRev.getUser().getUserName() + " At: " + tempRev.getTime());
        	displayofRevisions.addElement(temp);
        }
        repaint();
    	
    }
    

    
    private class loadRevisionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            //to do, if user selects a revision, and clicks this button,
        	//revert the edit field back to this version.

        }
    }
}
