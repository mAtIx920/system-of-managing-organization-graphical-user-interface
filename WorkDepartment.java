import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkDepartment {
    private static long ID;
    public long identifyNumber;

    String departmentName;
    private final static HashMap<Long, Object> departments = new HashMap<>();
    private final static List<String> departmentNames = new ArrayList<>();
    private final HashMap<Long, Object> workers = new HashMap<>();

    private WorkDepartment(String departmentName) {
        try {
            this.identifyNumber = ID++;
            this.checkUniqueDepartmentName(departmentName);
            this.departmentName = departmentName;
            departmentNames.add(departmentName);
            departments.put(this.identifyNumber, this);
            ItemWrapperOrdering.addItem("0", departments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUniqueDepartmentName(String departmentName) throws NotUniqueNameException {
        for(String name : departmentNames) {
            if(departmentName.equals(name)) throw new NotUniqueNameException(departmentName);
        }
    }

    static WorkDepartment createDepartment(String departmentName) {
        return new WorkDepartment(departmentName);
    }

    void addWorker(Worker worker) {
        this.workers.put(worker.workerIdentify, worker);
    }

    HashMap<Long, Object> getWorkers() {
        return this.workers;
    }

    void editDepartment(String departmentName) {
        this.departmentName = departmentName;
        ItemWrapperOrdering.addItem("0", departments);
    }

    void removeDepartment() {
        departments.remove(this.identifyNumber);
        ItemWrapperOrdering.addItem("0", departments);
    }

    @Override
    public String toString() {
        return "{Klasa DziałPracowników o identyfikatorze - " + "(" + this.identifyNumber + ")," +
                " Nazwa: " + this.departmentName + "," +
                " Lista pracowników w dziale: " + this.getWorkers().toString() + "}";
    }
}
