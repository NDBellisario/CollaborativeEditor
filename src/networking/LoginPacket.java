package networking;

import java.io.Serializable;
import java.util.ArrayList;

import model.User;
import model.UserAssistant;

@SuppressWarnings("serial")
public class LoginPacket implements Serializable {

	String userName;
	String password;
	String recover;

	public LoginPacket(String uname, String pw) {
		this.userName = uname;
		this.password = pw;
	}
	public String getName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	// Method is for recovery of users, if the user exists, get PW
	// Otherwise return this String.
	public String getRecovery(String arg, UserAssistant theUsers) {

		ArrayList<User> knownUsers = theUsers.getUsers();
		for (int i = 0; i < knownUsers.size(); i++) {
			if (knownUsers.get(i).getUserName().equals(userName)) {
				return knownUsers.get(i).getPassword();
			}

		}
		return "Username Not Found!  Creating Account With Password '0000'";
	}

	public boolean execute(UserAssistant theUsers) {
		ArrayList<User> knownUsers = theUsers.getUsers();
		for (int i = 0; i < knownUsers.size(); i++) {
			if (knownUsers.get(i).getUserName().equals(userName)) {

				if (knownUsers.get(i).getPassword().equals(password)) {
					return true;
				} else {
					return false;
				}
			}

		}

		return false;

	}
}
