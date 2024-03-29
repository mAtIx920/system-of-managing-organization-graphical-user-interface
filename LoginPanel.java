import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    JTextField usernameField = new JTextField();
    JPasswordField passField = new JPasswordField();

    LoginPanel() {
        super();
        setLayout(null);
        this.initLoginPanel();
    }

    private void initLoginPanel() {
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 8, 70, 20);
        this.add(usernameLabel);

        this.usernameField.setBounds(100, 27, 193, 28);
        this.add(this.usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(100, 55, 70, 20);
        this.add(passLabel);

        this.passField.setBounds(100, 75, 193, 28);
        this.add(this.passField);

        JButton button = new JButton("Login");
        button.setBounds(100, 110, 90, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passField.getText();

                for(Object worker : Worker.getWorkers().values()) {
                    User user = (User) worker;

                    if(user.login.equals(username) && user.password.equals(password)) {
                       Start.frameMainPanel.add(new UserPanel(user), "2");
                       Start.wrapper.show(Start.frameMainPanel, "2");
                       return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Username or Password mismatch");
            }
        });
        this.add(button);
    }

}
