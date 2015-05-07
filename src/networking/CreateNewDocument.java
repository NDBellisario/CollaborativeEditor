package networking;

import java.io.Serializable;
import java.util.ArrayList;

import model.Doc;
import model.DocumentAssistant;
/**
 * 
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 *Creates a new document for the editor
 */
public class CreateNewDocument implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String docName;
	private int ownerID;
	private int docID;
	private Doc theDoc;
	private DocumentAssistant ourList;
	/**
	 * Constructor for new document
	 * @param name title of document 
	 * @param id Unique ID for Document 
	 */
	public CreateNewDocument(String name, Integer id) {
		this.docName = name;
		this.ownerID = id;
	}
	/**
	 * 
	 * @param arg : Document Assistant 
	 * @return
	 */
	public DocumentAssistant execute(DocumentAssistant arg) {
		// TODO: read from option list of users
		ArrayList<Integer> tempEditors = new ArrayList<Integer>();
		tempEditors.add((Integer) 0);
		tempEditors.add((Integer) 1);

		docID = arg.addDocument(docName, ownerID, tempEditors);
		theDoc = arg.getList().get(docID-1);		
		ourList = arg;
		return arg;
	}
	/**
	 * Returns Document ID
	 * @return UID in integer form
	 */
	public Integer getDocID() {
		return docID;
	}
	/**
	 * returns document  
	 * @return returns Document
	 */
	public Doc getDoc(){
		return theDoc;
	}
	/**
	 * get's creator of the Document 
	 * @return returns the User who created the document in String forum 
	 */
	public String getName(){
		return docName;
	}
	/**
	 * returns a list of all Document's 
	 * @return DocumentAssistant
	 */
	public DocumentAssistant getDocAs(){
		return ourList;
	}

}
