import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DisplayItemPanel extends JFrame {
    JPanel panel = new JPanel();
    private final HashMap<Long, Object> itemList;
    String itemType;


    DisplayItemPanel(HashMap<Long, Object> itemList, String objectType) {
        super(objectType.equals("Order") ? "Nieukończone zlecenia" : "Prace");
        this.itemList = itemList;
        this.initPanel();
    }

    private void initPanel() {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        itemList.values().forEach(item -> {
            itemType = item.getClass().getName();

            if(itemType.equals("Order")) {
                Order order = (Order) item;

                if(order.endDate == null) {
                    listModel.addElement(order.getClass().getName() + " " + order.identifyNumber);
                }

            } else if(itemType.equals("Job")) {
                Job job = (Job) item;
                listModel.addElement(job.getClass().getName() + " " + job.identifyNumber);
            }
        });

        JList<String> list = new JList<>(listModel);
        list.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                AtomicLong clickedItemIndex = new AtomicLong();
                Arrays.stream(list.getSelectedIndices()).forEach( i -> {
                    String name = list.getModel().getElementAt(i);
                    clickedItemIndex.set(Long.parseLong(name.substring(name.length() - 1)));

                });
                EventQueue.invokeLater(() -> new ItemDetailsPanel((this.itemList.get(clickedItemIndex))));
            }
        });
        JScrollPane scrollPane = new JScrollPane(list);
        this.panel.add(scrollPane, BorderLayout.CENTER);
        this.add(this.panel, BorderLayout.CENTER);

        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);
        this.setContentPane(this.panel);
        this.setVisible(true);
    }
}
