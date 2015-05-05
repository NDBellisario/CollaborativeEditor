package networking;

import java.io.Serializable;
import java.util.ArrayList;

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
<<<<<<< HEAD
		ArrayList<Integer> deleteMe = new ArrayList<Integer>();
		deleteMe.add((Integer) ownerID);
		int docID = newMaster.addDocument(docName, ownerID, deleteMe);

=======
		this.docID = newMaster.addDocument(docName, ownerID, null);
>>>>>>> db5fe6deb9394a0815bb990515b3b7f45ce4da14
		return newMaster;
	}
	public Integer getDocID() {
		return docID;
	}
	



}
