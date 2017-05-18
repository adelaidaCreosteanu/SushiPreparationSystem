import java.io.Serializable;

public class Message implements Serializable {
    public static final String GET_DISHES = "please give me dishes";
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
