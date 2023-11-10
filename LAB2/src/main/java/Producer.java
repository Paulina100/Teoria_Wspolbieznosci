public class Producer implements Runnable{
    private Monitor monitor;
    public Producer(Monitor monitor){
        this.monitor = monitor;
    }

    public void run(){
        for (int i = 0; i < 1000; i++) {
            monitor.produce();
        }
    }
}
