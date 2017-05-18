import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingPanel extends JPanel {
    private ClientApplication clientApplication;
    private Comms comms;
    private ArrayList<SushiDish> dishes;

    public ShoppingPanel(ClientApplication clientApplication) {
        super();

        this.clientApplication = clientApplication;
        this.comms = clientApplication.getComms();

        comms.sendMessage(Message.GET_DISHES);
        dishes = (ArrayList<SushiDish>) comms.receiveMessage();

        setLayout(new GridBagLayout());
    }

}
