package networking;
import model.DocumentAssistant;
import model.User;
import view.EditView;

import java.io.Serializable;

/*
 * Manages the changes made to the text editor
 * 
 * Takes in a char to add to the editor
 * 
 * Returns the new List, updated!
 */
public class EditPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private User theUser;
    private String newText;
    private int docID;

    public EditPacket(EditView editView, User arg, int argID) {
        newText = editView.getText();
        theUser = arg;
        docID = argID;

    }

    public String getNewText() {
        return newText;
    }

    public int getDocID() {
        return docID;
    }

    public void execute(DocumentAssistant masterArg) {
    // If the document contents has something new, and the value of the new change is not null, we need to set the doc contents
        if (!(newText.equals(masterArg.getList().get(docID - 1).getDocContents()) && !newText.equals("null"))) {
            masterArg.getList().get(docID-1).setDocContents(newText);
        }

    }
}



