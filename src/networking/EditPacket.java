package networking;
import model.Doc;
import model.DocumentAssistant;
import model.RevisionAssistant;
import model.User;
import view.EditView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.StyledDocument;



/**
 * @class Editpacket
 * @author Nicholas,Omri,Cameron,Taylor,Eric
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
	private Date createdOn;
	private long mili;

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
		mili = System.currentTimeMillis();
		
		
		

	}
	/**
	 * secondary Constructor 
	 * @param mainUser
	 * @param docID2 
	 * @param docName
	 * @param arg
	 */
	public EditPacket(User mainUser, Integer docID2, String docName) {
		theUser = mainUser;
		docID = docID2;
		setDocName(docName);
		createdOn = new Date();
		packetStyle = null;
		mili = System.currentTimeMillis();
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
	public long getMili(){
		return mili;
	}
	/**
	 * Returns the current document assisant after being passed threw the Stream
	 * @param temp
	 * @return DocumentAssistant
	 */
	public DocumentAssistant execute(DocumentAssistant temp) {
		// If the document contents has something new, and the value of the new
		// change is not null, we need to set the doc contents


		if ((newText.equals(temp.getList().get(docID - 1).getDocContents()) && !newText.equals("null"))) {
			temp.getList().get(docID - 1).setDocContents((temp.getList().get(docID - 1).getDocContents()));
			setDocName(temp.getList().get(docID - 1).getDocName());
			temp.getList().get(docID - 1).setDocContents(newText);
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

	/**
	 * Returns the current Document 
	 * @return Document 
	 */
	public Doc getDoc() {
		return masterDA.getList().get(docID - 1);
	}
	/**
	 * Returns the current Tiem
	 * @return Long
	 */
	@SuppressWarnings("deprecation")
	public String getTime() {
		Date temp = new Date();
		String toReturn = ("" + temp.getHours()+":" + temp.getMinutes());
		
		return toReturn;
	}

}
