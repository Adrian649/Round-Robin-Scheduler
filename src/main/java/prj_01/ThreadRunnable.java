//Adrian Irizarry adrian.irizarry@upr.edu
package prj_01;
public class ThreadRunnable implements Runnable {

    private boolean doStop = false;
    private RoundRobinCLL rr = null;

    /* Constructors */
    public ThreadRunnable() {
    }
    public ThreadRunnable(RoundRobinCLL rr) {
        this.rr = rr;
    }


    /**
     * Method that tells each thread what to run. In this case we print which thread is currently running and then we tell each thread to run findEmptySlot()
     * For more details about findEmptySlot() look at the RoundRobinCLL class.
     */
    @Override
    public void run() {
        System.out.println("Running Thread... This is Thread " + Thread.currentThread().getName()); // Prints thread that is currently running.
        if (rr==null) {
            return;
        }
        while  (!rr.stopLoop) {
            // keep doing what this thread should do.
            rr.findEmptySlot(); // Runs findEmptySlot() as long as we are told to not stop the loop, in other words as long as we dont reach the termination limit.
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " Finished ... Bye Bye"); // Notification letting us know that this thread
                                                                                                    // is done with its process.
    }
}
