package networking;
import com.sun.xml.internal.bind.v2.TODO;
import model.User;
import model.UserAssistant;

public class UserPacket {
    private String username;
    private String password;

    public UserPacket(String nameArg, String passArg) {
        username = nameArg;
        password = passArg;
    }
public void changePermission(User userArg, int arg)
{
userArg.setPermission(arg);
    //userArg.EditView.setChangedPermission;
    // TODO: make this work!
}
    public boolean execute(UserAssistant theUser) {
        theUser.addUser(username, password, 2);
        return true;
    }

}
