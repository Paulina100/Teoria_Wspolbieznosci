public class Producer implements Runnable{
    private Monitor monitor;
    private int id;
    private int portion = 0;

    public Producer(Monitor monitor){
        this.monitor = monitor;
    }

    public Producer(Monitor monitor, int id, int portion){
        this.monitor = monitor;
        this.id = id;
        this.portion = portion;
    }

    public void run(){
        while (true) {
            monitor.produce(this.id, this.portion);
        }
    }
}
