//Adrian Irizarry adrian.irizarry@upr.edu
package prj_01;
import java.util.*;
public class Threads {
    public ArrayList<Thread> threads = new ArrayList<Thread>(); // Array containing all threads to be created.


    public Threads(int noThreads){
       for (int i=0; i<noThreads; i++){
           ThreadRunnable runnable = new ThreadRunnable();
           System.out.println("Creating Thread " + (i+1));
           threads.add(new Thread(runnable, ""+i));
       }
    }

    /**
     * Method responsible for creating each thread.
     * @param noThreads - Number of threads to be created.
     * @param runnable - interface that is needed to execute thread.
     */
    public Threads(int noThreads, ThreadRunnable runnable){
        for (int i=0; i<noThreads; i++){
            System.out.println("Creating Thread " + (i+1));
            threads.add(new Thread(runnable, ""+i));
        }
    }
}
