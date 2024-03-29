import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Brigade {
    private static long ID;
    public long identifyNumber;

    String brigadeName;
    private Foreman foreman;
    private final HashMap<Long, Object> brigades = new HashMap<>();
    private final List<Worker> brigadeWorkers = new ArrayList<>();

    Brigade(String brigadeName, Foreman foreman) {
        this.identifyNumber = ID++;
        this.brigadeName = brigadeName;
        this.foreman = foreman;
        this.foreman.addBrigade(this);
        this.brigades.put(this.identifyNumber, this);
        ItemWrapperOrdering.addItem("4", brigades);
    }

    public void addWorker(Worker worker) {
        if(worker instanceof User && !(worker instanceof Foreman)) {
            System.out.println("Klasa Użytkownik nie może zostać dodana do brygady");
            return;
        }
        this.brigadeWorkers.add(worker);
    }

    public void addWorkers(List<Worker> workers) {
        for(Worker worker : workers) {
            this.addWorker(worker);
        }
    }

    Foreman getForeman() {
        return this.foreman;
    }

    public List<Worker> getWorkers() {
        return this.brigadeWorkers;
    }

    void removeBrigade() {
        brigades.remove(this.identifyNumber);
        ItemWrapperOrdering.addItem("4", brigades);
    }



    @Override
    public String toString() {
        return "{Klasa Brygada o  identyfikatorze - " + "(" + this.identifyNumber + ")," +
                " Nazwa brygady: " + this.brigadeName + "," +
                " Brygadzista: " + this.foreman + "," +
                " Lista pracowników w brygadzie: " + this.getWorkers().toString() +"}";

    }
}
