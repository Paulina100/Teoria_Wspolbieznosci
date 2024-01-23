
import org.jcsp.lang.*;

public class PCMain {
    public static void main(String[] args) {
        new PCMain();
    }

    public PCMain() {
        final One2OneChannelInt channel = Channel.one2oneInt();
        for (int i = 0; i<10; i++) {
            CSProcess[] procList = {new Producer(channel), new Consumer(channel)};
            Parallel par = new Parallel(procList);
            par.run();
        }
    }
}
