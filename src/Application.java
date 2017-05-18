import javax.swing.*;

public class Application extends JFrame {
    protected Comms comms;

    public Application(String title) {
        super(title);
    }

    public Comms getComms() {
        return comms;
    }
}
