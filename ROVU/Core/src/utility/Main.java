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

        // TODO: Remove the following section of directly manipulating the robots
        // TODO: Niclas to implement the run method of the RobotHandler to utilise
        // TODO: Anthony's pathfinding algorithm
        int u=0;
        for(RobotHandler r: rc.getRobots()){
            for (int h=0;h<startingPoints.length;h++){
                if (startingPoints[h].getX()==r.getStartingPoint().getX() &&
                    startingPoints[h].getZ()==r.getStartingPoint().getZ()){
                    u=h;
                }
            }
            // concatenate the first path and second path
            Point [] firstPath = task(hospital,startingPoints[u], middlePoints[u]);
            Point [] secondPath = task(hospital,middlePoints[u],endPoints[u]);
            List <Point> concat = new ArrayList<Point>(Arrays.asList(firstPath));
            // Exclude first point of secondPath to avoid duplicate points
            concat.addAll(Arrays.asList(secondPath).subList(1,secondPath.length));

            // Return it as an array
            Point [] way = new Point [concat.size()];
            concat.toArray(way);

            for (Point f : way){
                System.out.println("For robot "+r.getName()+" X:"+f.getX()+ " Z:"+f.getZ());
            }
            r.setPath(way);
            simRobots.add(r);
        }

        AbstractSimulatorMonitor <RobotHandler> controller = new SimulatorMonitor(simRobots, e);

        // Launch the monitoring
        new Thread(() -> {
            javafx.application.Application.launch(ROVUView.class);
        }).start();
	}

    // TODO: Move this to the RobotHandler so it can find its own path given
    // TODO: its personal mission
    private static Point [] task (Hospital hospital, Point start, Point finish){
        A_Star test = new A_Star();
        test.init(hospital.pointNode(start, 0.5), hospital.pointNode(finish, 0.5)); //-6.8,-2.5
        List <Node> rpath = test.getRouteList(test.findRoute());
        Point [] commands = new Point [rpath.size()+1];
        for (int m=0;m<rpath.size();m++){
            commands[m]=hospital.getNodeCenter(rpath.get(m),0.5);//test.getNodeCenter(path.get(m), 1);
        }
        commands[commands.length-1]=finish;
        return commands;
    }

}
