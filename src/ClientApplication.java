import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientApplication extends JFrame implements Application {
    private ArrayList<Client> clients;
    private Comms comms;

    public ClientApplication() {
        super("Client Application");

        clients = new ArrayList<>();
        comms = new Comms(this);
        init();
    }

    public Comms getComms() {
        return comms;
    }

    public void init() {
        this.setContentPane(new LogInPanel(this));

        // JFrame settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(600, 600));
        this.setVisible(true);
    }

    public void register(String username, String password, String address, String postcode) throws Exception {
        for (Client client : clients) {
            if (username.equals(client.getUsername())) throw new Exception("Username already used!");

            clients.add(new Client(username, password, address, postcode));
        }
    }

    public void logIn(String username, String password) throws Exception {
        boolean found = false;

        for (Client client : clients) {
            if (username.equals(client.getUsername()) && password.equals(client.getPassword())) {
                // log in
                found = true;
            }
        }

        if (!found) throw new Exception("Wrong username or password!");
    }
}