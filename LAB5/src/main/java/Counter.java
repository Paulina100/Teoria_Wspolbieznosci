import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Counter {
    private int currOperation;
    private final int noOperations;
    private final long startTime;
    private final long startTimeCPU;
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    public Counter(int noOperations) {
        this.currOperation = 0;
        this.noOperations = noOperations;
        this.startTime = System.nanoTime();
        this.threadMXBean.setThreadCpuTimeEnabled(true);
        this.startTimeCPU = threadMXBean.getCurrentThreadCpuTime();
    }

    public void add(){
        this.currOperation += 1;

        if (this.currOperation >= noOperations){
            long endTime = System.nanoTime();
            System.out.println((endTime - this.startTime)/1000000 +" ms");

            long endTimeCPU = threadMXBean.getCurrentThreadCpuTime();
            System.out.println("CPU " + (endTimeCPU - this.startTimeCPU)/1000000 +" ms");
            System.exit(0);
        }
    }
}
