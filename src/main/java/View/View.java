package View;

import Controller.SimulationManager;
import Model.Task;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame {

    private JPanel contentPane;
    private JTextField numT;
    private JTextField numS;
    private JTextField maxArrival;
    private JTextField minArrival;
    private JTextField maxService;
    private JTextField minService;
    private JTextField simInterval;
    private JLabel errorMesaj;
    private JButton startBtn;
    private JPanel panel1;
    private JPanel panel;
    private JPanel panelU;
    private JLabel time;
    private JLabel avrTime;//average waiting time
    private JLabel avrService; //average service time
    private JLabel ph;//peak hour

    public View() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 913, 392);//413 latime
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel1 = new JPanel();
        panel1.setBackground(new Color(250, 250, 210));
        panel1.setBounds(0, 0, 217, 353);
        contentPane.add(panel1);
        panel1.setLayout(null);

        JLabel lblNewLabel = new JLabel("Clients:");
        lblNewLabel.setBounds(10, 11, 121, 14);
        panel1.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Queues:");
        lblNewLabel_1.setBounds(10, 33, 121, 14);
        panel1.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Simulation time:");
        lblNewLabel_2.setBounds(10, 55, 121, 14);
        panel1.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Minimum arrival time:");
        lblNewLabel_3.setBounds(10, 80, 142, 14);
        panel1.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Maximum arrival time:");
        lblNewLabel_4.setBounds(10, 99, 142, 14);
        panel1.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Minimum service time:");
        lblNewLabel_5.setBounds(10, 121, 142, 14);
        panel1.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("Maximum service time:");
        lblNewLabel_6.setBounds(10, 143, 142, 14);
        panel1.add(lblNewLabel_6);

        numT = new JTextField();
        numT.setBounds(156, 8, 50, 20);
        panel1.add(numT);
        numT.setColumns(10);

        numS = new JTextField();
        numS.setBounds(156, 30, 50, 20);
        panel1.add(numS);
        numS.setColumns(10);

        simInterval = new JTextField();
        simInterval.setBounds(156, 52, 50, 20);
        panel1.add(simInterval);
        simInterval.setColumns(10);

        minArrival = new JTextField();
        minArrival.setBounds(156, 74, 50, 20);
        panel1.add(minArrival);
        minArrival.setColumns(10);

        maxArrival = new JTextField();
        maxArrival.setBounds(156, 96, 50, 20);
        panel1.add(maxArrival);
        maxArrival.setColumns(10);

        minService = new JTextField();
        minService.setBounds(156, 118, 50, 20);
        panel1.add(minService);
        minService.setColumns(10);

        maxService = new JTextField();
        maxService.setBounds(156, 140, 50, 20);
        panel1.add(maxService);
        maxService.setColumns(10);

        startBtn = new JButton("START");
        startBtn.setBackground(new Color(50, 205, 50));
        startBtn.setBounds(117, 195, 75, 50);
        panel1.add(startBtn);

        errorMesaj = new JLabel("");
        errorMesaj.setHorizontalAlignment(SwingConstants.CENTER);
        errorMesaj.setBounds(10, 195, 97, 61);
        panel1.add(errorMesaj);
        //panel1 -- stanga; panelU --sus ; panel jos dreapta
        panelU = new JPanel();
        panelU.setBounds(222, 0, 677, 25);
        contentPane.add(panelU);
        panelU.setLayout(new GridLayout(1, 0, 0, 0)); //paneluri pe coloane

        panel = new JPanel();
        panel.setBounds(222, 24, 677, 319);
        contentPane.add(panel);
        panel.setLayout(new GridLayout(1, 0, 0, 0)); //paneluri pe coloane

        JLabel titlu = new JLabel("Waiting server:");
        panelU.add(titlu);
    }

    /*adaug titlu la server*/
    public void addToUpPanel(String text){
        JLabel titlu = new JLabel(text);
        panelU.add(titlu);
    }

    /*adaug panel nou la server*/
    public synchronized void addNewServer(ArrayList<Task>  tasks){
        Object [] data = new Object[10000];
        int i=0;
        for (Task t:tasks) {
            data[i]=t.toString();
            i++;
        }
        JList list = new JList(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        panel.add(list);
    }

    public void timeIs(int t){
        this.time = new JLabel();
        time.setBounds(10, 180, 142, 14);
        panel1.add(time);
        this.time.setText("Time: "+t);
    }

    public  void peekHourIs(float x){
        ph =new JLabel();
        ph.setBounds(10, 300, 162, 14);
        panel1.add(ph);
        this.ph.setText("peak hour: "+x);
    }

    public  void avrServiceIs(float x){
        avrService =new JLabel();
        avrService.setBounds(10, 280, 162, 14);
        panel1.add(avrService);
        this.avrService.setText("average service time: "+x);
    }

    public  void avrTimeIs(float x){
        avrTime =new JLabel();
        avrTime.setBounds(10, 260, 162, 14);
        panel1.add(avrTime);
        this.avrTime.setText("average waiting time: "+x);
    }

    public String getNumS() {
        return numS.getText();
    }
    public String getNumT() {
        return numT.getText();
    }
    public String getSiminterval() {
        return simInterval.getText();
    }
    public String getMaxArrival() {
        return maxArrival.getText();
    }
    public String getMinArrival() {
        return minArrival.getText();
    }
    public String getMaxService() {
        return maxService.getText();
    }
    public String getMinService() {
        return minService.getText();
    }

    public void validateMs() {
        errorMesaj.setText("Validat!");
    }
    public void unValidateMs() {
        errorMesaj.setText("Date incorecte!");
    }
    public void addStartListener(ActionListener x) {
        startBtn.addActionListener(x);
    }

}