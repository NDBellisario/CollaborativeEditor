package networking;
import model.Doc;
import model.DocumentAssistant;
import model.User;
import view.EditView;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Manages the changes made to the text editor
 * 
 * Takes in a char to add to the editor
 * 
 * Returns the new List, updated!
 */
public class EditPacket implements Serializable {
	private static final long serialVersionUID = 1L;
	private User theUser;
	private String newText;
	private int docID;
	private String docName;
	private DocumentAssistant masterDA;
	private boolean firstTime = false;
	public EditPacket(EditView editView, User arg, int argID) {

		if (editView == null) {
			newText = "Hi";
		} else {
			newText = editView.getText();
		}
		theUser = arg;
		docID = argID;

	}

	public EditPacket(User mainUser, Integer docID2, DocumentAssistant masterList) {
		theUser = mainUser;
		docID = docID2;
		setMaster(masterList);
	}

	public void setDocName(String name) {
		docName = name;

	}

	public String getNewText() {
		return newText;
	}

	public int getDocID() {
		return docID;
	}
	public String getDocName() {
		return docName;
	}
	public void setMaster(DocumentAssistant arg) {
		masterDA = arg;
	}

	public DocumentAssistant execute(DocumentAssistant temp) {
		// If the document contents has something new, and the value of the new
		// change is not null, we need to set the doc contents

		if ((newText.equals(temp.getList().get(docID - 1).getDocContents()) && !newText.equals("null"))) {
			temp.getList().get(docID - 1).setDocContents((temp.getList().get(docID - 1).getDocContents()));
			docName = temp.getList().get(docID - 1).getDocName();
			setMaster(temp);

		} else {
			temp.getList().get(docID - 1).setDocContents(newText);
			docName = temp.getList().get(docID - 1).getDocName();
			setMaster(temp);
		}
		return temp;

	}
	public DocumentAssistant getMaster() {
		return masterDA;
	}

}
