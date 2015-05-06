package networking;

import java.io.Serializable;
import java.util.ArrayList;

import model.Doc;
import model.DocumentAssistant;

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
	
	public CreateNewDocument(String name, Integer id) {
		this.docName = name;
		this.ownerID = id;
	}
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
	public Integer getDocID() {
		return docID;
	}
	public Doc getDoc(){
		return theDoc;
	}
	public String getName(){
		return docName;
	}
	public DocumentAssistant getDocAs(){
		return ourList;
	}

}
