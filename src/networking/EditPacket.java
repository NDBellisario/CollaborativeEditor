package networking;
import model.Doc;
import model.DocumentAssistant;
import model.User;
import view.EditView;

import java.io.Serializable;

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

	public EditPacket(EditView editView, User arg, int argID) {

			newText = editView.getText();

		theUser = arg;
		docID = argID;
		System.out.println("user cares about: " + arg.selectedDoc);
		System.out.println("our doc is of value: " + argID);

	}
	public EditPacket(User arg, int argID, DocumentAssistant DAarg){
		newText = "";
		theUser = arg;
		docID = argID;
		masterDA = DAarg;
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
	public Doc getDocObject() {
		//System.out.println(masterDA.getList().size());
		return masterDA.getList().get(docID - 1);

	}
	public DocumentAssistant tempMe(){
		return masterDA;
	}

	public DocumentAssistant execute(DocumentAssistant temp) {
		// If the document contents has something new, and the value of the new
		// change is not null, we need to set the doc contents

		if (!(newText.equals(temp.getList().get(docID - 1).getDocContents()) && !newText.equals("null"))) {
			temp.getList().get(docID - 1).setDocContents(newText);

			docName = temp.getList().get(docID - 1).getDocName();
			masterDA = temp;
			System.out.println("Execute: The server says this doc ID is: " + temp.getList().get(docID - 1).getDocIdentification());
		}
		return masterDA;

	}

}
