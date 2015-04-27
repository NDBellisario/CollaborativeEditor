package model;

import java.util.Stack;

public class RevisionAssistant extends EditAssistant
{
	
	public Stack<Revision> revisionStack;
	
	public RevisionAssistant()
	{
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
	
	private class Revision {
		
		private int revisionCount;
		private User revisor;
		private String revision;
		
		Revision(User user, String text, int count) {
			this.revisionCount = count;
			this.revisor = user;
			this.revision = text;
		}

	}

}
