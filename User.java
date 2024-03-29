import java.time.LocalDateTime;
import java.util.HashMap;

public class User extends Worker {
    protected String login;
    protected String password;
    protected String initials;
    private static final HashMap<Long, Object> users = new HashMap<>();

    User(String name, String lastName, String login, String password, LocalDateTime birthDate, WorkDepartment workDepartment, boolean isForeman) {
        super(name, lastName, birthDate, workDepartment);
        this.login = login;
        this.password = password;
        this.initials = this.createInitial(name, lastName);
        if(!isForeman) {
            users.put(this.workerIdentify, this);
            ItemWrapperOrdering.addItem("2", users);
        }
    }

    public void setName(String name) {
        this.name = name;
        this.initials = this.createInitial(name, this.lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.initials = this.createInitial(this.initials, lastName);
    }

    WorkDepartment getUserDepartment() {
        return this.workDepartment;
    }

    Long getIdentifyNumber() {
        return this.workerIdentify;
    }

    private String createInitial(String name, String lastName) {
        return (Character.toString(name.charAt(0)) + Character.toString(lastName.charAt(0))).toUpperCase();
    }

    void removeUser() {
        users.remove(this.workerIdentify);
        ItemWrapperOrdering.addItem("2", users);
        this.removeWorker(this.workerIdentify);
    }

    void editUser(String name, String lastName, String login, String password, WorkDepartment workDepartment) {
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.workDepartment = workDepartment;
        ItemWrapperOrdering.addItem("2", users);
        this.editWorker(this.workerIdentify, this);
    }

    @Override
    public String toString() {
        return "Klasa Użytkownik o identyfikatorze - " + "(" + this.workerIdentify + ")," +
                " Login użytkownika: " + this.login + "," +
                " Haslo użytkownika: " + this.password + "," +
                " Inicjały użytkownika: " + this.initials + "," +
                super.toString();
    }
}

