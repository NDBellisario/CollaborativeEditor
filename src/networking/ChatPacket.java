package networking;
import java.io.Serializable;
import java.util.*;
/**
 * Chat packet contains information on the chat window, mainly comnversational Strings
 * @author NDBellisario
 *
 */
public class ChatPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String chatText;
    private List<String> oldChats;
    private List<String> newChats;

    /**
     * Constructs the Chat Packet with text from chat
     * @param text
     */
    public ChatPacket(String text) {
        chatText = text;
    }
    /**
     * constructs a List of the conversation for the Server to send back to user
     * @param arg
     */
    public ChatPacket(List<String> arg){
        oldChats = arg;
        newChats = arg;
    }
/**
 * Override the old chat set with the new one
 * @param arg
 */
    public void setCurrent(List<String> arg) {
        oldChats = new ArrayList<String>();
        oldChats = arg;
    }
    /**
     * Returns a list of the current chat list
     * @return List of Strings
     */
    public List<String> getChats(){
        return newChats;
    }
/**
 * Used for after packet is transfer to server.
 * runs execute function after being received 
 * @return List of Strings of the chat
 */
    public List<String> execute() {
        oldChats.add(chatText + "\n\n");
        newChats = oldChats;
        return oldChats;
    }
}
