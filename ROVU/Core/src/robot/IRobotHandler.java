package robot;

import model.MissionPoint;

public interface IRobotHandler {
    void addMissionPoint(MissionPoint missionPoint);
    void executeMission();
    void stop();
}
