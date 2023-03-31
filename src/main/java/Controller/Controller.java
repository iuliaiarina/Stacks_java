package Controller;

import Model.Task;
import View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    private View view;
    private int maxArrival;
    private int maxService;
    private int minArrival;
    private int minService;
    private int numT; //NumberOfClients/Tasks
    private int numS; //numberOfServers/Queues
    private int simInterval; // time limit/simulation interval

    private SimulationManager simulationManager;

    public Controller() {
        this.view =new View();
        view.setVisible(true);
        view.addStartListener(new StartListener()); // init schudler;
    }

    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String string;
                string = view.getMaxArrival();
                maxArrival = Integer.parseInt(string);
                string = view.getMaxService();
                maxService = Integer.parseInt(string);
                string = view.getMinArrival();
                minArrival = Integer.parseInt(string);
                if(minArrival>=maxArrival)throw new Exception();
                string = view.getMinService();
                minService = Integer.parseInt(string);
                if(minService>=maxService)throw new Exception();
                string = view.getNumT();
                numT = Integer.parseInt(string);
                string = view.getNumS();
                numS = Integer.parseInt(string);
                string = view.getSiminterval();
                simInterval = Integer.parseInt(string);
                view.validateMs();
                simulationManager = new SimulationManager(view,maxArrival,maxService,minArrival,minService,numT,numS,simInterval);
                Thread t =new Thread(simulationManager);
                t.start();
            }
            catch(Exception e1){
                view.unValidateMs();
            }
        }
    }

}
