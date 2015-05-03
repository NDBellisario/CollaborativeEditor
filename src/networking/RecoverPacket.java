package networking;
import model.User;
import model.UserAssistant;

import java.io.Serializable;
import java.util.*;

public class RecoverPacket implements Serializable {
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
                toReturn = knownUsers.get(i).setPassword(newPW);
            }

        }
        return toReturn;
    }
}
