import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LogInPanel extends JPanel {
    private ClientApplication clientApplication;
    private ArrayList<String> postcodes;

    private JTextField usernameFld;
    private JTextField passwordFld;
    private JTextField addressFld1;
    private JTextField addressFld2;
    private JSpinner postcodeSpinner;

    private JButton enterBtn;
    private JButton changeViewBtn;

    // Constants used to change views
    private static final int REGISTER = 0;
    private static final int LOGIN = 1;

    public LogInPanel(ClientApplication clientApplication) {
        super();

        this.clientApplication = clientApplication;
        setLayout(new GridBagLayout());

        postcodes = new ArrayList<>(10);
        for (int i = 1; i < 17; i++) {
            postcodes.add("SO" + i);
        }

        setRegisterForm();
        setRegisterView();
    }

    private void setRegisterForm() {
        GridBagConstraints genericConstraints = new GridBagConstraints();   // I need same inner padding for most components

        // USERNAME
        JLabel usernameLbl = new JLabel("Username");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 0;
        Insets genericInsets = new Insets(2, 10, 2, 10);
        genericConstraints.insets = genericInsets;
        genericConstraints.anchor = GridBagConstraints.LINE_END;
        add(usernameLbl, genericConstraints);

        usernameFld = new JTextField(100);
        usernameFld.setMinimumSize(new Dimension(100, 20));
        genericConstraints.gridx = 1;
        add(usernameFld, genericConstraints);

        // PASSWORD
        JLabel passwordLbl = new JLabel("Password");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 1;
        add(passwordLbl, genericConstraints);

        passwordFld = new JPasswordField(100);
        passwordFld.setMinimumSize(new Dimension(100, 20));
        genericConstraints.gridx = 1;
        add(passwordFld, genericConstraints);

        // ADDRESS
        JLabel addressLbl = new JLabel("Address");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 2;
        add(addressLbl, genericConstraints);

        addressFld1 = new JTextField(100);
        genericConstraints.gridx = 1;
        genericConstraints.insets = new Insets(2, 10, 0, 10);   // Tighter fit
        addressFld1.setMinimumSize(new Dimension(100, 20));
        add(addressFld1, genericConstraints);

        addressFld2 = new JTextField(100);
        genericConstraints.gridy = 3;
        genericConstraints.insets = new Insets(0, 10, 2, 10);   // Tighter fit
        addressFld2.setMinimumSize(new Dimension(100, 20));
        add(addressFld2, genericConstraints);

        // POSTCODE
        JLabel postcodeLbl = new JLabel("Postcode");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 4;
        genericConstraints.insets = genericInsets;  // Reset insets
        add(postcodeLbl, genericConstraints);

        postcodeSpinner = new JSpinner(new SpinnerListModel(postcodes));
        postcodeSpinner.setMinimumSize(new Dimension(60, 20));
        genericConstraints.gridx = 1;
        genericConstraints.anchor = GridBagConstraints.LINE_START;
        add(postcodeSpinner, genericConstraints);

        setButtons();
    }

    private void setLoginForm() {
        GridBagConstraints genericConstraints = new GridBagConstraints();   // I need same inner padding for most components

        // USERNAME
        JLabel usernameLbl = new JLabel("Username");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 0;
        genericConstraints.insets = new Insets(2, 10, 2, 10);
        genericConstraints.anchor = GridBagConstraints.LINE_END;
        add(usernameLbl, genericConstraints);

        usernameFld = new JTextField(100);
        usernameFld.setMinimumSize(new Dimension(100, 20));
        genericConstraints.gridx = 1;
        add(usernameFld, genericConstraints);

        // PASSWORD
        JLabel passwordLbl = new JLabel("Password");
        genericConstraints.gridx = 0;
        genericConstraints.gridy = 1;
        add(passwordLbl, genericConstraints);

        passwordFld = new JPasswordField(100);
        passwordFld.setMinimumSize(new Dimension(100, 20));
        genericConstraints.gridx = 1;
        add(passwordFld, genericConstraints);

        setButtons();
    }

    private void setButtons() {
        GridBagConstraints buttonConstraints = new GridBagConstraints();

        enterBtn = new JButton();
        buttonConstraints.gridx = 2;
        buttonConstraints.gridy = 5;
        buttonConstraints.insets = new Insets(30, 0, 0, 0);
        buttonConstraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(enterBtn, buttonConstraints);

        changeViewBtn = new JButton();
        buttonConstraints.gridy = 6;
        buttonConstraints.insets = new Insets(5, 0, 0, 0);
        add(changeViewBtn, buttonConstraints);
    }

    private void setRegisterView() {
        // REGISTER
        enterBtn.setText("Register");
        for (ActionListener al : enterBtn.getActionListeners()) {
            enterBtn.removeActionListener(al);
        }

        enterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String address = addressFld1.getText().trim();
                if (!addressFld2.getText().trim().isEmpty()) {
                    address += ", " + addressFld2.getText().trim();
                }

                try {
                    clientApplication.register(usernameFld.getText().toLowerCase(), passwordFld.getText(), address, (String) postcodeSpinner.getValue());
                } catch (Exception e1) {
                    exceptionThrown(e1);
                }
            }
        });

        // LOGIN
        changeViewBtn.setText("Already a member?");
        for (ActionListener al : changeViewBtn.getActionListeners()) {
            changeViewBtn.removeActionListener(al);
        }

        changeViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchView(LOGIN);
            }
        });
    }

    private void setLoginView() {
        // LOGIN
        enterBtn.setText("Log in");
        for (ActionListener al : enterBtn.getActionListeners()) {
            enterBtn.removeActionListener(al);
        }

        enterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    clientApplication.logIn(usernameFld.getText().toLowerCase(), passwordFld.getText());
                } catch (Exception e1) {
                    exceptionThrown(e1);
                }
            }
        });

        // REGISTER
        changeViewBtn.setText("Not a member yet?");
        for (ActionListener al : changeViewBtn.getActionListeners()) {
            changeViewBtn.removeActionListener(al);
        }

        changeViewBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchView(REGISTER);
            }
        });
    }

    private void switchView(int option) {
        removeAll();

        if (option == LOGIN) {
            setLoginForm();
            setLoginView();
        } else if (option == REGISTER) {
            setRegisterForm();
            setRegisterView();
        }

        repaint();      // necessary for removeAll to work
    }

    private void exceptionThrown(Exception e) {
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel exception = new JLabel(e.getMessage());

        constraints.gridx = 0;
        constraints.gridy = 5;
        add(exception, constraints);
        // TODO: doesn't work correctly?
    }
}
