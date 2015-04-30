package networking;

import java.io.Serializable;
import java.util.ArrayList;

import model.Revision;
import model.RevisionAssistant;
import model.User;
import model.UserAssistant;
import server.CEServer;
import view.EditView;

/*
 * Manages the changes made to the text editor
 * 
 * Takes in a char to add to the editor
 * 
 * Returns the new List, updated!
 */
public class EditPacket implements Serializable {
	private String newText;
	User theUser;
	private static final long serialVersionUID = 1L;
	
	public EditPacket(EditView editView, User arg) {
		newText = editView.getText();
		theUser = arg;
	}

	public String execute() {
		Revision revision = new Revision(theUser, newText);
		RevisionAssistant.revisionStack.add(revision);
		//if(masterText.length() %15 == 0)
			//masterText += "\n";
		//int masterLength = masterText.length();
		//CEServer.masterList = newText;
		//String newMaster = masterText.substring(masterLength);
		if(newText.equals(CEServer.masterList) && !newText.equals("null"))
			return "";
		else if(newText.length() == 100)
			newText += "\n";
		return newText;

	}

}
