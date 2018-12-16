package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import model.Mission;
import model.MissionPoint;
import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.A_Star;
import robot.Node;
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
		test.setWalls(bounds, walls);
		test.setMap(new ArrayList<Node>());
		List <Node> list = test.generateEmptyGrid(20, 1);//20 is standard size -10 <> +10, 20/coefficient = number of nodes in line
	
		test.setStartDestination(list.get(2), list.get(6));
		List <Node> path = test.getRouteList(test.findRoute());
		//List <Node> path = new ArrayList <Node>();
		//Node vk= test.findRoute();
		System.out.println("pathX"+list.get(0).getPoint().getX()+"==pathZ=="+list.get(0).getPoint().getZ());
		//System.out.println("===========pathX"+list.get(4).getPoint().getX()+"==pathZ=="+list.get(4).getPoint().getZ());
		//path.add(vk);
		com = new Point [path.size()];
		int h=0;
		System.out.println("Comsize="+com.length);
		for (int m=0;m<path.size();m++){
			System.out.println(">>>>>PointX======"+path.get(m).getPoint().getX()+" PointZ======"+path.get(m).getPoint().getZ());
			com[h]=test.getNodeCenter(path.get(m), 1);
			h++;
		}
		for (Point g:com){
			System.out.println("PointX======"+g.getX()+" PointZ======"+g.getZ());
		}
		List <Node> test2=test.walling(list, 1);
		
		for (Node c:test2){
			System.out.println("Wall??? "+c.isWall());
		}

		}
		
		MoveRobot(arr[0], com, arr[0].startingPoint);
	}
	
	public void MoveRobot (RobotHandler robot, Point[] commands, Point position){
	
		if(robot.isAtPosition(position)){
			robot.setDestination(commands[0]);	
		}
		
		for (int i=1;i<commands.length;i++){
			Point start = commands[i-1];
			Point end = commands[i];
			
			if(robot.isAtPosition(start)){
			robot.setDestination(end);
			}	
		}
		
	}

}

