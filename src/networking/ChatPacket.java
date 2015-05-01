package networking;

import java.io.Serializable;
import java.util.List;

import com.sun.security.ntlm.Client;

import controller.CEController;
import server.CEServer;
import model.User;

public class ChatPacket implements Serializable {
	private String chatText;
	private List<String> allChat;
	private User theUser;
		
		
		
		public ChatPacket(String text){
			this.chatText = text;
			
		}
		public ChatPacket(List<String> newMessages){
			this.allChat = newMessages;
		}

	public void Execute(CEServer executeOn){
		executeOn.updateChat(chatText);
	}
	
	public void Execute(CEController executeOn){
		executeOn.updateChat(allChat);
	}
}
