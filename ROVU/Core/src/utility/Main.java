package utility;
import java.awt.Color;
import java.util.*;

import controller.RobotController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Hospital;
import model.Node;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.A_Star;
import robot.RobotHandler;
import simbad.sim.EnvironmentDescription;
import view.MissionEditorView;

@SuppressWarnings("unused")
public class Main extends Application{
	
	public static void main(String[] args)  throws InterruptedException {
        EnvironmentDescription e = new EnvironmentDescription();
        Color color = Color.BLUE;

        Hospital hospital = new Hospital(0.5,e);
        hospital.generateEmptyGrid(40, 0.5);
        Set<RobotHandler> robots = new HashSet<>();
        Point[] startingPoints = {new Point(-6,-2.5), new Point(-1.5,-2.5), new Point(1.5,-2.5), new Point(6,-2.5)};
        Point[] middlePoints = {new Point(-6.8,2.5), new Point(-2.3,2.5), new Point(2.3,2.5), new Point(6.8,2.5)};
        Point[] endPoints = {new Point(-7.5,-4), new Point(-3,-4), new Point(3,-4), new Point(7.5,-4)};

        RobotController rc = RobotController.getController();
        rc.addRobots(4 ,startingPoints);

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
            concat.addAll(Arrays.asList(secondPath));
            // Return it as an array
            Point [] way = new Point [concat.size()];
            concat.toArray(way);

            for (Point f : way){
                System.out.println("For robot "+r.getName()+" X:"+f.getX()+ " Z:"+f.getZ());
            }
            r.setPath(way);
            robots.add(r);
        }

        AbstractSimulatorMonitor <RobotHandler> controller = new SimulatorMonitor(robots, e);

        // Launch the monitoring
        // launch(args);
	}

	@Override
    public void start(Stage primaryStage) throws Exception {
        Application missionEditor = new MissionEditorView();
        missionEditor.start(primaryStage);
    }

    private static Point [] task (Hospital hospital, Point start, Point finish){
        A_Star test = new A_Star();
        test.init(hospital.pointNode(start, 0.5), hospital.pointNode(finish, 0.5)); //-6.8,-2.5
        List <Node> rpath = test.getRouteList(test.findRoute());
        Point [] commands = new Point [rpath.size()];
        for (int m=0;m<rpath.size();m++){
            commands[m]=hospital.getNodeCenter(rpath.get(m),0.5);//test.getNodeCenter(path.get(m), 1);
        }
        return commands;
    }

}
