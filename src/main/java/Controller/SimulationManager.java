package Controller;

import Model.Server;
import Model.Task;
import View.View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SimulationManager implements Runnable {
   private Scheduler scheduler;
   private View view;
   private ArrayList<Task> tasks;
   private SelectionPolicy selectionPolicy;
   private File file;

    private int maxArrival;
    private int maxService;
    private int minArrival;
    private int minService;
    private int numT; //NumberOfClients/Tasks
    private int numS; //numberOfServers/Queues
    private int simInterval; // time limit/simulation interval

    public SimulationManager(View view, int maxArrival, int maxService, int minArrival, int minService, int numT, int numS, int simInterval) {
        this.maxArrival = maxArrival;
        this.maxService = maxService;
        this.minArrival = minArrival;
        this.minService = minService;
        this.numT = numT;
        this.numS = numS;
        this.simInterval = simInterval;

        this.view = view;
        this.file =openFile();
        scheduler=new Scheduler(numS);
        scheduler.changeStrategy(SelectionPolicy.SHORTEST_QUEUE);//SHORTEST_TIME
                                                                    //SHORTEST_QUEUE
        tasks = new ArrayList<Task>();
        generateRandomTasks();
        Collections.sort(tasks);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void generateRandomTasks(){
        int id,arrivalT,serviceT;
        id=0;
        for(int i=0;i<numT;i++) {
            id++;
            arrivalT = getRandomNumber(minArrival, maxArrival);
            serviceT = getRandomNumber(minService, maxService);
            Task task = new Task(id, arrivalT, serviceT);
            this.tasks.add(task);
        }
    }

    @Override
    public synchronized void run() {
        int currentTime=0;
        float avergeWaitnigTime=0;
        float avrServiceTime=this.getAvrServiceTime();
        int peekH=0;
        int ok=0;
        int maxTasks=0;
        int over=0;
        writeFile("---------------START---------------\n\n");
        while(currentTime<=simInterval) {
            ArrayList<Task> removed=new ArrayList<Task>();
             for (Task t: tasks) {
                if(t.getArrivalT()==currentTime){
                    ok=1;
                    scheduler.dispatchTask(t);
                    removed.add(t);
                }
            }
            for(Task t:removed){
                tasks.remove(t);
            }

            // afisare ----------------------------------------------------------------------------:
            System.out.println("Time: "+currentTime);
            writeFile("Time: "+currentTime+"\n");
            boolean serversEmpty=true;
            for(int i=0;i<numS;i++){
                if(scheduler.getServers().get(i).getServerSize()!=0) {
                    serversEmpty = false;
                    break;
                }
            }

            //-- afis waiting line:
            this.afis();
            view.dispose();
            view =new View();
            view.setVisible(true);
            view.timeIs(currentTime);
            view.addNewServer(tasks);  // prima lista waiting list
            //-- end waitnig line

            scheduler.afis(view,numS,file);  // afisam cozile actuale

            if(tasks.isEmpty()&&serversEmpty) {
                System.out.println("Averge Waiting Time is: "+(float)avergeWaitnigTime/numT);
                System.out.println("Averge Service Time is: "+avrServiceTime);
                System.out.println("Peek Hour is: "+peekH);
                writeFile("Averge Waiting Time is: "+(float)avergeWaitnigTime/numT+"\n");
                writeFile("Averge Service Time is: "+avrServiceTime+"\n");
                writeFile("Peek Hour is: "+peekH+"\n");
                writeFile("---------------END---------------\n\n\n\n");
                view.avrTimeIs((float)avergeWaitnigTime/numT);
                view.avrServiceIs(avrServiceTime);
                view.peekHourIs(peekH);
                over=1;
                break;
            }
            //afis end--------------------------------------------------------------------------------------------

            if(scheduler.getNumberTasks()>maxTasks){
                maxTasks=scheduler.getNumberTasks();
                peekH=currentTime;
            }
            currentTime++;
            if(ok==1 ||avergeWaitnigTime>=1)
                avergeWaitnigTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(over == 0) {
            System.out.println("Averge Waiting Time is: " + (float) avergeWaitnigTime / numT);
            System.out.println("Averge Service Time is: " + avrServiceTime);
            System.out.println("Peek Hour is: " + peekH);
            writeFile("Averge Waiting Time is: " + (float) avergeWaitnigTime / numT + "\n");
            writeFile("Averge Service Time is: " + avrServiceTime + "\n");
            writeFile("Peek Hour is: " + peekH + "\n");
            writeFile("---------------END---------------\n\n\n\n");
            view.avrTimeIs((float) avergeWaitnigTime / numT);
            view.avrServiceIs(avrServiceTime);
            view.peekHourIs(peekH);
        }
    }

    public float getAvrServiceTime(){
        int sum=0;
        for(Task t:tasks){
            sum+=t.getServiceT();
        }
        return (float)sum/tasks.size();
    }

    public File openFile() {
        File myObj = null;
        try {
            myObj = new File("logfile.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return myObj;
    }

    public void writeFile(String text){
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public synchronized void afis(){
            System.out.println("Waiting list:");
            writeFile("Waiting list:\n");
            for (Task a : tasks) {
                System.out.print(a.toString() + ", ");
                writeFile(a.toString() + ", ");
            }
            System.out.println("\n");
            writeFile("\n\n");
    }

}
