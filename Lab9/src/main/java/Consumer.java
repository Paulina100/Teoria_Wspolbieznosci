import org.jcsp.lang.*;


/** Consumer class: reads one int from input channel, displays it, then
 * terminates.
 */
public class Consumer implements CSProcess{
    private One2OneChannelInt res;
    private One2OneChannelInt req;
    private One2OneChannelInt[] bufferChannels;
    public int count = 0;
    public long totalWaitTime = 0;

    public Consumer(final One2OneChannelInt req, final One2OneChannelInt res, final One2OneChannelInt[] bufferChannels) {
        this.req = req;
        this.res = res;
        this.bufferChannels = bufferChannels;
    }

    public void run() {
        int item;
        int bufferIndex;
        while (true) {
            long startTime = System.nanoTime();
            // Send consumption request to the manager
            req.out().write(0);
            // Receive the buffer index from the manager
            bufferIndex = res.in().read();
            long endTime = System.nanoTime();
            this.totalWaitTime += endTime - startTime;

            // Receive the item from the buffer
            item = bufferChannels[bufferIndex].in().read();
            count++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // -1: termination signal
            if (item < 0)
                break;
        }
//        System.out.println("Consumer ended with count " + count + ". Total wait time: " + totalWaitTime/1000000 + " ms");
    }
}
