package model;

import project.Point;

import java.util.ArrayList;
import java.util.List;

public class MissionPoint {
    Point point;
    List<Constraint> constraints = new ArrayList<Constraint>();
    int priority;
    RobotAction action;
    boolean done = false;

    public MissionPoint(Point p){
        point = p;
        priority = 0;
    }
    public MissionPoint(Point p, List<Constraint> con){
        point = p;
        constraints = con;
        priority = 0;
    }
    public MissionPoint(Point p, int prio){
        point = p;
        priority = prio;
    } public MissionPoint(Point p, List<Constraint> con, int prio){
        point = p;
        constraints = con;
        priority = prio;
    }
    public void done(){
        done = true;
    }

}
