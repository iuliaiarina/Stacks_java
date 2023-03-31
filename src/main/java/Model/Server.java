package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
//  reprezinta o coada
public class Server implements Runnable,Comparable<Server> {
    private LinkedBlockingQueue<Task> tasks;  //taskurile se adauga in coada in functie de arrival time
    private AtomicInteger waitingPeriod; //reprezinta service time-ul pt intreaga coada

    public Server() {
        this.tasks = new LinkedBlockingQueue<Task>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task task) {
        tasks.add(task);
        waitingPeriod.addAndGet(task.getServiceT()); // adaug service timeul la suma
    }

    public void decrementWP(){
        waitingPeriod.addAndGet(-1);
    }

    @Override
    public synchronized void run() {
        while (true) { //serverele merg mereu
            if (!tasks.isEmpty()) {  // cand detecteaza un task il compileaza
                Task t = tasks.peek(); //aleg un task
                if(t.getServiceT() == 0){
                    tasks.poll();
                }
                /*
                Task t = tasks.poll();
                Thread.onSpinWait();
                try {
                    Thread.sleep(t.getServiceT() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tasks.poll();
                waitingPeriod.addAndGet(-t.getServiceT());
                */
            }
        }
    }

    public int getServerSize() {
        return tasks.size();
    }

   public ArrayList<Task> getTasksList() {
       ArrayList<Task> result = new ArrayList<Task>();
        for (Task t : tasks)
            result.add(t);
        return result;
    }


    public LinkedBlockingQueue<Task> getTasks() {
        return tasks;
    }

    @Override
    public int compareTo(Server o) {
        if (this.waitingPeriod.get() > o.waitingPeriod.get()) return 1;
        else if (this.waitingPeriod.get() == o.waitingPeriod.get()) return 0;
        else return -1;
    }

}
