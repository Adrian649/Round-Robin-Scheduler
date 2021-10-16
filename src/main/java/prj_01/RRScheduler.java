//Adrian Irizarry adrian.irizarry@upr.edu
package prj_01;

public class RRScheduler {

    /**
     *Main function where we call each function that is responsible for the round robin scheduler to run
     *
     */
    public static void main(String[] args){
        int termination_limit = 100; // Iterations to be done
        int no_threads = 5; // Number of threads to be used
        int project_step = 2; // Step of the project that will get executed
        // These for loops are responsible for running the program with the command provided, the first one takes the value after -t and changes the termination limit.
        // Second loop takes the value after -p and changes the value of number of threads.
        // Third loop takes the value after -s and changes the value of the project step.
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-t") || args[i].equals("--termination")) {
                termination_limit = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-p") || args[i].equals("--processes")) {
                no_threads = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-s") || args[i].equals("--prjstep")) {
                project_step = Integer.valueOf(args[++i]);
                if (project_step!=1 && project_step!=2) {
                    System.out.println("Project Step value is 1 or 2 (" + project_step + " given).");
                    System.exit(1);
                }
            }
        }

        System.out.println("Starting Program...");


        RoundRobinCLL roundRobine = null;
        // If we are running the second step we initialize the Cll class.
        if (project_step==2) {
            roundRobine =  new RoundRobinCLL(12, termination_limit);
        }

        ThreadRunnable rrRunnable = new ThreadRunnable(roundRobine); // Initializes class that implements the runnable interface
        Threads threads = new Threads(no_threads, rrRunnable); // Initiliazes the threads

        // This loop goes through each loop and tells it to start their process.
        for (int i=0; i<threads.threads.size(); i++) {
            threads.threads.get(i).start();
        }
        // Tells the program that if the Cll is not empty to run findFilledSlot(). For more details about this function look at the RoundRobinCLL class.
        if (roundRobine!=null) roundRobine.findFilledSlot() ;

        System.out.println("Main Finished ... Bye Bye"); // Notification for when the main process has finished.

        if (roundRobine!=null) roundRobine.stopLoop = true; // Stops the process of findFilledSlot() and findEmptySlot()

    }
}
