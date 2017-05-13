import java.net.InetAddress;

public class Message {
    private String content;
    private InetAddress sender;

    public Message(String content, InetAddress sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public InetAddress getSender() {
        return sender;
    }
}
