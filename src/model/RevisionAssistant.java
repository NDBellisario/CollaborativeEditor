package model;
import java.io.Serializable;
import java.util.*;

public class RevisionAssistant implements Serializable {
    public Stack<Revision> revisionList;

    public RevisionAssistant() {
        revisionList = new Stack<Revision>();
    }


    public void addRevision(Revision arg) {
        revisionList.push(arg);
    }

    public Stack<Revision> getStack() {
       
        return revisionList;
    }

}
