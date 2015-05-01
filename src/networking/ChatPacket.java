package networking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sun.security.ntlm.Client;

import controller.CEController;
import server.CEServer;
import model.User;

public class ChatPacket implements Serializable {

	private static final long serialVersionUID = 1L;
	private String chatText;
	private ArrayList<String> oldChats;

	public ChatPacket(String text) {
		chatText = text;
	}
	public void setCurrent(ArrayList<String> arg) {
		oldChats = arg;
	}

	public ArrayList<String> execute() {
		oldChats.add(chatText);
		return oldChats;
	}
}
