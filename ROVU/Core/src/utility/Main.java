package utility;
import java.awt.Color;

import java.util.HashSet;
import java.util.Set;

import controller.RobotController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
import view.MissionEditorView;

@SuppressWarnings("unused")
public class Main extends Application{

	
	public static void main(String[] args)  throws InterruptedException {

		    EnvironmentDescription e = new EnvironmentDescription();
			
			Color color = Color.BLUE;

			Boundary H1 = new HorizontalBoundary(-9.0f, 5.0f, -5.0f, e, color);
			Boundary H2 = new HorizontalBoundary(9.0f, 5.0f, -5.0f, e, color);
			Boundary V3 = new VerticalBoundary(5.0f, 9.0f, -9.0f, e, color);
			Boundary V4 = new VerticalBoundary(-5.0f, -9.0f, 9.0f, e, color);
			
			AbstractWall roomWallH1 = new HorizontalWall(4.5f, 0.0f, -5.0f, e, color);
			AbstractWall roomWallH2 = new HorizontalWall(0f, 0.0f, -5.0f, e, color);
			AbstractWall roomWallH3 = new HorizontalWall(-4.5f, 0.0f, -5.0f, e, color);
			
			AbstractWall roomWallV1 = new VerticalWall(0f, -9.0f, -8.0f, e, color);
			AbstractWall roomWallV2 = new VerticalWall(0f, -5.5f, -3.5f, e, color);
			AbstractWall roomWallV3 = new VerticalWall(0f, -1.0f, 1.0f, e, color);
			AbstractWall roomWallV4 = new VerticalWall(0f, 5.5f, 3.5f, e, color);
			AbstractWall roomWallV5 = new VerticalWall(0f, 8.0f, 9.0f, e, color);
			
			Set<RobotHandler> robots = new HashSet<>();
			Point[] startingPoints = {new Point(-6.8,-2.5), new Point(-2.3,-2.5), new Point(2.3,-2.5), new Point(6.8,-2.5)};
			RobotController rc = new RobotController(4 ,startingPoints);

			for(RobotHandler r: rc.robots){
				robots.add(r);
			}
					
			AbstractSimulatorMonitor<RobotHandler> controller = new SimulatorMonitor(robots, e);

        // Launch the monitoring
        launch(args);
		
		
	}

	 @Override
	    public void start(Stage primaryStage) throws Exception {
	        Application missionEditor = new MissionEditorView();
	        missionEditor.start(primaryStage);
	    }

}
