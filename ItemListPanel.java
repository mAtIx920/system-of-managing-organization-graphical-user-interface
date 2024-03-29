import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemListPanel extends JFrame implements WindowListener {
    static HashMap<Long, Object> items;
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JPanel panel = new JPanel();
    static List<Object> selectedObject = new ArrayList<>();
    JButton button = new JButton("Submit");

    ItemListPanel(String id) {
        super("Niewiem co to jest");
        items = ItemWrapperOrdering.getItems(id);



        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        List<String> tmp = new ArrayList<>();

        for(Object item : items.values()) {
            tmp.add(item.getClass().getName());
        }

        int i = 0;
        for(Long number : items.keySet()) {
            listModel.addElement(tmp.get(i) + " " + number);
            i++;
        }


        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setCellRenderer(new ListViewRenderer(items));
        button.setVisible(false);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    selectedObject.clear();
                    button.setVisible(false);
                    Arrays.stream(list.getSelectedIndices()).forEach(i -> {
                        String name = list.getModel().getElementAt(i);
                        long index = Long.parseLong(name.substring(name.length() - 1));

//                        System.out.println(items.get(index));
//
//                       if(name.substring(0, name.length() - 2).equals("User") && !text.equals("Pracownik")) {
//                           System.out.println(items);
//                       }
//                       System.out.println(items.get(index));
                        selectedObject.add(items.get(index));
                        System.out.println(selectedObject.size());
                        button.setVisible(selectedObject.size() == 1);

//                       System.out.println(selectedObject);
                    });
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(list);
        panel.add(scrollPane, BorderLayout.CENTER);







        panel.add(button);

        setContentPane(panel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
//        setSize(300, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        selectedObject.clear();
        dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("selectedObject");
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
