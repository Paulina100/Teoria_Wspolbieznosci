public class Consumer implements Runnable{
    private Monitor monitor;

    public Consumer(Monitor monitor){
        this.monitor = monitor;
    }

    public void run(){
        while (true){
            monitor.consume();
        }
    }
}