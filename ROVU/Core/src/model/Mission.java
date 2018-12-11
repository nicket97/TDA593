package model;

import java.util.ArrayList;
import java.util.List;

public class Mission {
    private List<MissionPoint> mission;
    private List<MissionPoint> finishedMission;


    public Mission(List<MissionPoint> missionPoints) {
        mission = missionPoints;
    }

    @Override
    public String toString() {
//        List<String> points = new ArrayList<>();
//        mission.forEach(point -> {
//            double x = point.getX();
//            double z = point.getZ();
//            String pointString = "(" + x + "," + z + ")";
//            points.add(pointString);
//        });
        return ""; // points.toString();
    }

    public void finishedPoint(MissionPoint p){
        finishedMission.add(p);
    }
}
