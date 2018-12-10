package model;

import project.Point;

import java.util.ArrayList;
import java.util.List;

public class MissionPoint {
    Point point;
    List<Constraint> constraints = new ArrayList<Constraint>();
    public MissionPoint(Point p){
        point = p;
    }
    public MissionPoint(Point p, List<Constraint> con){
        point = p;
        constraints = con;
    }
}
