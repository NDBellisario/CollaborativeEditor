package networking;

import java.io.Serializable;

import server.CEServer;
import controller.CEController;

public class LogoutPacket implements Serializable {
	
	private String user;
	
	public void execute(CEController cECon){
		cECon.logout();
	}
	public void quit(CEServer ceServer){
		ceServer.userLeft(this.user);
	}
	public String getUser(){
		return this.user;
	}
	public void setUser(String usr){
		this.user = usr;
	}
}
