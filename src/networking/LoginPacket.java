package networking;

import java.io.Serializable;
import java.util.ArrayList;

import model.User;
import model.UserAssistant;

@SuppressWarnings("serial")
public class LoginPacket implements Serializable {

	String userName;
	String password;

	public LoginPacket(String uname, String pw) {
		userName = uname;
		password = pw;
	}
	public String getName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}

	public boolean execute(UserAssistant theUsers) {
		ArrayList<User> knownUsers = theUsers.getUsers();
		for (int i = 0; i < knownUsers.size(); i++) {
			if (knownUsers.get(i).getUserName() == userName)
				if (knownUsers.get(i).getPassword() == password)
					return true;

		}

		return false;

	}
}
