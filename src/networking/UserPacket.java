package networking;

import model.UserAssistant;

public class UserPacket {

	private String username;
	private String password;

	public UserPacket(String nameArg, String passArg) {
		username = nameArg;
		password = passArg;
	}

	public boolean execute(UserAssistant theUser) {
		theUser.addUser(username, password, 2);
		return true;
	}

}
