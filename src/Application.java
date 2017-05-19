import javax.swing.*;

public class Application extends JFrame {
    protected Comms comms;

    public Application(String title) {
        super(title);
    }

    public Comms getComms() {
        return comms;
    }

    // Called by Comms when a message is received. For BusinessApplication only
    public void receivedMessage(int request) {
    }

    public void receivedMessage(int request, Object content) {
    }
}
