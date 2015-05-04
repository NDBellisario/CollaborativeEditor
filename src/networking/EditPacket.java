package networking;
import model.Doc;
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

        for (int i = 0; i < masterArg.getList().size(); i++) {
            if (masterArg.getList().get(i).getDocIdentification() == (docID)) {

                if (newText.equals(masterArg.getList().get(i).getDocContents()) && !newText.equals("null")) {
                    masterArg.getList().get(i).setDocContents("");
                }

                masterArg.getList().get(i).setDocContents(newText);
            }
        }
    }

}
