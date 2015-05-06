package model;
import java.util.*;

public class RevisionAssistant extends EditAssistant {
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
