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
    public MissionPoint(double x, double z, List<Constraint> con){
        point = new Point(x, z);
        constraints = con;
        priority = 0;
    }
    public MissionPoint(double x, double z, int prio){
        point = new Point(x, z);
        priority = prio;
    }

    public MissionPoint(double x, double z, List<Constraint> con, int prio){
        point = new Point(x, z);
        constraints = con;
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

}
