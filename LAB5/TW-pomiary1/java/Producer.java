public class Producer implements Runnable{
    private Monitor monitor;

    public Producer(Monitor monitor){
        this.monitor = monitor;
    }

    public void run(){
        while (true) {
            monitor.produce();
        }
    }
}
