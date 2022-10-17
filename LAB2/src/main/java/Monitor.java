public class Monitor {
    public boolean emptyBufor = true;

    public synchronized void produce(){
        while (!emptyBufor){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        emptyBufor = false;
        notify();
        System.out.println("produced");
    }

    public synchronized void consume(){
        while (emptyBufor){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        emptyBufor = true;
        notify();
        System.out.println("consumed");
    }
}
