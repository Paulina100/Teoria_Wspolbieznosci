public class Consument implements Runnable{
    private Data data;
    public Consument(Data data){
        this.data = data;
    }

    public void run(){
        while (true){
            data.consume();
        }
    }
}
