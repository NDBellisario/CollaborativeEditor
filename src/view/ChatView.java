package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

//import model.AddMessageCommand;
//import view.ChatPanel.EnterListener;

public class ChatView extends JPanel implements Observer{
	
    private JPanel chatPanel;
    private JPanel typePanel;
    private JButton sendButton;
    private JTextPane groupText, personalTest;
    private JTextArea textArea; // chat log displayed here
    private JTextArea textField; // field where user enters text
    private JTextArea whoIsTyping;
    private String username;


    public ChatView(String username){
    	this.username = username;
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
			/*try{
				output.writeObject(new AddMessageCommand(clientName + ":  " + s));
			}catch(Exception e){
				e.printStackTrace();
			}*/
			textArea.append(username+": "+s);
			textArea.append("\n\n");
			textField.setText("");
		}
    }
    
    //To be filled in later.
    @Override
    public void update(Observable o, Object arg){
	 
  // TODO Auto-generated method stub
    }


    //Don't need this method. Panel is set up in Constructor method.
    /*public void setUpGui(){
	 
    }*/

      
    //not sure why the user would need a document select button in the
    //chat panel. this panel is just for chatting.
   /* private class documentSelect implements ActionListener{
	 
        public void actionPerformed(ActionEvent arg0){
        	
        }
    }*/
    
    //Just a main to test its appeareance.
   /* public static void main(String[] args){
    	JFrame frame = new JFrame();
    	frame.setLayout(new BorderLayout());
    	ChatView chat = new ChatView();
    	chat.setVisible(true);
    	frame.add(chat, BorderLayout.CENTER);
    	frame.setVisible(true);
    	frame.setResizable(true);
    	frame.pack();
    	frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }*/
}
