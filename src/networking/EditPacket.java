package networking;
import model.Doc;
import model.DocumentAssistant;
import model.Revision;
import model.RevisionAssistant;
import model.User;
import view.EditView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
	private boolean revisionTime;
	private RevisionAssistant revAssist;
	private Date createdOn;
	private Long mili;
	public EditPacket(EditView editView, User arg, int argID) {

		if (editView == null) {
			newText = "EDIT VIEW IS NULL WHENE WOULD THIS HAPPEN";
		} else {
			newText = editView.getText();
		}
		theUser = arg;
		docID = argID;
		createdOn = new Date();

	}

	public EditPacket(User mainUser, Integer docID2, String docName, RevisionAssistant arg) {
		theUser = mainUser;
		docID = docID2;

		setDocName(docName);
		revisionTime = true;
		createdOn = new Date();
		revAssist = arg;
		Revision newR = new Revision(theUser, createdOn, mili);
		revAssist.addRevision(newR);
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
	public User getUser() {
		return theUser;
	}

	public DocumentAssistant execute(DocumentAssistant temp) {
		// If the document contents has something new, and the value of the new
		// change is not null, we need to set the doc contents
		mili = System.currentTimeMillis();
		revAssist = temp.getList().get(docID - 1).getRevisions();
		if ((newText.equals(temp.getList().get(docID - 1).getDocContents()) && !newText.equals("null"))) {
			temp.getList().get(docID - 1).setDocContents((temp.getList().get(docID - 1).getDocContents()));
			setDocName(temp.getList().get(docID - 1).getDocName());
			setMaster(temp);

		} else {
			temp.getList().get(docID - 1).setDocContents(newText);
			setDocName(temp.getList().get(docID - 1).getDocName());

//			if (revAssist.getStack().peek() != null) {
//
//				if (revAssist.getStack().peek().getET() > (mili + 60000)) {
//					Revision newR = new Revision(theUser, createdOn, mili);
//					revAssist.addRevision(newR);
//					setMaster(temp);
//				}
//
//			}
			setMaster(temp);
		}
		return temp;

	}
	public DocumentAssistant getMaster() {
		return masterDA;
	}
	public RevisionAssistant getRev() {
		// TODO Auto-generated method stub
		return revAssist;
	}
	public Doc getDoc() {
		return masterDA.getList().get(docID - 1);
	}

}
