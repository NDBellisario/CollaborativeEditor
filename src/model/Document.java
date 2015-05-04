package model;
import java.io.Serializable;
import java.util.*;

public class Document implements Serializable {
    /*
     * Author: Cameron Morrell
	 * Instances of this class will be stored in an ArrayList on the server
	 * in DocumentAssistant.
	 */
    private static final long serialVersionUID = 1L;
    private String docName;
    private int docIdentification;
    private int ownerId;
    private HashMap<Integer, User> editors;
    private ArrayList<String> annotations;
    private String docContents;

    public Document(String docName, int docId, int ownerId, HashMap<Integer, User> editors) {
        this.docName = docName;
        this.docIdentification = docId;
        this.ownerId = ownerId;
        this.editors = editors;
        this.annotations = new ArrayList<String>();
        setDocContents("<html><font color = red>Document: " + docName + "</font></html>");

    }

    public String getDocContents() {
        return docContents;
    }

    public void setDocContents(String toAdd) {
        docContents = toAdd;
    }

    public int getDocIdentification() {
        return docIdentification;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void addEditor(Integer permissionArg, User userArg) {
        editors.put(permissionArg, userArg);
    }

    public void removeEditor(User toRemove) {
        editors.remove(toRemove);
    }

    public void addAnnotation(String toAdd) {
        annotations.add(toAdd);
    }

    public void removeAnnotations(String toRemove) {
        annotations.remove(toRemove);
    }

    public ArrayList<String> getAnnotations() {
        return annotations;
    }
}
