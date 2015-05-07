package networking;

import java.io.Serializable;

import model.DocumentAssistant;
import model.User;
/**
 * UpdateUserPacket
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 *Update's user with current DocumentAssistant 
 */
public class UpdateUserPacket implements Serializable {
	
	private DocumentAssistant mainDA;
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor build UUP
	 * @param DocumentAssistant
	 */
	public UpdateUserPacket(DocumentAssistant arg) {
		mainDA = arg;
	}
	/**
	 * get's current DocumentAssistant inside of the packet and returns for use
	 * @return DocumentAssistant
	 */
	public DocumentAssistant getML() {
		// TODO Auto-generated method stub
		return mainDA;
	}

}
