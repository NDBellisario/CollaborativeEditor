package model;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import networking.EditPacket;
/**
 * @Author  Nicholas,Taylor,Omri,Eric,Cameron Team Amphetamine Salts 
 * Instances of this class will be stored in an
 * ArrayList on the server in DocumentAssistant.
 */
public class Doc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String docName;
	private int docIdentification;
	private int ownerId;
	private ArrayList<Integer> editors;
	private ArrayList<String> annotations;
	private String docContents;
	private RevisionAssistant theRevisions;
<<<<<<< HEAD
	private StyledDocument ourStyle;

=======
	/**
	 * @author  Nicholas,Taylor,Omri,Eric,Cameron Team Amphetamine Salts
	 * @param docName name of document constructed 
	 * @param docId unique ID for reference with document 
	 * @param ownerId unique Owner ID to track document creater
	 * @param editors current permitted users who can edit the document 
	 */
>>>>>>> 772b974c20ba06e5bda84c7915ac71e8a4824e6d
	public Doc(String docName, int docId, int ownerId, ArrayList<Integer> editors) {
		this.docName = docName;
		this.docIdentification = docId;
		this.ownerId = ownerId;
		this.editors = editors;
		// this.editors = new ArrayList<Integer>();
		// editors.add((Integer) ownerId);
		this.annotations = new ArrayList<String>();
		setDocContents("");
		theRevisions = new RevisionAssistant();

	}
	/**
	 * Sets current Revision set for this Document
	 * @param arg List of Revisions to track all changes made in document 
	 */
	public void setRevision(RevisionAssistant arg) {
		theRevisions = arg;
	}
<<<<<<< HEAD
	public void setStyle(StyledDocument arg){
		ourStyle = arg;
	}
	public StyledDocument getStyle(){
		return ourStyle;
	}
=======
	/**
	 * permissions for who can view documents
	 * @param theArg User who wish's to view document
	 * @return boolean with accepted for denied
	 */
>>>>>>> 772b974c20ba06e5bda84c7915ac71e8a4824e6d
	public boolean canView(User theArg) {

		int lookFor = theArg.getID();
		for (int curVal : editors) {
			if (curVal == lookFor) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Get's Documents Revision Assistant
	 * @return Revision Assistant
	 */
	public RevisionAssistant getRevisions() {
		return theRevisions;
	}
	/**
	 * get's current document title
	 * @return String of this documents title
	 */
	public String getDocName() {
		return docName;
	}
/**
 * get's current documents text
 * @return a string of the documents text
 */
	public String getDocContents() {

		if (docContents == null) {
			docContents = "";
		}
		return docContents;
	}
/**
 * sets Current document content to a long string 
 * @param toAdd String of the document contents 
 */
	public void setDocContents(String toAdd) {
		docContents = toAdd;
	}
/**
 * Get's Documents UIN (unique Identification number)
 * @return and integer of the UIN
 */
	public int getDocIdentification() {
		return docIdentification;
	}
/** Get's the Document owners IDN
 * 
 * @return the Document owner IDN
 */
	public int getOwnerId() {
		return ownerId;
	}
/**
 * Add's a user with permissions to documents preferences
 * @param permissionArg level of permission
 * @param userArg user to be added
 */
	public void addEditor(Integer permissionArg, User userArg) {
		editors.add(userArg.getID());
	}
/**
 * Removes an editor from the list of permitted users
 * @param toRemove user to remove
 */
	public void removeEditor(User toRemove) {
		editors.remove(toRemove);
	}
/**
 * Add's annotation to documents spec sheet 
 * @param toAdd notes to add
 */
	public void addAnnotation(String toAdd) {
		annotations.add(toAdd);
	}
/**
 * Removes document annotation from spec sheet
 * @param toRemove notes to remove
 */
	public void removeAnnotations(String toRemove) {
		annotations.remove(toRemove);
	}
	/**
	 * Returns a list of current permitted editors
	 * @return Arraylist of permitted editors ID numbers
	 */
	public ArrayList<Integer> getDocEditors() {

		return editors;
	}
	/**
	 * returns a List of Annotations
	 * @return an arrayList of all annotations
	 */
	public ArrayList<String> getAnnotations() {
		return annotations;
	}
}
