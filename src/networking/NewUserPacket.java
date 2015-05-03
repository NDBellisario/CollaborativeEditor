package networking;
import model.User;
import model.UserAssistant;

import java.io.Serializable;

public class NewUserPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;

    public NewUserPacket(String argUN, String argPW) {
        userName = argUN;
        password = argPW;
    }

    public User execute(UserAssistant theUsers) {

        return theUsers.addUser(userName, password);
    }
}
