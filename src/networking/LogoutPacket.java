package networking;

import java.io.Serializable;

import server.CEServer;
import controller.CEController;
/**
 * @class LogoutPacket
 * @author NDBellisario
 * Tell's the users client to close
 */
public class LogoutPacket implements Serializable {
	
	private String user;
	/**
	 * forces exit of user client 
	 * @param cECon
	 */
	public void execute(CEController cECon){
		cECon.logout();
	}
	/**
	 * Tells the server that a user is leaving
	 * @param ceServer
	 */
	public void quit(CEServer ceServer){
		ceServer.userLeft(this.user);
	}
	/**
	 * returns a string of the user
	 * @return
	 */
	public String getUser(){
		return this.user;
	}
	/**
	 * set's the string of the user
	 * @param usr
	 */
	public void setUser(String usr){
		this.user = usr;
	}
}
