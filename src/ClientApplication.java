import javax.swing.*;
import java.awt.*;

public class ClientApplication extends JFrame {

    public ClientApplication() {
        super("Client Application");

        init();
    }

    public void init() {
        this.setContentPane(new LogInPanel(this));

        // JFrame settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(600, 600));
        this.setVisible(true);
    }

    public void register(String username, String password, String address, String postcode) throws Exception {
        // Register a new user
        throw new Exception("Username already used!");
    }

    public void logIn(String username, String password) throws Exception {
        // Log in
        throw new Exception("Wrong username or password!");
    }
}