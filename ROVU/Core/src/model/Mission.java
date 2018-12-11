package model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Mission {
    List<MissionPoint> mission = new ArrayList<>();
    List<MissionPoint> doneMissions = new ArrayList<MissionPoint>();
    public Mission( List<MissionPoint> m){
        mission = m;

    }
    public void pointDone(MissionPoint p){
doneMissions.add(p);
    }
}
