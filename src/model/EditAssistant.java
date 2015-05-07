package model;
import java.io.*;
import java.util.*;
/**
 * 
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 * Contains an List of all edits and users in editing of document
 */
public class EditAssistant extends Observable {
    private ArrayList<User> theData;
    private User currUser;
    private File toEdit;

    /**
     * The typing mechanism for the document itself. Allows you to add characters
     *
     * @param toAdd
     */
    public void addText(String toAdd) {
        byte[] byteAdd = toAdd.getBytes();
        //if beginning.startswith "{Bold : 17,20}"
        if (currUser.getPermission() < 3) {

            //User can type
            try {
                //If doc was just opened
                RandomAccessFile raf = new RandomAccessFile(toEdit, "rw");
                String hasText = raf.readLine();
                if (hasText != null) {
                    try {
                        FileWriter fw = new FileWriter(toEdit);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(toAdd);
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //off = pointer to where the cursor is
                    //everyone has their own packet with a cursor pointer

					
					/*toEdit.write(byteAdd, off, len);
					BufferedWriter bw = new BufferedWriter(fw);
		            bw.write(toAdd);
		            bw.close();*/
                    //for now will edit end of file
                    long fileLength = toEdit.length();
                    raf.seek(fileLength);
                    raf.writeBytes(toAdd);
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Delete a string in the text
     *
     * @param toDelete
     */
    public void deleteText(String toDelete) {
        if (currUser.getPermission() < 3) {
            //User can type
        }
    }

    /**
     * Undos any change made to the document
     *
     * @return null
     */
    public void undoText() {

    }
/**
 * Get's the User class from the list of editors
 * @param name
 * @return User who is editing document 
 */
    public User getUser(String name) {
        User toReturn = null;
        for (User user : theData) {
            if (user.getUserName().equals(name)) {
                toReturn = user;
            }
        }
        return toReturn;
    }

    /**
     * Redo's the last change made to the document
     */
	/*
	public void styling(Packet packet) {
		
	}*/
    public void redoText() {
    }
}
