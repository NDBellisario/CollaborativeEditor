package networking;
import model.User;
import model.UserAssistant;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;
/**
 * Recover Packet 
 * @author NDBellisario
 *Recovers the User's information if lost
 */
public class RecoverPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String newPW;
/**
 * Constuct's user's information for recovery 
 * @param argUserName
 * @param argNewPW
 */
    public RecoverPacket(String argUserName, String argNewPW) {
        userName = argUserName;
        newPW = argNewPW;
    }
    /**
     * returns the name of user which packet was created for 
     * @return
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * Execute method to be run by server upon arrival
     * @param theUsers
     * @return boolean on completion
     */
    public boolean execute(UserAssistant theUsers) {

        ArrayList<User> knownUsers = theUsers.getUsers();
        boolean toReturn = false;
        for (int i = 0; i < knownUsers.size(); i++) {
            if (knownUsers.get(i).getUserName().equals(userName)) {
                try {
                    toReturn = knownUsers.get(i).setPassword(newPW);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

        }
        return toReturn;
    }
}
