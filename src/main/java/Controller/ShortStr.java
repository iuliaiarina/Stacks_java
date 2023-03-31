package Controller;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.Collections;

//aici aleg serverul cu cei mai putini clienti:
public class ShortStr implements Strategy {
    @Override
    public synchronized void addTask(ArrayList<Server> servers, Task task) {
        int min=9000000;
        int i = 0;

        for (Server s: servers) {
            if (min > s.getServerSize()) {
                min = s.getServerSize();
                i = servers.indexOf(s);
            }
        }
        Server s=servers.get(i);
        s.addTask(task);

    }
}
