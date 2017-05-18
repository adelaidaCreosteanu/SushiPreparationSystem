import java.io.Serializable;

public class Message implements Serializable {
    public static final int GET_DISHSTOCK_HASHMAP = 0;
    private Object content;
    private int senderPortNumber;

    public Message(Object content, int senderPortNumber) {
        this.content = content;
        this.senderPortNumber = senderPortNumber;
    }

    public Object getContent() {
        return content;
    }

    public int getSender() {
        return senderPortNumber;
    }
}
