import java.io.Serializable;

public class Message implements Serializable {
    public static final int REQUEST_DISHSTOCK_HASHMAP = 0;

    private int request;
    private Object content;
    private int senderPortNumber;

    public Message(int request, Object content, int senderPortNumber) {
        this.request = request;
        this.content = content;
        this.senderPortNumber = senderPortNumber;
    }

    public int getRequest() {
        return request;
    }

    public Object getContent() {
        return content;
    }

    public int getSender() {
        return senderPortNumber;
    }
}
