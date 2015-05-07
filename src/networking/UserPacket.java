package networking;
import com.sun.xml.internal.bind.v2.TODO;
import model.User;
import model.UserAssistant;

import java.io.Serializable;
/**
 * User Packet 
 * @author NDBellisario
 * Contains Information on a specific User, mainly used the by the server for communication with seperate clients
 */
public class UserPacket implements Serializable{
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
/**
 * Constructor
 * @param nameArg - String
 * @param passArg - String
 */
    public UserPacket(String nameArg, String passArg) {
        username = nameArg;
        password = passArg;
    }
    /**
     * Changes permission level of user 
     * @param userArg - User
     * @param arg - Int
     */
public void changePermission(User userArg, int arg)
{
userArg.setPermission(arg);
    //userArg.EditView.setChangedPermission;
    // TODO: make this work!
}
/**
 * Execution method for server or client to activate after pass through 
 * @param theUser - UserAssistant
 * @return boolean upon compleation 
 */
    public boolean execute(UserAssistant theUser) {
        theUser.addUser(username, password);
        return true;
    }

}
