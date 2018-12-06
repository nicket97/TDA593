package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Mission {
    private List<Point> mission;
    private List<Strategy> strategies;

    public Mission(List<Point> missionPoints) {
        mission = missionPoints;
    }

    @Override
    public String toString() {
        List<String> points = new ArrayList<>();
        mission.forEach(point -> {
            double x = point.getX();
            double y = point.getY();
            String pointString = "(" + x + "," + y + ")";
            points.add(pointString);
        });
        return points.toString();
    }

}
