package Controller;

import Model.Server;
import Model.Task;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private int NoServers;
    private Strategy strategy;

    public Scheduler(int NoServers) {
        this.servers = new ArrayList<>();
        this.NoServers = NoServers;
        for(int i=0;i<NoServers;i++){;
            Server s=new Server();
            servers.add(s);
            Thread t=new Thread(s);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy selectionPolicy){
        if(selectionPolicy.equals(SelectionPolicy.SHORTEST_QUEUE))
          this.strategy=new ShortStr();
        else if(selectionPolicy.equals(SelectionPolicy.SHORTEST_TIME))
          this.strategy=new TimeStr();
    }

    public synchronized void dispatchTask(Task task){
        strategy.addTask(servers,task);
    }

    /*afisam cozile actuale + numele cozii
    * parcurgem intreaga lista de servere
    * alegem un server*/
    public synchronized void afis (View view, int numS, File file){
        for(int j=0;j<servers.size();j++) {
            Server server=servers.get(j);
            String title="Server: "+j;
            System.out.println(title);
            writeFile(title+"\n",file);
            view.addToUpPanel(title);
            view.addNewServer(server.getTasksList()); //adaug un server lla view +titlu
            for (Task a : server.getTasks()) {
                System.out.print(a.toString() + ", ");
                writeFile(a.toString() + ", ",file);
            }
            System.out.println("\n");
            writeFile("\n\n",file);
            if(server.getTasks().peek()!=null){
                server.getTasks().peek().decrementServicet();
                server.decrementWP();
            }
        }
    }

    public void writeFile(String text,File file){
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public int getNumberTasks(){
        int nrTasks=0;
        for (Server s:servers) {
            nrTasks+=s.getServerSize();
        }
        return nrTasks;
    }

}
