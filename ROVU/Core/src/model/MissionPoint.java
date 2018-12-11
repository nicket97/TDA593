package model;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import project.Point;

import java.util.ArrayList;
import java.util.List;

public class MissionPoint {
    private Point point;
    private List<Constraint> constraints = new ArrayList<Constraint>();
    private int priority;
    private RobotAction action;
    private boolean done = false;

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
    public MissionPoint(Point p, List<Constraint> con, int prio, RobotAction a){
        point = p;
        constraints = con;
        priority = prio;
        action = a;
    }
    public void done(){
        done = true;
    }

    public int getRobot() {
        for(Constraint c : constraints){
            if (c == Constraint.ROBOT1){
                return 1;
            }
            if (c == Constraint.ROBOT2){
                return 2;
            }
            if (c == Constraint.ROBOT3){
                return 3;
            }
            if (c == Constraint.ROBOT4){
                return 4;
            }

        }
        return 0;
    }

    public boolean isDone() {
        return done;
    }
    public void robotDoAction(){
        action.performAction();
    }
}
