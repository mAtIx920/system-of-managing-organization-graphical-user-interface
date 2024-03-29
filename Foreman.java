import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Foreman extends User {
    private static final HashMap<Long, Object> allForemen = new HashMap<>();
    private final List<Brigade> attendantBrigades = new ArrayList<>();
    private final HashMap<Long, Object> foremanOrders = new HashMap<>();

    Foreman(String name, String lastName, String login, String password, LocalDateTime birthDate, WorkDepartment workDepartment) {
        super(name, lastName, login, password, birthDate, workDepartment, true);
        allForemen.put(this.workerIdentify, this);
        ItemWrapperOrdering.addItem("3", allForemen);
    }

    public void addBrigade(Brigade brigade) {
        this.attendantBrigades.add(brigade);
    }

    void removeForeman() {
        allForemen.remove(this.workerIdentify);
        ItemWrapperOrdering.addItem("3", allForemen);
        this.removeWorker(this.workerIdentify);
    }

    void addOrder(Order order) {
        this.foremanOrders.put(order.identifyNumber, order);
    }

//    public List<Brygada> getBrigadeList() {
//        return this.brigadeNamesList;
//    }

    HashMap<Long, Object> getOrdersList() {
        return this.foremanOrders;
    }

    @Override
    public String toString() {
        return "{Klasa Brygadzista o identyfikatorze - " + "(" + this.workerIdentify + ")," +
                "imie" + this.name +
                " Lista brygad w jakich brał udział: " + ",";
    }
}
