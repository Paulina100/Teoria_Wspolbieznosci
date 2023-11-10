public class Consument implements Runnable{
    private Monitor monitor;
    public Consument(Monitor monitor){
        this.monitor = monitor;
    }

    public void run(){
        for (int i = 0; i < 2000; i++) {
            monitor.consume();
        }
    }
}