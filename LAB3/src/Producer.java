public class Producer implements Runnable{
    private Data data;
    public Producer(Data data){
        this.data = data;
    }

    public void run(){
        while (true){
            data.produce();
        }
    }
}

