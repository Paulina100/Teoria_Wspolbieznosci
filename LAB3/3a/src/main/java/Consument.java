public class Consument implements Runnable{
    private Monitor monitor;
    private int id;
    private int portion = 0;

    public Consument(Monitor monitor){
        this.monitor = monitor;
    }

    public Consument(Monitor monitor, int id, int portion){
        this.monitor = monitor;
        this.id = id;
        this.portion = portion;
    }

    public void run(){
        while (true) {
            monitor.consume(this.id, this.portion);
        }
    }
}