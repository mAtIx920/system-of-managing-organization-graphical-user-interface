import java.time.LocalDateTime;
import java.util.HashMap;

public abstract class Worker {
    private static long ID;
    long workerIdentify;

    protected String name;
    protected String lastName;
    protected LocalDateTime birthDate;
    protected WorkDepartment workDepartment;
    public boolean doingOrderNow = false;
    private static final HashMap<Long, Object> workers = new HashMap<>();


    Worker(String name, String lastName, LocalDateTime birthDate, WorkDepartment workDepartment) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.workDepartment = workDepartment;
        this.workerIdentify = ID++;
        workers.put(this.workerIdentify, this);
        ItemWrapperOrdering.addItem("1", workers);
    }

    public static HashMap<Long, Object> getWorkers() {
        return workers;
    }

    public boolean getAvailabilityWorker() {
        return this.doingOrderNow;
    }

    void workerDoingWork(boolean state) {
        this.doingOrderNow = state;
    }

    void removeWorker(Long id) {
        workers.remove(id);
        ItemWrapperOrdering.addItem("1", workers);
    }

    void editWorker(Long id, Worker worker) {
        workers.put(id, worker);
    }

    @Override
    public String toString() {
        return "Imie pracownika: " + this.name + "," +
                " Nazwisko pracownika: " + this.lastName + "," +
                " Data urodzenia: " + this.birthDate + "}";
    }
}
