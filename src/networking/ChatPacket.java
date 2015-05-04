package networking;
import java.io.Serializable;
import java.util.*;

public class ChatPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String chatText;
    private List<String> oldChats;
    private List<String> newChats;

    public ChatPacket(String text) {
        chatText = text;
    }
    public ChatPacket(List<String> arg){
        oldChats = arg;
        newChats = arg;
    }

    public void setCurrent(List<String> arg) {
        oldChats = new ArrayList<String>();
        oldChats = arg;
    }
    public List<String> getChats(){
        return newChats;
    }

    public List<String> execute() {
        oldChats.add(chatText + "\n\n");
        newChats = oldChats;
        return oldChats;
    }
}
