package model;
import java.io.Serializable;
import java.util.*;

public class DocumentAssistant implements Serializable {
    /*
     * Author: Cameron Morrell
     * This class will store all of the server's Document objects in an ArrayList
     * each Document has a unique ID number stored as an integer
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Document> documentList;

    public DocumentAssistant() {
        documentList = new ArrayList<Document>();
    }

    public void addDocument(String docName, int docId, int ownerId, HashMap<Integer,User> editors) {
        documentList.add(new Document(docName,docId,ownerId, editors));
    }

    public void removeDocument(Document toRemove) {
        documentList.remove(toRemove);

    }

    public ArrayList<Document> getList() {
        return documentList;
    }

}
