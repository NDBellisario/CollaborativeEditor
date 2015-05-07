package model;
import java.io.Serializable;
import java.util.*;
/** 
 * @class DocumentAssistant
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 * This class will store all of the server's Doc objects in an ArrayList
 * each Doc has a unique ID number stored as an integer
 */
public class DocumentAssistant implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private ArrayList<Doc> documentList;
/**
 * Constructs documents assistant
 */
    public DocumentAssistant() {
        documentList = new ArrayList<Doc>();
    }
/**
 * Add's a reference to new document to the list of Document in the assitant 
 * @param docName Name of document
 * @param ownerId User who owns document
 * @param editors ArrayList of Editors and permisions on document 
 * @return
 */
    public int addDocument(String docName, int ownerId, ArrayList<Integer> editors) {
        int newID = documentList.size()+1;
        //editors.add(ownerId);
        editors.add(1);
        editors.add(2);
        editors.add(3);
        editors.add(4);
        editors.add(5);
        editors.add(6);
        Doc temp = new Doc(docName,newID,ownerId, editors);
        temp.setDocContents("");
    	documentList.add(temp);
        return newID;
    }
/** 
 * Removes Document from List of Saved documents
 * 
 */
    public void removeDocument(Doc toRemove) {
        documentList.remove(toRemove);

    }
/**
 * returns a current list of all document in class
 * @return Arraylist of Documents
 */
    public ArrayList<Doc> getList() {
        return documentList;
    }


}
