package model;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class Doc implements Serializable {
    /*
     * Author: Cameron Morrell
	 * Instances of this class will be stored in an ArrayList on the server
	 * in DocumentAssistant.
	 */
    private static final long serialVersionUID = 1L;
    private String docName;
    private int docIdentification;
    private int ownerId;
    private ArrayList<Integer> editors;
    private ArrayList<String> annotations;
    private String docContents;

    public Doc(String docName, int docId, int ownerId, ArrayList<Integer> editors) {
        this.docName = docName;
        this.docIdentification = docId;
        this.ownerId = ownerId;
        this.editors = editors;
        //this.editors = new ArrayList<Integer>();
        //editors.add((Integer) ownerId);
        this.annotations = new ArrayList<String>();
        setDocContents("<html><font color = red>Doc: " + docName + "</font></html>");


    }

    public String getDocName(){
        return docName;
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
        editors.add(userArg.getID());
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
    public ArrayList<Integer> getDocEditors(){


        return editors;
    }

    public ArrayList<String> getAnnotations() {
        return annotations;
    }
}
