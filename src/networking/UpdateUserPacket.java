package networking;

import java.io.Serializable;

import model.DocumentAssistant;
import model.User;

public class UpdateUserPacket implements Serializable {
	
	private DocumentAssistant mainDA;
	private static final long serialVersionUID = 1L;
	
	public UpdateUserPacket(DocumentAssistant arg) {
		mainDA = arg;
	}
	public DocumentAssistant getML() {
		// TODO Auto-generated method stub
		return mainDA;
	}

}
