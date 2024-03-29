import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserPanel extends JPanel {
    private final User loggedUser;

    private static String currentText;
    private static String currentName;
    DefaultListModel<String> listModel = new DefaultListModel<>();
    private static HashMap<Long, Object> items;

    JPanel centerPanel = new JPanel(new BorderLayout());
    JLabel label = new JLabel();

    static List<Object> selectedObject = new ArrayList<>();

    JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    UserPanel(User loggedUser) {
        this.loggedUser = loggedUser;
        this.setLayout(new BorderLayout());
        this.initCenterPanel("0", "Dział pracowników");
        currentName = "Dział pracowników";
        this.initNorthPanel(this.loggedUser.initials, "Dział pracowników", false);

        this.initLeftPanel();

        if(loggedUser.getClass().getName().equals("Foreman")) {
            Foreman foreman = (Foreman)loggedUser;
            EventQueue.invokeLater(() -> new DisplayItemPanel(foreman.getOrdersList(), "Order"));
        }
    }

    private void initNorthPanel(String userInitial, String buttonText, boolean disableEditButton) {
        northPanel.removeAll();
        northPanel.updateUI();
        String[] buttonNames = {"Dodaj", "Edytuj", "Usuń"};
        System.out.println(currentText);
        if(currentText.equals("Zlecenie") && disableEditButton) {
            Order order = (Order)selectedObject.get(0);
            JButton cos = new JButton("Zakończ zlecenie");
            cos.setVisible(order.orderState == Order.OrderState.ROZPOCZETE);
            cos.addActionListener(e -> order.interruptOrder());
            northPanel.add(cos);
        }

        if(currentText.equals("Dział pracowników")) {
            WorkDepartment workDepartment = (WorkDepartment)selectedObject.get(0);
            JButton workerListButton = new JButton("Pracownicy działu");
            workerListButton.setVisible(true);
            workerListButton.addActionListener(e -> new DisplayItemPanel(workDepartment.getWorkers(), "Pracownik"));

        }


        JButton orderButton = new JButton("Zlecenia");
        System.out.println(loggedUser.getClass().getName());
        orderButton.setVisible((loggedUser.getClass().getName()).equals("Foreman"));
        orderButton.addActionListener(e -> {
            Foreman foreman = (Foreman)loggedUser;
            EventQueue.invokeLater(() -> new DisplayItemPanel(foreman.getOrdersList(), "Order"));
        });
        northPanel.add(orderButton);

        for(String name : buttonNames) {
            JButton button = new JButton(name);
            button.setVisible(!(name.equals("Dodaj") && buttonText.equals("Pracownik") || (name.equals("Edytuj") && !disableEditButton)));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(button.equals(e.getSource())) {
                        if(e.getActionCommand().equals("Usuń")) {
                            removeObject();

                        } else if(e.getActionCommand().equals("Dodaj")) {
                            EventQueue.invokeLater(() -> {
                                new NewUserPanel(() -> initCenterPanel(currentName, currentText), loggedUser.getIdentifyNumber(), currentText, null, false);
                            });

                        } else if(e.getActionCommand().equals("Edytuj")) {
                            EventQueue.invokeLater(() -> {
                                new NewUserPanel(() -> initCenterPanel(currentName, currentText), loggedUser.getIdentifyNumber(), currentText, selectedObject.get(0), true);
                            });
                        }


                    }
                }
            });
            northPanel.add(button);
        }

        JLabel label = new JLabel("Witaj byku! " + userInitial);
        label.setPreferredSize(new Dimension(label.getWidth() + 150, label.getHeight() + 35));
        label.setBorder(new RoundedBorder(Color.BLACK, 10));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        northPanel.add(label);

        this.add(northPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel(String name, String text) {
        items = ItemWrapperOrdering.getItems(name);

        currentText = text;
        currentName = name;
        label.setText(text);
        listModel.clear();

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

        list.addListSelectionListener(new ListSelectionListener() {
           @Override
           public void valueChanged(ListSelectionEvent e) {
               if(!e.getValueIsAdjusting()) {
                   selectedObject.clear();
                   Arrays.stream(list.getSelectedIndices()).forEach( i -> {
                       String name = list.getModel().getElementAt(i);
                       long index = Long.parseLong(name.substring(name.length() - 1));

                       selectedObject.add(items.get(index));
                       initNorthPanel(loggedUser.initials, text, selectedObject.size() == 1);

                   });
               }
           }
       });
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(new RoundedBorder(Color.BLACK, 0));
        label.setPreferredSize(new Dimension(centerPanel.getWidth(), 30));

        centerPanel.add(label, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(list);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);
    }

    private void initLeftPanel() {
        String[] buttonNames = {"Dział pracowników", "Pracownik", "Użytkownik", "Brygadzista", "Brygada", "Zlecenie", "Praca"};
        JPanel leftPanel = new JPanel(new GridLayout(8, 1));

        for(int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setName(String.valueOf(i));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(button.equals(e.getSource())) {
                        currentText = button.getText();
                        initCenterPanel(button.getName(), button.getText());
                        if(button.getText().equals("Pracownik")) {
                            initNorthPanel(loggedUser.initials, button.getText(), false);
                        } else {
                            initNorthPanel(loggedUser.initials, button.getText(), false);
//                            System.out.println("akdjfdfk®");
                        }

                    }
                }
            });
            leftPanel.add(button);
        }

        JButton logoutButton = new JButton("Wyloguj");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start.wrapper.show(Start.frameMainPanel, "1");
            }
        });
        leftPanel.add(logoutButton);

        this.add(leftPanel, BorderLayout.WEST);
    }

    private void removeObject() {
        selectedObject.forEach(i -> {
            String className = i.getClass().getName();

            switch (className) {
                case "WorkDepartment" -> {
                    WorkDepartment workDepartment = (WorkDepartment) i;
                    workDepartment.removeDepartment();
                }
                case "User" -> {
                    User user = (User) i;
                    user.removeUser();
                }
                case "Foreman" -> {
                    Foreman foreman = (Foreman) i;
                    foreman.removeForeman();
                }
                case "Brigade" -> {
                    Brigade brigade = (Brigade) i;
                    brigade.removeBrigade();
                }
                case "Order" -> {
                    Order order = (Order) i;
                    order.removeOrder();
                }
                case "Job" -> {
                    Job job = (Job) i;
                    job.removeJob();
                }
            }
        });
        initCenterPanel(currentName, currentText);
    }
}
