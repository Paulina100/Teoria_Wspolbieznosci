

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private One2OneChannelInt channel;

    public Producer(final One2OneChannelInt out) {
        channel = out;
    }

    public void run() {
        int item = (int) (Math.random() * 100) + 1;
        channel.out().write(item);
        System.out.println("Produce " + item);
    }

}
