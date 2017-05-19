import java.io.Serializable;

public class Message implements Serializable {
    public static final int REQUEST_DISHSTOCK_HASHMAP = 0;
    public static final int REQUEST_REGISTER_CUSTOMER = 1;
    public static final int REQUEST_CUSTOMERS_ARRAYLIST = 2;
    public static final int REQUEST_PLACE_ORDER = 3;

    public static final int GIVE_DISHSTOCK_HASHMAP = 4;
    public static final int GIVE_CUSTOMERS_ARRAYLIST = 5;



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
