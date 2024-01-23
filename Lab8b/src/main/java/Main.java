import org.jcsp.lang.*;

/** Main program class for Producer/Consumer example.
 * Sets up channel, creates one of each process then
 * executes them in parallel, using JCSP.
 */
public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        final One2OneChannelInt[] prodChan = {Channel.one2oneInt(), Channel.one2oneInt()};
        final One2OneChannelInt[] consReq = {Channel.one2oneInt(), Channel.one2oneInt()};
        final One2OneChannelInt[] consChan = {Channel.one2oneInt(), Channel.one2oneInt()};

        CSProcess[] procList = {new Producer(prodChan[0], 0),
                new Producer(prodChan[1], 100),
                new Buffer(prodChan, consReq, consChan),
                new Consumer(consReq[0], consChan[0]),
                new Consumer(consReq[1], consChan[1])};
        Parallel par = new Parallel(procList);
        par.run();
    }
}