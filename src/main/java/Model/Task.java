package Model;

public class Task implements Comparable<Task>{
    private final int id;
    private final int arrivalT;
    private int serviceT;

    public Task(int id, int arrivalT, int serviceT) {
        this.id = id;
        this.arrivalT = arrivalT;
        this.serviceT = serviceT;
    }

    public String toString(){
        String t="("+id+","+arrivalT+","+serviceT+")";
        return t;
    }

    public int getId() {
        return id;
    }

    public int getArrivalT() {
        return arrivalT;
    }

    public int getServiceT() {
        return serviceT;
    }

    public void decrementServicet(){
        this.serviceT=this.serviceT-1;
    }

    @Override
    public int compareTo(Task o) {
        if (this.arrivalT > o.getArrivalT()) return 1;
        else if (this.arrivalT == o.getArrivalT()) return 0;
        else return -1;
    }
}
