package model;

import project.Point;

import java.util.ArrayList;
import java.util.List;

public class MissionPoint {
    private Point point;
    private List<Constraint> constraints = new ArrayList<Constraint>();
    private int priority;
    private RobotAction action;
    private boolean done = false;

    public MissionPoint(double x, double z){
        point = new Point(x, z);
        priority = 0;
    }

    public MissionPoint(double x, double z, Constraint constraint) {
        this(x, z);
        constraints.add(constraint);
    }

    public MissionPoint(double x, double z, List<Constraint> con){
        this(x, z);
        constraints.addAll(con);
    }
    public MissionPoint(double x, double z, int prio){
        this(x, z);
        priority = prio;
    }

    public MissionPoint(double x, double z, Constraint con, int prio){
        this(x, z, con);
        priority = prio;
    }

    public MissionPoint(double x, double z, List<Constraint> con, int prio){
        this(x, z, con);
        priority = prio;
    }

    public void done(){
        done = true;
    }

    public double getX() {
        return point.getX();
    }

    public double getZ() {
        return point.getZ();
    }
    
    @Override
    public String toString() {
        return point.toString() + "\t prio:" +  priority + "\t cons:" + constraints.toString();
    }
}