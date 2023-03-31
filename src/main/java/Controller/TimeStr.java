package Controller;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.Collections;

//aici aleg serverul cu cel mai mic waiting time:
public  class TimeStr implements Strategy {
    @Override
    public synchronized void addTask(ArrayList<Server> servers, Task task) {
        ArrayList<Server> s=new ArrayList<Server>();
        for(Server s1:servers)
            s.add(s1);
        Collections.sort(s);// sortate dupa Waiting time
        for(Server s1:servers)
            if(s1.equals(s.get(0)))
                s1.addTask(task); //il i-au pe primul si ii adaug taskul
    }
}
