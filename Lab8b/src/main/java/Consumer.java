import org.jcsp.lang.*;


/** Consumer class: reads one int from input channel, displays it, then
 * terminates.
 */
public class Consumer implements CSProcess{
    private One2OneChannelInt in;
    private One2OneChannelInt req;

    public Consumer(final One2OneChannelInt req, final One2OneChannelInt in) {
        this.req = req;
        this.in = in;
    }

    public void run() {
        int item;
        while (true) {
            req.out().write(0);
            item = in.in().read();
            if (item < 0)
                break;
            System.out.println("consume " + item);
        }
        System.out.println("Consumer ended.");
    }
}
