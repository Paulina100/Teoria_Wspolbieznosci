public class Consument implements Runnable{
    private Data data;
    public Consument(Data data){
        this.data = data;
    }

    public void run(){
        for (int i = 0; i < 1000; i++){
            data.consume();
        }
    }
}
