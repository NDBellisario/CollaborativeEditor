package model;
import java.io.Serializable;
import java.util.*;

public class DocumentAssistant implements Serializable {
    /*
     * Author: Cameron Morrell
     * This class will store all of the server's Doc objects in an ArrayList
     * each Doc has a unique ID number stored as an integer
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Doc> documentList;

    public DocumentAssistant() {
        documentList = new ArrayList<Doc>();
    }

    public void addDocument(String docName, int docId, int ownerId, HashMap<Integer,User> editors) {
        documentList.add(new Doc(docName,docId,ownerId, editors));
    }

    public void removeDocument(Doc toRemove) {
        documentList.remove(toRemove);

    }

    public ArrayList<Doc> getList() {
        return documentList;
    }

}
