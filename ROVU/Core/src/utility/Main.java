package utility;
import java.awt.Color;
import java.util.*;

import controller.RobotController;
import model.*;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.A_Star;
import robot.RobotHandler;
import simbad.sim.EnvironmentDescription;
import view.ROVUView;

@SuppressWarnings("unused")
public class Main {

        public static void main(String[] args) throws InterruptedException {
                EnvironmentDescription e = new EnvironmentDescription();
                Color color = Color.BLUE;
                Hospital hospital = new Hospital(0.5, e);
                hospital.generateEmptyGrid(40, 0.5);

                // Hard-coded missions for different robots
                List<MissionPoint> mission = new ArrayList<>();
                mission.add(new MissionPoint(-6, -2.5, Constraint.ROBOT1, 3));
                mission.add(new MissionPoint(-6.8, 2.5, Constraint.ROBOT1, 2));
                mission.add(new MissionPoint(-7.5, -4, Constraint.ROBOT1, 1));
                mission.add(new MissionPoint(-1.5, -2.5, Constraint.ROBOT2, 3));
                mission.add(new MissionPoint(-2.3, 2.5, Constraint.ROBOT2, 2));
                mission.add(new MissionPoint(-3, -4, Constraint.ROBOT2, 1));
                mission.add(new MissionPoint(1.5, -2.5, Constraint.ROBOT3, 3));
                mission.add(new MissionPoint(-2.3, 2.5, Constraint.ROBOT3, 2));
                mission.add(new MissionPoint(3, -4, Constraint.ROBOT3, 1));
                mission.add(new MissionPoint(6, -2.5, Constraint.ROBOT4, 3));
                mission.add(new MissionPoint(6.8, 2.5, Constraint.ROBOT4, 2));
                mission.add(new MissionPoint(7.5, -4, Constraint.ROBOT4, 1));

                RobotController rc = RobotController.getController();
                rc.addRobot(new RobotHandler(new Point(-6, -2.5), "ROBOT 1", 0));
                rc.addRobot(new RobotHandler(new Point(-1.5, -2.5), "ROBOT 2", 1));
                rc.addRobot(new RobotHandler(new Point(1.5, -2.5), "ROBOT 3", 2));
                rc.addRobot(new RobotHandler(new Point(6, -2.5), "ROBOT 4", 3));
                rc.setEnvironment(hospital);
                rc.setMission(new Mission(mission));
                // TODO: Use the two following methods to execute the hard-coded missions
                // TODO: or the ones given from MissionEditor
                rc.executeMission();
                rc.initSimulator();

                // Launch the monitoring
                new Thread(() -> {
                        javafx.application.Application.launch(ROVUView.class);
                }).start();
        }

}