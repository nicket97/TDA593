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
	
	public static void main(String[] args)  throws InterruptedException {
        EnvironmentDescription e = new EnvironmentDescription();
        Color color = Color.BLUE;

        Hospital hospital = new Hospital(0.5,e);
        hospital.generateEmptyGrid(40, 0.5);
        Set<RobotHandler> simRobots = new HashSet<>();
        Point[] startingPoints = {new Point(-6,-2.5), new Point(-1.5,-2.5), new Point(1.5,-2.5), new Point(6,-2.5)};
        Point[] middlePoints = {new Point(-6.8,2.5), new Point(-2.3,2.5), new Point(2.3,2.5), new Point(6.8,2.5)};
        Point[] endPoints = {new Point(-7.5,-4), new Point(-3,-4), new Point(3,-4), new Point(7.5,-4)};

        // Hard-coded missions for different robots
        List<MissionPoint> mission = new ArrayList<>();
        mission.add(new MissionPoint(-6, -2.5, Constraint.ROBOT1));
        mission.add(new MissionPoint(-6.8, 2.5, Constraint.ROBOT1));
        mission.add(new MissionPoint(-7.5, -4, Constraint.ROBOT1));
        mission.add(new MissionPoint(-1.5, -2.5, Constraint.ROBOT2));
        mission.add(new MissionPoint(-2.3, 2.5, Constraint.ROBOT2));
        mission.add(new MissionPoint(-3, -4, Constraint.ROBOT2));
        mission.add(new MissionPoint(1.5, -2.5, Constraint.ROBOT3));
        mission.add(new MissionPoint(-2.3, 2.5, Constraint.ROBOT3));
        mission.add(new MissionPoint(3, -4, Constraint.ROBOT3));
        mission.add(new MissionPoint(6, -2.5, Constraint.ROBOT4));
        mission.add(new MissionPoint(6.8, 2.5, Constraint.ROBOT4));
        mission.add(new MissionPoint(7.5, -4, Constraint.ROBOT4));

        RobotController rc = RobotController.getController();
        rc.init();
        rc.setEnvironment(hospital);

        // TODO: Use the two following methods to execute the hard-coded missions
        // TODO: or the ones given from MissionEditor
//        rc.initSimulator(); // We shouldn't expose our robots outside the robotController
//        rc.executeMission();
        // Launch the monitoring
        new Thread(() -> {
            javafx.application.Application.launch(ROVUView.class);
        }).start();
	}
}
