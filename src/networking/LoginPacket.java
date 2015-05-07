package networking;
import model.User;
import model.UserAssistant;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
/**
 * 
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 * contains login information New User for Server to allow in
 */
public class LoginPacket implements Serializable {
    String userName;
    byte[] password;
    String recover;
/**
 * Constructs login packet
 * @param uname - String UserName
 * @param pw - Byte Array Password 
 */
    public LoginPacket(String uname, byte[] pw) {
        this.userName = uname;
        this.password = pw;
    }
/**
 * String getName returns Name of User
 * @return getName - String 
 */
    public String getName() {
        return userName;
    }
/**
 * getPassword get 
 * @return
 */
    public byte[] getPassword() {
        return password;
    }

    public int execute(UserAssistant theUsers) {
        // 0: Login Success!
        // 1: User found, wrong password
        // 2: No account found, need to create one
        int toReturn = 2;

        ArrayList<User> knownUsers = theUsers.getUsers();
        for (int i = 0; i < knownUsers.size(); i++) {
            if (knownUsers.get(i).getUserName().equals(userName)) {
                toReturn = 1;

                if (new String(knownUsers.get(i).getPassword()).equals(new String(password))) {
                    toReturn = 0;
                }
            }

        }

        return toReturn;

    }
}
