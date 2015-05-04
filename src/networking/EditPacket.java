package networking;
import model.Document;
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
    private Document theDoc;
    private DocumentAssistant theAsst;

    public EditPacket(EditView editView, User arg, Document docArg) {
        newText = editView.getText();
        theUser = arg;
        theDoc = docArg;
        ;
    }

    public Document getTheDoc() {
        return theDoc;
    }
    public String getNewText(){
        return newText;
    }

    public void execute(DocumentAssistant masterArg) {

        theAsst = masterArg;

        int toSet = masterArg.getList().indexOf(theDoc);
        if (newText.equals(theDoc.getDocContents()) && !newText.equals("null")) {
            masterArg.getList().get(toSet).setDocContents("");
        }

        masterArg.getList().get(toSet).setDocContents(newText);

    }

}
