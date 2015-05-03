package model;
import java.io.Serializable;
import java.util.ArrayList;

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
	private ArrayList<Integer> editorIds;
	private ArrayList<String> annotations;
	private String docContents;
	
	
	public Document(String docName, int docId, int ownerId, ArrayList<Integer> editorIds) {
		this.docName = docName;
		this.docIdentification = docId;
		this.ownerId = ownerId;
		this.editorIds = editorIds;
		this.annotations = new ArrayList<String>();
		buildBaseDocument();
		
	}
	
	public void buildBaseDocument() {
		this.docContents = "";
	}
	
}
