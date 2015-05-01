package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;

import javax.swing.*;

import networking.ChatPacket;
import controller.CEController;
import model.User;

//import model.AddMessageCommand;
//import view.ChatPanel.EnterListener;

public class ChatView extends JPanel implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel chatPanel;
    private JPanel typePanel;
    private JButton sendButton;
    private JTextPane groupText, personalTest;
    private JTextArea textArea; // chat log displayed here
    private JTextArea textField; // field where user enters text
    private JTextArea whoIsTyping;
    private String username;
    private ObjectOutputStream output;

    public ChatView(User user,ObjectOutputStream outputSet){
    	this.username = user.getUserName();
	    this.setLayout(new BorderLayout());
	    this.setPreferredSize(new Dimension(280, 600));
	    
	    //Text area to carry the conversation text.
	    textArea = new JTextArea();
	    textArea.setEditable(false);
	    textArea.setLineWrap(true);
	    
	    //Text field to 
	    textField = new JTextArea();
	    textField.setEditable(true);
	    textField.setLineWrap(true);
	    JButton enterButton = new JButton("Send");
	    
	   // textField.setPreferredSize(new Dimension(600, 40));
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
	 	whoIsTyping.setText(username+" is typing....");
	    
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
	 	this.add(new JLabel("Chat", SwingConstants.CENTER),BorderLayout.NORTH);
    }
    
    //Action listener class to update the chat conversation
    //when a user clicks send.
    private class chatSendListener implements ActionListener{
	 
    	public void actionPerformed(ActionEvent arg0) {
			String s = textField.getText();
			String temp = username+": "+s;
		//	textArea.append(username+": "+s);
		//	textArea.append("\n\n");
			textField.setText("");
			ServerChatWrite(temp);
			
			
		}
    }
    public void ServerChatWrite(String text2Send) {
		
		ChatPacket message = new ChatPacket(text2Send);
		try {
			 output.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    
	public void updateChatPanel(List<String> arg) {
		String s = "";
		for (String message: arg)
			s = s + message;
		
		textArea.setText(s);
		textArea.setCaretPosition(s.length());
		repaint();
	}
}
