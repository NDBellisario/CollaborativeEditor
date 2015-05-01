package model;

import java.util.ArrayList;

public class RevisionAssistant extends EditAssistant
{
	
	public ArrayList<Revision> revisionList;
	
	public RevisionAssistant()
	{
		revisionList = new ArrayList<Revision>();
	}
	
	/**
	 * Creates another version of an EditAssistant
	 * @param arg
	 */
	public void createVersion(EditAssistant arg)
	{
	}
	
	/**
	 * Save the current work
	 * @param arg
	 */
	public void save(EditAssistant arg)
	{
	}
	
	/**
	 * Save it locally as opposed to a global
	 * @param arg
	 */
	public void saveLocally(EditAssistant arg)
	{
	}
	
	/**
	 * Create a new document of text
	 * 
	 */
	public void newDoc()
	{
	}
	
	public void addRevision(Revision revision) {
		revisionList.add(revision);
	}
	
	public ArrayList<Revision> getStack() {
		ArrayList<Revision> temp = revisionList;
		return temp;
	}
	
	
	


}
