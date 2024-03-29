import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class NewUserPanel extends JFrame {
    final MyMethod initCenterPanelMethodContext;
    final String currentNamePanel;

    static JTextField birth = new JTextField(20);
    JTextField departmentNameField = new JTextField();
    JTextField brigadeNameField = new JTextField();
    JTextField userNameField = new JTextField(20);
    JTextField userLastNameField = new JTextField(20);
    JTextField userLoginField = new JTextField(20);
    JTextField userPasswordField = new JTextField(20);
    JTextField jobTimeField = new JTextField(20);
    JTextField descriptionJob = new JTextField(20);
    JCheckBox plannedCheck = new JCheckBox("Czy planowane");
    ButtonGroup buttonGroup = new ButtonGroup();
    String jobTypeSelected;

    JPanel panel = new JPanel();

    public NewUserPanel(MyMethod method, Long loggedUserIdentifyNumber, String currentNamePanel, Object editObject, boolean editMode) {
        super(editMode ? "Panel edycji" : "Panel dodawania");
        this.initCenterPanelMethodContext = method;
        this.currentNamePanel = currentNamePanel;

        //UserNameField section
        JLabel nameLabel = new JLabel("Imię pracownika");
        nameLabel.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        userNameField.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        panel.add(nameLabel);
        panel.add(userNameField);

        //UserLasNameField
        JLabel lastNameLabel = new JLabel("Nazwisko pracownika");
        lastNameLabel.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        userLastNameField.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        panel.add(lastNameLabel);
        panel.add(userLastNameField);

        //UserLoginField
        JLabel loginLabel = new JLabel("Login pracownika");
        loginLabel.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        userLoginField.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        panel.add(loginLabel);
        panel.add(userLoginField);

        //UserPasswordField
        JLabel passwordLabel = new JLabel("Hasło pracownika");
        passwordLabel.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        userPasswordField.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        panel.add(passwordLabel);
        panel.add(userPasswordField);

        //UserBirthField
        JButton birthButton = new JButton("Wybierz datę urodzenia");
        birthButton.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        birthButton.addActionListener(e -> EventQueue.invokeLater(DatePicker::new));
        birth.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        panel.add(birthButton);
        panel.add(birth);

        //UserWorkDepartmentField
        JButton departmentButton = new JButton("Wybierz dział pracowników");
        departmentButton.setVisible(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista"));
        departmentButton.addActionListener(e -> new ItemListPanel("0"));
        panel.add(departmentButton);

        //DepartmentNameField
        JLabel departmentLabel = new JLabel("Nazwa departamentu");
        departmentLabel.setVisible(currentNamePanel.equals("Dział pracowników"));
        departmentNameField.setVisible(currentNamePanel.equals("Dział pracowników"));
        panel.add(departmentLabel);
        panel.add(departmentNameField);

        //BrigadeNameField
        JLabel brigadeLabel = new JLabel("Nazwa brygady");
        brigadeLabel.setVisible(currentNamePanel.equals("Brygada"));
        brigadeNameField.setVisible(currentNamePanel.equals("Brygada"));
        JButton brigadeButton = new JButton("Wybierz brygadzistę");
        brigadeButton.setVisible(currentNamePanel.equals("Brygada"));
        brigadeButton.addActionListener(e -> new ItemListPanel("3"));
        panel.add(brigadeLabel);
        panel.add(brigadeNameField);
        panel.add(brigadeButton);

        //OrderField
        plannedCheck.setVisible(currentNamePanel.equals("Zlecenie"));
        JButton orderButton = new JButton("Dodaj brygadę");
        orderButton.setVisible(currentNamePanel.equals("Zlecenie"));
        orderButton.addActionListener(e -> new ItemListPanel("4"));
        panel.add(plannedCheck);
        panel.add(orderButton);

        //JobField
        String[] jobNameTypes = {"Ogólna", "Montaż", "Demontaż", "Wymiana"};
        Arrays.stream(jobNameTypes).forEach(name -> {
            JRadioButton button = new JRadioButton(name);
            button.setVisible(currentNamePanel.equals("Praca"));
            button.addActionListener(event -> {
                JRadioButton selectedRadioButton = (JRadioButton) event.getSource();
                jobTypeSelected = selectedRadioButton.getText();
            });
            buttonGroup.add(button);
            panel.add(button);
        });
        JLabel jobTimeLabel = new JLabel("Czas pracy");
        jobTimeLabel.setVisible(currentNamePanel.equals("Praca"));
        jobTimeField.setVisible(currentNamePanel.equals("Praca"));
        JLabel jobDescriptionLabel = new JLabel("Opis pracy");
        jobDescriptionLabel.setVisible(currentNamePanel.equals("Praca"));
        descriptionJob.setVisible(currentNamePanel.equals("Praca"));
        panel.add(jobTimeLabel);
        panel.add(jobTimeField);
        panel.add(jobDescriptionLabel);
        panel.add(descriptionJob);

        //EditModeFunction
        if(editMode) {
            switch (currentNamePanel) {
                case "Użytkownik", "Brygadzista" -> {
                    User user = (User) editObject;
                    if (Objects.equals(user.getIdentifyNumber(), loggedUserIdentifyNumber)) {
                        nameLabel.setVisible(false);
                        userNameField.setVisible(false);
                        lastNameLabel.setVisible(false);
                        userLastNameField.setVisible(false);
                        loginLabel.setVisible(false);
                        userLoginField.setVisible(false);
                        birthButton.setVisible(false);
                        birth.setVisible(false);
                        departmentButton.setVisible(false);
                    }
                    userNameField.setText(user.name);
                    userLastNameField.setText(user.lastName);
                    userLoginField.setText(user.login);
                    userPasswordField.setText(user.password);
                    birth.setText(user.birthDate.toString());
                }
                case "Dział pracowników" -> {
                    WorkDepartment workDepartment = (WorkDepartment) editObject;
                    departmentNameField.setText(workDepartment.departmentName);
                }
                case "Brygada" -> {
                    Brigade brigade = (Brigade) editObject;
                    brigadeNameField.setText(brigade.brigadeName);
                }
            }
        }

        //SubmitButton
        JButton button = new JButton(editMode ? "Edytuj" : "Dodaj");
        button.addActionListener(e -> {
            if(editMode) {
                if(currentNamePanel.equals("Dział pracowników")) {
                    ItemWrapperOrdering.editWorkDepartment((WorkDepartment)editObject, departmentNameField.getText());
                } else if(currentNamePanel.equals("Użytkownik") || currentNamePanel.equals("Brygadzista")) {
                    User user = (User)editObject;
                    ItemWrapperOrdering.editWorker(
                            (User)editObject,
                            userNameField.getText(),
                            userLastNameField.getText(),
                            userLoginField.getText(),
                            userPasswordField.getText(),
                            Objects.equals(user.getIdentifyNumber(), loggedUserIdentifyNumber) ? user.getUserDepartment() : (WorkDepartment)ItemListPanel.selectedObject.get(0));
                }
            } else {
                createObject(currentNamePanel);
                initCenterPanel();
            }
            dispose();
        });
        panel.add(button);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setVisible(true);
        pack();
    }

    private void initCenterPanel() {
        initCenterPanelMethodContext.execute();
    }

    private void createObject(String currentNamePanel) {
        switch (currentNamePanel) {
            case "Dział pracowników" -> WorkDepartment.createDepartment(departmentNameField.getText());
            case "Użytkownik" -> new User(userNameField.getText(), userLastNameField.getText(), userLoginField.getText(), userPasswordField.getText(), DatePicker.date, (WorkDepartment) ItemListPanel.selectedObject.get(0), false);
            case "Brygadzista" -> new Foreman(userNameField.getText(), userLastNameField.getText(), userLoginField.getText(), userPasswordField.getText(), DatePicker.date, (WorkDepartment) ItemListPanel.selectedObject.get(0));
            case "Brygada" -> new Brigade(brigadeNameField.getText(), (Foreman) ItemListPanel.selectedObject.get(0));
            case "Zlecenie" -> new Order(plannedCheck.isSelected(), (Brigade) ItemListPanel.selectedObject.get(0));
            case "Praca" -> new Job(change(jobTypeSelected), Integer.parseInt(jobTimeField.getText()), descriptionJob.getText());
        }
    }

    private Job.JobType change(String jobType) {
        return switch (jobType) {
            case "Montaż" -> Job.JobType.Montaz;
            case "Demontaż" -> Job.JobType.Demontaz;
            case "Wymiana" -> Job.JobType.Wymiana;
            default -> Job.JobType.Ogolna;
        };
    }
}

