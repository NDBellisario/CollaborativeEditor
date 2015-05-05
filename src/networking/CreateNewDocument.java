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
	
	public CreateNewDocument(String name, Integer id) {
		this.docName = name;
		this.ownerID = id;
	}
	public DocumentAssistant execute(DocumentAssistant arg) {
		// TODO: read from option list of users
		ArrayList<Integer> tempEditors = new ArrayList<Integer>();
		tempEditors.add((Integer) ownerID);
		docID = arg.addDocument(docName, ownerID, tempEditors);
		for(int i = 0; i < arg.getList().size(); i++){
			if (arg.getList().get(i).getDocIdentification() == docID){
				theDoc = arg.getList().get(i);
			}
		}
		theDoc = null;
		
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

}
