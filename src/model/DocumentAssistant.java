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

    public int addDocument(String docName, int ownerId, ArrayList<Integer> editors) {
        int newID = documentList.size()+1;
        editors.add(1);
        editors.add(2);
        editors.add(3);
        Doc temp = new Doc(docName,newID,ownerId, editors);
        temp.setDocContents("Test String");
    	documentList.add(temp);
        return newID;
    }

    public void removeDocument(Doc toRemove) {
        documentList.remove(toRemove);

    }

    public ArrayList<Doc> getList() {
        return documentList;
    }


}
