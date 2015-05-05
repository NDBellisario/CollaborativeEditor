package networking;

import java.util.ArrayList;

import model.*;

public class GetDocsPacket {
	
	private ArrayList<Doc> myDocs;
	private User myUser;

	
	public GetDocsPacket(User mainUser) {
		myUser = mainUser;
		myDocs = new ArrayList<Doc>();// TODO Auto-generated constructor stub
	}

	public void makeList(DocumentAssistant masterList, User mainUser) {
		myUser = mainUser;
		for (Doc i : masterList.getList()) {
			if(i.getDocEditors().contains(myUser.getID())) {
				myDocs.add(i);
			}
		}
	}
	
	public ArrayList<Doc> getList() {
		return myDocs;
	}
	
	public User getUser() {
		return myUser;
	}
	

}
