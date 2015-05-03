package networking;
import model.User;
import model.UserAssistant;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class RecoverPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String newPW;

    public RecoverPacket(String argUserName, String argNewPW) {
        userName = argUserName;
        newPW = argNewPW;
    }
    public String getUserName()
    {
        return userName;
    }
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
