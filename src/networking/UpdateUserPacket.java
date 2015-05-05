package networking;

import java.io.Serializable;

import model.User;

public class UpdateUserPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User ourUser;
	public UpdateUserPacket() {

	}
	public User getUser() {
		// TODO Auto-generated method stub
		return ourUser;
	}

}
