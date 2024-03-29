import java.util.HashMap;
import java.util.List;

public class ItemWrapperOrdering {
    private final static HashMap<String, HashMap<Long, Object>> itemsWrapper = new HashMap<>();

    public static void addItem(String key, HashMap<Long, Object> items) {
        itemsWrapper.put(key, items);
    }

    public static HashMap<Long, Object> getItems(String id) {
        return itemsWrapper.get(id);
    }

    static void editWorkDepartment(WorkDepartment workDepartment, String newDepartmentName) {
        workDepartment.editDepartment(newDepartmentName);
    }

    static void editWorker(User user, String name, String lastName, String login, String password, WorkDepartment workDepartment) {
        user.editUser(name, lastName, login, password, workDepartment);
    }

    static void editBrigade() {

    }

//    public static void checkObjectClass(Object object) {
//        String className = object.getClass().getName();
//
//        if(className.equals("WorkDepartment")) {
//            WorkDepartment workDepartment = (WorkDepartment)object;
//            workDepartment.removeDepartment();
//        } else if(className.equals("User")) {
//            User user = (User)object;
//            user.removeUser();
//        } else if(className.equals("Foreman")) {
//            Foreman foreman = (Foreman)object;
//            foreman.removeForeman();
//        } else if(className.equals("Brigade")) {
//            Brigade brigade = (Brigade)object;
//            brigade.removeBrigade();
//        } else if(className.equals("Order")) {
//            Order order = (Order)object;
//            order.removeOrder();
//        } else if(className.equals("Job")) {
//            Job job = (Job)object;
//            job.removeJob();
//        }
//    }
}
