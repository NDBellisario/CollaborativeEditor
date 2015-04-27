package networking;

import java.io.Serializable;
import java.util.ArrayList;

import model.Revision;
import model.RevisionAssistant;
import model.User;
import model.UserAssistant;
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
	private String masterText;
	private static final long serialVersionUID = 1L;
	
	public EditPacket(EditView editView) {
		newText = editView.getText();
	}
	public void setMaster(String arg){
		masterText = arg;
	}
	public String execute(User mainUser) {
		Revision revision = new Revision(mainUser, newText);
		RevisionAssistant.revisionStack.add(revision);
		masterText+= newText;
		return masterText;

	}

}
