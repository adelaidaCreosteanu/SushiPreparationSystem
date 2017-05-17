import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LogInPanel extends JPanel {
    private ClientApplication clientApplication;
    private ArrayList<String> postcodes;

    public LogInPanel(ClientApplication clientApplication) {
        super();

        this.clientApplication = clientApplication;
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());

        postcodes = new ArrayList<>(10);

        for (int i = 1; i < 17; i++) {
            postcodes.add("SO" + i);
        }

        setRegisterLayout();
    }

    private void setRegisterLayout() {
        GridBagConstraints genericConstraints = new GridBagConstraints();   // I need same inner padding for most components

        // USERNAME
        JLabel usernameLbl = new JLabel("Username");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 0;
        Insets genericInsets = new Insets(2, 10, 2, 10);
        genericConstraints.insets = genericInsets;
        genericConstraints.anchor = GridBagConstraints.LINE_END;
        this.add(usernameLbl, genericConstraints);

        JTextField usernameFld = new JTextField(100);
        usernameFld.setMinimumSize(new Dimension(100, 20));
        genericConstraints.gridx = 1;
        this.add(usernameFld, genericConstraints);

        // PASSWORD
        JLabel passwordLbl = new JLabel("Password");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 1;
        this.add(passwordLbl, genericConstraints);

        JPasswordField passwordFld = new JPasswordField(100);
        passwordFld.setMinimumSize(new Dimension(100, 20));
        genericConstraints.gridx = 1;
        this.add(passwordFld, genericConstraints);

        // ADDRESS
        JLabel addressLbl = new JLabel("Address");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 2;
        this.add(addressLbl, genericConstraints);

        JTextField addressFld1 = new JTextField(100);
        genericConstraints.gridx = 1;
        genericConstraints.insets = new Insets(2, 10, 0, 10);   // Tighter fit
        addressFld1.setMinimumSize(new Dimension(100, 20));
        this.add(addressFld1, genericConstraints);

        JTextField addressFld2 = new JTextField(100);
        genericConstraints.gridy = 3;
        genericConstraints.insets = new Insets(0, 10, 2, 10);   // Tighter fit
        addressFld2.setMinimumSize(new Dimension(100, 20));
        this.add(addressFld2, genericConstraints);

        // POSTCODE
        JLabel postcodeLbl = new JLabel("Postcode");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 4;
        genericConstraints.insets = genericInsets;  // Reset insets
        this.add(postcodeLbl, genericConstraints);

        JSpinner postcodeSpinner = new JSpinner(new SpinnerListModel(postcodes));
        postcodeSpinner.setMinimumSize(new Dimension(60, 20));
        genericConstraints.gridx = 1;
        genericConstraints.anchor = GridBagConstraints.LINE_START;
        this.add(postcodeSpinner, genericConstraints);

        // REGISTER
        JButton registerBtn = new JButton("Register");
        genericConstraints.gridx = 2;
        genericConstraints.gridy = 5;
        genericConstraints.insets = new Insets(30, 0, 0, 0);
        genericConstraints.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(registerBtn, genericConstraints);
    }
}
