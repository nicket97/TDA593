package utility;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import model.Environment;
import model.Hospital;
import model.Mission;
import model.MissionPoint;
import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.A_Star;
import model.Node;
import robot.RobotHandler;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

public class SimulatorMonitor extends AbstractSimulatorMonitor<RobotHandler> {
	Point [] com ;
	public  List <Boundary> bounds;
	public  List <Wall> walls;

	
	Set<RobotHandler> r;
	public SimulatorMonitor(Set<RobotHandler> robots, EnvironmentDescription e,List <Boundary> bounds, List <Wall> walls) {
		super(robots, e);
		r = robots;
		this.walls=walls;
		this.bounds=bounds;
	}



	public void update(RobotHandler arg0) {
		

		RobotHandler [] arr = new RobotHandler [r.size()];
		boolean check=true;
		if (check){
			check=false;
		int i=0;
		
		for (RobotHandler h:r){
			arr[i]=h;
			i++;
		}
		
		A_Star test = new A_Star();
		Environment env = new Environment(new ArrayList<Node>());
		env.setWalls(bounds, walls);
		env.setCoefficient(1);

		List <Node> list = env.generateEmptyGrid(20, 1);//20 is standard size -10 <> +10, 20/coefficient = number of nodes in line


		test.setStartDestination(env.getMap().get(2), env.getMap().get(8));
		List <Node> path = test.getRouteList(test.findRoute());

		System.out.println("Start pathX"+list.get(2).getPoint().getX()+"==pathZ=="+list.get(2).getPoint().getZ());
		System.out.println("Destination pathX"+list.get(8).getPoint().getX()+"==pathZ=="+list.get(8).getPoint().getZ());

		com = new Point [path.size()];
		int h=0;

		for (int m=0;m<path.size();m++){
			System.out.println(">>>>>PointX======"+path.get(m).getPoint().getX()+" PointZ======"+path.get(m).getPoint().getZ()+" ??Wall="+path.get(m).isWall());
			com[h]=path.get(m).getPoint();//test.getNodeCenter(path.get(m), 1);
			h++;
		}
		for (Point g:com){
			System.out.println("Path array PointX======"+g.getX()+" PointZ======"+g.getZ());
		}
		
		for (Node c:list){
			System.out.println("Logical??? "+c.isLogical()+"  PointXlog=="+c.getPoint().getX()+" PointZlog=="+c.getPoint().getZ());
			System.out.println("Physical??? "+c.isPhysical()+"  PointXp=="+c.getPoint().getX()+" PointZp=="+c.getPoint().getZ());
		}

		}



		//MoveRobot(arr[0], tewr, arr[0].startingPoint);

	}
	
	public void MoveRobot (RobotHandler robot, Point[] commands, Point position){
	
		if(robot.isAtPosition(position)){
			robot.setDestination(commands[0]);	
		}
		
		for (int i=1;i<commands.length;i++){
			Point start = commands[i-1];
			Point end = commands[i];
			
			if(robot.isAtPosition(start)){
			System.out.println("Ending=="+end.getX()+" "+end.getZ());
			robot.setDestination(end);
			}	
		}
		
	}

}

