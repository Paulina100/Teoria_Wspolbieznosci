import org.jcsp.lang.*;

public class Producer implements CSProcess{
    private One2OneChannelInt res;
    private One2OneChannelInt req;
    private One2OneChannelInt[] bufferChannels;
    private int start;
    public long totalWaitTime = 0;

    public Producer(One2OneChannelInt req, One2OneChannelInt res, One2OneChannelInt[] bufferChannels, int start) {
        this.req = req;
        this.res = res;
        this.bufferChannels = bufferChannels;
        this.start = start;
    }
    public void run(){
        int item;
        for(int i = 0; i<1000; i++){
            item = (int) (Math.random()*100)+1+start;

            long startTime = System.nanoTime();
            // Send production request to the manager
            req.out().write(0);
            // Receive the buffer index from the manager
            int bufferIndex = res.in().read();
            long endTime = System.nanoTime();
            this.totalWaitTime += endTime - startTime;

            // Send the item to the buffer
            bufferChannels[bufferIndex].out().write(item);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Send last production (termination signal) request to the manager
        req.out().write(0);
        // Receive the buffer index from the manager
        int bufferIndex = res.in().read();

        // Send the termination signal to the buffer
        bufferChannels[bufferIndex].out().write(-1);
//        System.out.println("Producer " + start + " ended. Total wait time: " + totalWaitTime/1000000 + " ms");
    }
}
