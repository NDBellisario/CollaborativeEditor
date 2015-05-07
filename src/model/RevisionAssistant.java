package model;
import java.io.Serializable;
import java.util.*;

import networking.EditPacket;
/**
 * Creates and holds a list of all revisions
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 *
 */
public class RevisionAssistant implements Serializable {
    public Stack<EditPacket> revisionList;
/**
 * Constructor for RevisionAssistant 
 */
    public RevisionAssistant() {
        revisionList = new Stack<EditPacket>();
    }
/**
 * Add's a new Revision to the list 
 * @param takes in Revision 
 */

    public void addRevision(EditPacket arg) {
        revisionList.push(arg);
    }
/**
 * Returns a list of the revisions in the current document 
 * @return a Stack of Revisions
 */
    public Stack<EditPacket> getStack() {
       
        return revisionList;
    }

}
