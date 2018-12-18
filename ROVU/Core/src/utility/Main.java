package utility;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.RobotController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Hospital;
import model.Node;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.RobotHandler;
import simbad.sim.AbstractWall;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;
import simbad.sim.Wall;
import view.MissionEditorView;

@SuppressWarnings("unused")
public class Main extends Application{

	public static List <Boundary> bounds;
	public static List <Wall> walls;
	
	public static void main(String[] args)  throws InterruptedException {

		    EnvironmentDescription e = new EnvironmentDescription();
			
			Color color = Color.BLUE;
			bounds = new ArrayList<Boundary>();
			walls=new ArrayList<Wall>();
			
			Hospital host = new Hospital(1.0,e);

			Set<RobotHandler> robots = new HashSet<>();
			Point[] startingPoints = {new Point(-6.8,-2.5), new Point(-2.3,-2.5), new Point(2.3,-2.5), new Point(6.8,-2.5)};
			RobotController rc = new RobotController(4 ,startingPoints);
			
			
			for(RobotHandler r: rc.robots){
				robots.add(r);
			}
					
			AbstractSimulatorMonitor <RobotHandler> controller = new SimulatorMonitor(robots, e,bounds,walls);
			

        // Launch the monitoring
        launch(args);
		
		
	}

	 @Override
	    public void start(Stage primaryStage) throws Exception {
	        Application missionEditor = new MissionEditorView();
	        //missionEditor.start(primaryStage);
	    }

}
