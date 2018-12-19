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
import robot.A_Star;
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
	
	public static void main(String[] args)  throws InterruptedException {

		    EnvironmentDescription e = new EnvironmentDescription();
			
		    
			Color color = Color.BLUE;
			
			Hospital host = new Hospital(0.5,e); 
			host.generateEmptyGrid(40, 0.5);
			Set<RobotHandler> robots = new HashSet<>();
			
			
			
			


			
			
			
			Point[] startingPoints = {new Point(-6,-2.5), new Point(-1.5,-2.5), new Point(1.5,-2.5), new Point(6,-2.5)};
			Point[] middlePoints = {new Point(-6.8,2.5), new Point(-2.3,2.5), new Point(2.3,2.5), new Point(6.8,2.5)};
			Point[] endPoints = {new Point(-7.5,-4), new Point(-3,-4), new Point(3,-4), new Point(7.5,-4)};
			RobotController rc = new RobotController(4 ,startingPoints);
			
			int u=0;
			
			for(RobotHandler r: rc.robots){
				for (int h=0;h<startingPoints.length;h++){
					if (startingPoints[h].getX()==r.startingPoint.getX() && startingPoints[h].getZ()==r.startingPoint.getZ()){
						u=h;
					}
				}
				Point [] com=task(host,startingPoints[u], middlePoints[u]);
				Point [] com2 = task(host,middlePoints[u],endPoints[u]);
				List <Point> concat = new ArrayList<Point>();
				for (Point p:com){
					concat.add(p);
				}
				for (int y=1;y<com2.length;y++){
					concat.add(com2[y]);
				}
				
				Point [] way = new Point [concat.size()];
				for (int v=0;v<concat.size();v++){
					way[v]=concat.get(v);
				}
				
				for (Point f:way){
					System.out.println("For robot "+r.getName()+" X:"+f.getX()+ " Z:"+f.getZ());
				}
				r.setPath(way);
				robots.add(r);
			}
					
			AbstractSimulatorMonitor <RobotHandler> controller = new SimulatorMonitor(robots, e);
			

        // Launch the monitoring
        launch(args);
		
		
	}

	 @Override
	    public void start(Stage primaryStage) throws Exception {
	        Application missionEditor = new MissionEditorView();
	        //missionEditor.start(primaryStage);
	    }
	 
	 	private static Point [] task (Hospital host, Point start, Point finish){
	 		A_Star test = new A_Star();
	 		test.setStartDestination(host.pointNode(start, 0.5), host.pointNode(finish, 0.5)); //-6.8,-2.5
	 		List <Node> rpath = test.getRouteList(test.findRoute());
	 		Point [] com = new Point [rpath.size()];
	 		for (int m=0;m<rpath.size();m++){
				com[m]=host.getNodeCenter(rpath.get(m),0.5);//test.getNodeCenter(path.get(m), 1);
			}
	 		return com;
	 	}

}
