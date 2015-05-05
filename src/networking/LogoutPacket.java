package networking;

import java.io.Serializable;

import controller.CEController;

public class LogoutPacket implements Serializable {
	
	
	public void execute(CEController cECon){
		cECon.logout();
	}
}
