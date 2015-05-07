package networking;
import model.User;
import model.UserAssistant;

import java.io.Serializable;
/**
 * New user packet, is Transfered when a new user is added to the server 
 * @author Nicholas,Omri,Cameron,Taylor,Eric
 *
 */
public class NewUserPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;
/**
 * Constructor for New user packet
 * @param argUN - String
 * @param argPW - String 
 */
    public NewUserPacket(String argUN, String argPW) {
        userName = argUN;
        password = argPW;
    }
/**
 * Execution method that is run upon arrival through Stream
 * @param theUsers
 * @return the User that was added
 */
    public User execute(UserAssistant theUsers) {

        return theUsers.addUser(userName, password);
    }
}
