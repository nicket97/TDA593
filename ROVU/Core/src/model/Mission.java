package model;

import java.util.ArrayList;
import java.util.BitSet;
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

    public MissionPoint[] getMissionPoints() {
        return (MissionPoint[]) mission.toArray();
    }

    public List<MissionPoint> getMission() {
        return mission;
    }

    public void updateMissionList() {
        for (int i = 0; i < mission.size(); i++){
            if(mission.get(i).isDone()){
                doneMissions.add(mission.get(i));
                mission.remove(i);
            }
        }
    }
}
