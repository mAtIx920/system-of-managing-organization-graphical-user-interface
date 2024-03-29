import javax.swing.*;
import java.util.List;

public class ItemDetailsPanel extends JFrame {
    JPanel panel = new JPanel();

    ItemDetailsPanel(Object itemList) {
        super();


        this.panel.add(new JButton("cos"));
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(this.panel);
        setVisible(true);
        pack();
    }

    private void initPanel() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
    }
}
