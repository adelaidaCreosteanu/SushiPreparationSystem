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
//        this.pack();
        this.setVisible(true);
    }

    //protected void loggedIn(LogInDetails details){}
}
