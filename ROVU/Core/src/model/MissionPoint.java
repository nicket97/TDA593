package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import project.Point;

import java.util.ArrayList;
import java.util.List;

public class MissionPoint implements Comparable {
    private Point point;
    private List<Constraint> constraints = new ArrayList<Constraint>();
    private int priority;
    private RobotAction action;
    private BooleanProperty done = new SimpleBooleanProperty(false);

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

    public MissionPoint(double x, double z, RobotAction a) {
        this(x, z);
        action = a;
    }

    public MissionPoint(double x, double z, Constraint con, RobotAction a) {
        this(x, z, con);
        action = a;
    }

    public MissionPoint(double x, double z, List<Constraint> con, RobotAction a) {
        this(x, z, con);
        action = a;
    }

    public void done() {
        done.setValue(true);
    }

    public double getX() {
        return point.getX();
    }

    public double getZ() {
        return point.getZ();
    }
    
    @Override
    public String toString() {
        return point.toString() + "\t prio:" + priority + "\t cons:" + constraints.toString();
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
        return done.getValue();
    }

    public BooleanProperty doneProperty() {
        return done;
    }

    public void robotDoAction(){
        action.performAction();
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public int compareTo(Object o) {
        return priority;
    }
}
