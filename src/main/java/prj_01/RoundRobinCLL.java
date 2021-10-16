//Adrian Irizarry adrian.irizarry@upr.edu
package prj_01;
import java.util.concurrent.ThreadLocalRandom;


class Node {
    public int id;
    public Node next;
    public Node previous;
    public Boolean proccessed_flag;

    public Node (int id) {
        this.id = id;
        proccessed_flag = true;
    }
}

interface RoundRobinCLLInterface {
    abstract void findEmptySlot();
    abstract void findFilledSlot();
}

public class RoundRobinCLL implements RoundRobinCLLInterface {
    private int num_nodes = 5;
    public Node head = null;
    public Node tail = null;
    public Boolean stopLoop = false;
    private int termination_limit;

    /**
     * Method that tells the current thread to wait a certain amount of time. This is possible by using the function nextInt
     * which returns a value between the two numbers that we give it, in this case the number that we are going to get is between
     * 500 and 2999.
     *
     * @return void
     */
    private void holdon() {
        try{ // We use a try and catch in case we get an error, like an interrupted exception that could possibly happen.
            Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(500, 3000)); // Tells the thread to wait between 500 and 2999.
        }
        catch(Exception e){
            System.out.println("Something went wrong.");
        }
    }

    @Override
    /**
     * This method constructs a string to show us the current thread that is running. Additionally constructs a string to show
     * the proccessed flag of each individual node, this is posible with string concatenation.
     *
     * @return A string with information of all the nodes and their proccessed flags.
     */
    public String toString () {
        String s = new String(""+ Thread.currentThread().getName() + " ");
        Node node = head;
        s+= "(Node-1: " + node.proccessed_flag + ")";
        s+= " ==> ";

        for (int i=1; i<num_nodes; i++) {
            node = node.next;
            s+= "(Node-"+(i+1)+": "+node.proccessed_flag + ")";
            if (i<num_nodes-1)
                s+= " ==> ";
        }
        return s;
    }

    /**
     * Method responsible for updating the processing flag of the specified node that we give the function.
     * @param node - Node that we want to change its processed flag.
     * @param set_slot - future processed flag.
     */
    private synchronized void holdRR(Node node, Boolean set_slot) {
        System.out.println("Thread " + Thread.currentThread().getName() + " Holding Resources");
        node.proccessed_flag = set_slot ; // Changes the processed flag to what we passed through the function.
        System.out.println("Thread " + Thread.currentThread().getName() + " Releasing Resources");
        if (set_slot) holdon();
    }

    /**
     * Method that goes through the round robin Cll and changes any node that has its processed flag true to false
     *
     * @return void
     */
    public void findEmptySlot() {
        holdon();
        /* PUT YOUR CODE HERE TO FIND AN EMPTY SLOT */
        /* STARTING FROM THE FIRST NODE IN THE LINKED LIST */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        /*** TO AVOID RACE CONDITION ***/
        Node current = head; // We start at the head of the linked list
        while (!stopLoop) {
            if (current.proccessed_flag.equals(true)) { // Checks if we found a processed flag
                holdRR(current,false); // Tells the threads to change its processed flag to false
            }
            current = current.next; // Continue onto the next node.
        }
    }

    /**
     * Method that goes through the round robin Cll and does the opposite of findEmptySlot(), this being changing any node that has its processed flag false
     * to true.
     */
    public void findFilledSlot() {
        /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
        /* FOR THE MAIN PROCESS                        */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        holdon();
        int count = 0 ; // Counter to keep count of number of iterations we have done.
        Node current = head;
        while (!stopLoop) {
            /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
            if (current.proccessed_flag.equals(false)) {
                holdRR(current,true);
            }
            current = current.next;
            count++;
            if (count>termination_limit) break; // If the counter reaches the number of iterations that we want, we finish the task
            System.out.println("Main Move No.: " + count%num_nodes + "\t" + toString()); // Prints all the info about nodes and their processed_flags
        }
    }

    /**
     * Method that initializes a doubly circular linked list. This is done by creating the set amount of nodes, determined by the variable num_nodes
     * and then we connect each node until we reach the tail where the next instead of being null like in a normal list we connect to the head therefore creating
     * what we call a circular linked list as we can visualize it as so.
     */
    private void fillRoundRubin () {
        /* PUT YOUR CODE HERE INITIATE THE CIRCULAR LINKED LIST */
        /* WITH DESIRED NUMBER OF NODES BASED TO THE PROGRAM   */
        int counter = 2; // This counter keeps track of how many nodes have been created. It starts at 2 because I firstly took care of the head and tail
                        // meaning the loop will start creating the nodes that will be between head and tail. (On the next lines it will be more clear, hopefully :).)
        Node nHead = new Node(1); // Creates the first node.
        head = nHead; // Assigns the first node to the head.
        head.proccessed_flag = true;
        head.previous = tail; // Connect head with tail so it is a circular linked list.
        Node nTail = new Node(num_nodes); // Creates the last node.
        tail = nTail; // Assigns the last node to tail.
        tail.proccessed_flag = true;
        tail.next = head; // Connect tail with head.
        Node current = head; // Temp variable to save value. Later we will use it so we know whats the next node we need to work on.
        Node currentLast = tail; // Temp variable to save value. Later we will use it so we know whats the next node we need to work on.

        while (counter < num_nodes) { // Loop to create the remaining nodes between head and tail.
            Node newNode = new Node(counter); // New node to be added to the Cll.
            newNode.next = currentLast; // We set the next of the newnode to point to the next node in the list.
            newNode.previous = current; // We set the previous of the newnode to point to the previous node in the list.
            current.next = newNode; // Finally we connect the previous node to the new node that will be after this one.
            currentLast.previous = newNode; // Connects the node ahead of the new node.

            // After these few lines we have succesfully connected the newNode in between the head and tail meaning we should have:
            // tail <- head -> <- newNode -> <- tail -> head
            // And as we iterate we should have:
            // tail <- head -> <- newNode -> <-newNode2 -> <- ... <- tail -> head

            current = current.next; // Like mentioned in line 136 we update the value so we know which one is the node before the new node we will create.
            currentLast = currentLast.previous; // Like mentioned in line 137 we update the value so we know which one is the node after the new node we will create.
            counter++; // Add to the counter to keep track of how many nodes we have created.
        }
        current.next = tail; // Connect the last node that was created to the tail so we finally finish the Cll.


    }

    public RoundRobinCLL(int num_nodes, int termination_limit) {
        this.num_nodes = num_nodes;
        this.termination_limit = termination_limit;
        fillRoundRubin();
    }
    public RoundRobinCLL(int num_nodes) {
        this.num_nodes = num_nodes;
        fillRoundRubin();
    }

    public RoundRobinCLL() {
        fillRoundRubin();
    }

}
