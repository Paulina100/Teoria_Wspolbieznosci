import org.jcsp.lang.*;

public class Producer implements CSProcess{
    private One2OneChannelInt channel;
    private int start;

    public Producer(final One2OneChannelInt out, int start){
        channel = out;
        this.start = start;
    }
    public void run(){
        int item;
        for(int i = 0; i<100; i++){
            item = (int) (Math.random()*100)+1+start;
            channel.out().write(item);
            System.out.println("produce " + item);
        }
        channel.out().write(-1);
        System.out.println("Producer " + start + " ended.");
    }
}
