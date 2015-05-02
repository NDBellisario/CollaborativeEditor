package networking;
import java.io.Serializable;
import java.util.*;

public class ChatPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String chatText;
    private List<String> oldChats;

    public ChatPacket(String text) {
        chatText = text;
    }

    public void setCurrent(List<String> arg) {
        oldChats = new ArrayList<String>();
        oldChats = arg;
    }

    public List<String> execute() {
        oldChats.add(chatText + "\n\n");
        return oldChats;
    }
}
