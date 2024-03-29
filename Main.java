import java.awt.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        WorkDepartment department1 = WorkDepartment.createDepartment("Nowy");
        WorkDepartment department2 = WorkDepartment.createDepartment("Stary");

        Foreman f1 = new Foreman("Marek", "Koparek", "login1", "123", LocalDateTime.now(), department1);
        Foreman f2 = new Foreman("Barbara", "Kopara", "login2", "1234", LocalDateTime.now(), department1);
        Foreman f3 = new Foreman("Marek", "Koparek", "login3", "12345", LocalDateTime.now(), department1);
        Foreman f4 = new Foreman("Marek", "Koparek", "login4", "12345", LocalDateTime.now(), department1);
        User u1 = new User("Marek", "Koparek", "login434", "123567", LocalDateTime.now(), department1, false);
        User u2 = new User("Marek", "Koparek", "login5345", "1235", LocalDateTime.now(), department1, false);
        User u3 = new User("Marek", "Koparek", "login6345", "1", LocalDateTime.now(), department1, false);

        Job j1 = new Job(Job.JobType.Montaz, 12, "DFSDF");
        Job j2 = new Job(Job.JobType.Montaz, 12, "DFSDF");
        Job j3 = new Job(Job.JobType.Montaz, 12, "DFSDF");

        Brigade b1 = new Brigade("costam", f1);
        Brigade b2 = new Brigade("dupa", f1);

        Order o1 = new Order(true, b1);
        Order o2 = new Order(true, b1);

        EventQueue.invokeLater(Start::new);
    }
}
