package networking;

import java.io.Serializable;

import model.Doc;
import model.DocumentAssistant;

public class CreateNewDocument implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String docName;
private int ownerID;
private int docID;
	public CreateNewDocument(String name, Integer id) {
		this.docName = name;
		this.ownerID = id;
	}
	public DocumentAssistant execute(DocumentAssistant arg){
		DocumentAssistant newMaster = arg;
		this.docID = newMaster.addDocument(docName, ownerID, null);
		return newMaster;
	}
	public Integer getDocID() {
		return docID;
	}
	



}
