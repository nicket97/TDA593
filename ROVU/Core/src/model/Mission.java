package model;

import java.util.ArrayList;
import java.util.List;

public class Mission {
    private List<MissionPoint> mission;

    public Mission(List<MissionPoint> missionPoints) {
        mission = missionPoints;
    }

    // Simplified view of mission
    @Override
    public String toString() {
        List<String> points = new ArrayList<>();
        mission.forEach(point -> {
            double x = point.getX();
            double z = point.getZ();
            String pointString = "(" + x + ", " + z + ")";
            points.add(pointString);
        });
        return points.toString();
    }

    public List<MissionPoint> getMission() {
        return mission;
    }
}
