package networking;
import model.Revision;
import model.User;
import server.CEServer;
import view.EditView;

import java.io.Serializable;
import java.util.*;

/*
 * Manages the changes made to the text editor
 * 
 * Takes in a char to add to the editor
 * 
 * Returns the new List, updated!
 */
public class EditPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    User theUser;
    private String newText;

    public EditPacket(EditView editView, User arg) {
        newText = editView.getText();
        theUser = arg;
    }
    public String getNewText()
    {
        return newText;
    }

    public String execute(String theList) {
        Date date = new Date();
        Revision revision = new Revision(theUser, newText, date);

        //if(masterText.length() %15 == 0)
        //masterText += "\n";
        //int masterLength = masterText.length();
        //CEServer.masterList = newText;
        //String newMaster = masterText.substring(masterLength);
        if (newText.equals(theList) && !newText.equals("null"))
            return newText = "";
        //else if (newText.length() == 100)
        //    newText += "\n";
        return newText;

    }

}
