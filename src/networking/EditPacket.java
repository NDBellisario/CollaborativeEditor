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


import javax.swing.text.StyledDocument;


/*
=======
/**
 * @class Editpacket
 * @author Nicholas,Omri,Cameron,Taylor,Eric
>>>>>>> Stashed changes
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

	private StyledDocument packetStyle;

	/**
	 * Constructor
	 * @param editView current Edit view
	 * @param arg current User 
	 * @param argID document ID
*/
	public EditPacket(EditView editView, User arg, int argID) {
		packetStyle = null;
		if (editView == null) {
			newText = "";
			//System.out.println("AAAAAA");
		} else {
			newText = editView.getText();
			//System.out.println("BBBBBBB");
			packetStyle = editView.getPane().getStyledDocument();
		}
		theUser = arg;
		docID = argID;
		createdOn = new Date();
		revisionTime = false;
		
		
		

	}
	/**
	 * secondary Constructor 
	 * @param mainUser
	 * @param docID2 
	 * @param docName
	 * @param arg
	 */
	public EditPacket(User mainUser, Integer docID2, String docName, RevisionAssistant arg) {
		theUser = mainUser;
		docID = docID2;
		setDocName(docName);
		revisionTime = true;
		createdOn = new Date();
		revAssist = arg;
		Revision newR = new Revision(theUser, createdOn, mili);
		revAssist.addRevision(newR);
		packetStyle = null;
	}
	public StyledDocument getStyle(){
		return packetStyle;
	}
	public void setStyle(StyledDocument arg){
		packetStyle = arg;
	}
/**
 * Set's document name 
 * @param name
 */
	public void setDocName(String name) {
		docName = name;

	}
/**
 * Set's the new text for the document 
 * @return String of text;
 */
	public String getNewText() {
		return newText;
	}
	/**
	 * Returns the document ID number 
	 * 
	 * @return document ID
	 */
	public int getDocID() {
		return docID;
	}
 /**
  * Returns a string of the Document name
  * @return Document name
  */
	public String getDocName() {
		return docName;
	}
	/**
	 * Set's the master of the file for editing 
	 * @param Current DocumentAssistant
	 */
	public void setMaster(DocumentAssistant arg) {
		masterDA = arg;
	}
	/**
	 * returns main user on Documents
	 * @return User instance
	 */
	public User getUser() {
		return theUser;
	}
	/**
	 * Returns the current document assisant after being passed threw the Stream
	 * @param temp
	 * @return DocumentAssistant
	 */
	public DocumentAssistant execute(DocumentAssistant temp) {
		// If the document contents has something new, and the value of the new
		// change is not null, we need to set the doc contents
		mili = System.currentTimeMillis();
		revAssist = temp.getList().get(docID - 1).getRevisions();
		if ((newText.equals(temp.getList().get(docID - 1).getDocContents()) && !newText.equals("null"))) {
			temp.getList().get(docID - 1).setDocContents((temp.getList().get(docID - 1).getDocContents()));
			setDocName(temp.getList().get(docID - 1).getDocName());
			temp.getList().get(docID - 1).setDocContents(newText);
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
	/**
	 * Get's current master Assistant
	 * @return DocumentAssistant
	 */
	public DocumentAssistant getMaster() {
		return masterDA;
	}
	/**
	 * get's current Revision assistant 
	 * @return RevisionAssistant
	 */
	public RevisionAssistant getRev() {
		// TODO Auto-generated method stub
		return revAssist;
	}
	/**
	 * Returns the current Document 
	 * @return Document 
	 */
	public Doc getDoc() {
		return masterDA.getList().get(docID - 1);
	}

}
