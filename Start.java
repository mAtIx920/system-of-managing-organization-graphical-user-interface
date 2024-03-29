import javax.swing.*;
import java.awt.*;

public class Start extends JFrame {
    public static CardLayout wrapper = new CardLayout();
    public static JPanel frameMainPanel = new JPanel(new BorderLayout());

    Start() {
        super("GUI panel");
        frameMainPanel.setLayout(wrapper);
        frameMainPanel.add(new LoginPanel(), "1");
        this.setSize(640, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setContentPane(frameMainPanel);
    }

}
