import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
    private static long ID;
    public long identifyNumber;

    public enum OrderType {PLANOWANE, NIEPLANOWANE};
    public enum OrderState {UTWORZONE, ROZPOCZETE, ZAKONCZONE};
    private List<Job> orderJobs = new ArrayList<>();
    private static final HashMap<Long, Object> allOrders = new HashMap<>();

    private OrderType orderType;
    OrderState orderState;
    private Brigade orderBrigade;
    LocalDateTime createDate;
    private LocalDateTime progressDate;
    LocalDateTime endDate;

    Thread currentThread;

    Order(boolean isPlanned, Brigade brigade) {
        this.identifyNumber = ID++;
        this.setOrderType(isPlanned);
        this.orderState = OrderState.UTWORZONE;
        this.orderBrigade = brigade;
        this.orderBrigade.getForeman().addOrder(this);
        this.createDate = LocalDateTime.now();
        this.progressDate = null;
        this.endDate = null;
        allOrders.put(this.identifyNumber, this);
        ItemWrapperOrdering.addItem("5", allOrders);
    }

    void removeOrder() {
        allOrders.remove(this.identifyNumber);
        ItemWrapperOrdering.addItem("5", allOrders);
    }

    public List<Job> getOrderWorks() {
        return this.orderJobs;
    }

    private void setOrderType(boolean isPlanned) {
        this.orderType = isPlanned ? OrderType.PLANOWANE : OrderType.NIEPLANOWANE;
    }

    public boolean addBrigade(Brigade brigade) {
        if(this.orderBrigade != null) return false;

        this.orderBrigade = brigade;
        return true;
    }

    public void addJob(Job job) {
        this.orderJobs.add(job);
    }

    public void rozpocznijZlecenie() {
        if(this.getOrderState() != StanZlecenia.UTWORZONE) {
            System.out.println("Zlecenie o identyfikatorze " + this.identyfikator +
                    " nie moze zostac uruchomione ponownie, poniewaz zostało ukonczone badz jest w trakcie realizacji");
            return;
        }

        Thread thread = new Thread(this);
        this.currentThread = thread;
        if(!thread.isAlive()) {
            thread.start();
        }
    }

    private boolean checkAvailabilityWorkers() {
        return this.brygada.getWorkers().stream().anyMatch(Pracownik::getAvailabilityWorker);
    }

    boolean isOrderWorksAvailable() {
        return this.pracaList.stream().anyMatch(praca -> praca.getState() != Thread.State.NEW);
    }

    void setIfWorkerFromBrigadeWorkNow(boolean state) {
        for(Pracownik worker: this.brygada.getWorkers()) {
            worker.workerDoingWork(state);
        }
    }

    StanZlecenia getOrderState() {
        return this.stanZlecenia;
    }

    @Override
    public void variableUpdate() throws IOException {
        saveDataToFile(this.getClass().getName(), allOrders);
        loadDataFromFile(this.getClass().getName());
    }

    void interruptOrder() {
        this.currentThread.interrupt();
    }

    @Override
    public void run() {
        if(this.brygada == null || this.brygada.getWorkers().size() == 0 || this.pracaList.size() == 0) {
            System.out.println("Przed rozpoczeciem zlecenia dodaj brygade i liste prac do wykonania");
            return;
        }

        if(this.checkAvailabilityWorkers()) {
            System.out.println("Pracownicy z brygady w zleceniu o identydikatorze (" +
                    this.identyfikator + ") są zajeci, rozpoczecie zlecenia nie moze byc wykonane");
            return;
        }


        if(this.isOrderWorksAvailable()) {
            System.out.println("Praca znajdujaca sie w zleceniu o identyfikatorze (" +
                    this.identyfikator + ") zostala juz wykonana badz jest w trakcie realizacji");

            return;
        }

        int i = 0;
        for(Praca praca : this.pracaList) {
            praca.addWorksToQueue(this.pracaList.subList(0, i));
            praca.setOrderId(this.identyfikator);

            if(i < this.pracaList.size() - 1) {
                praca.setNextWork(this.pracaList.get(i + 1));
            }
            i++;
        }

        System.out.println("Rozpoczeto zlecenie o identyfikatorze - " + "(" + this.identyfikator + ")");
        this.stanZlecenia = StanZlecenia.ROZPOCZETE;
        this.dataRealizacji = LocalDateTime.now();

        for (Praca praca : this.pracaList) {
            praca.start();
        }

        this.setIfWorkerFromBrigadeWorkNow(true);

        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Zakończenie zlecenia o identyfikatorze - " + "(" + this.identifyNumber + ")");
        this.endDate = LocalDateTime.now();
        this.orderState = OrderState.ZAKONCZONE;

        this.setIfWorkerFromBrigadeWorkNow(false);
    }

    @Override
    public String toString() {
        return "{Klasa Zlecenie o identyfiktorze - " + "(" + this.identifyNumber + ")," +
                " Rodzaj zlecenia: " + this.orderType + "," +
                " Stan zlecenia: " + this.orderState + "." +
                " Data utworzenia zlecenia: " + this.createDate + "," +
                " Data realizacji zelcenia: " + this.progressDate + "," +
                " Data ukonczenia zelcenia: " + this.endDate + "}";
    }
}
