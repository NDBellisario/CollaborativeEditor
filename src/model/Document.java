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
	private String Owner;
	private ArrayList<Integer> editors;
	private String docContents;
	private ArrayList<String> annotations;
	
}
