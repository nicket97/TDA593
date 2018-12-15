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
import simbad.sim.EnvironmentDescription;

public class SimulatorMonitor extends AbstractSimulatorMonitor<RobotHandler> {
	Point [] com ;
	Set<RobotHandler> r;
	public SimulatorMonitor(Set<RobotHandler> robots, EnvironmentDescription e) {
		super(robots, e);
		r = robots;
	}

	

	public void update(RobotHandler arg0) {
		
		/*
		List <RobotHandler> r1 = new ArrayList <RobotHandler>();
		int t=0;
		while (t<4){
		for (RobotHandler g:r){
			if (g.robotIndex==t){
				r1.add(g);
				t++;
			}
		}}
		r1.get(0).setFin(1);
		 
		for(int i=0;i<r1.size();i++){
			RobotHandler robot=r1.get(i);
            Point []dest = robot.getPath();
			if (!robot.isAtPosition(dest[1]) && (robot.fin==1) ){
			MoveRobot(robot, robot.getPath(), robot.startingPoint);
			}
			//System.out.println("Pos: "+robot.isAtPosition(dest[1])+ " "+(robot.robotIndex)+" "+robot.fin);
			if ((robot.fin!=3) && robot.isAtPosition(dest[1]) && !robot.isAvailable()){
				robot.setAvailable(true);
				robot.setFin(3);
				
				
				if (r1.get(i).robotIndex!=r1.size()-1){
				System.out.println ("Next=========================:"+r1.get(r1.indexOf(robot)+1).robotIndex);
				
				r1.get(r1.indexOf(robot)+1).setFin(1);
				}
				
			}
	
		}*/
		//MoveRobot(arg0, new Point[] {new Point (-2.5, -2.5),new Point (2.5, -2.5),new Point (6, -2.5)},new Point(-6,-2.5)); //robot movement
		//RobotHandler robot=
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
		test.setMap(new ArrayList<Node>());
		List <Node> list = test.generateEmptyGrid(20, 1);
		
	
		test.setStartDestination(list.get(2), list.get(6));
		List <Node> path = test.getRouteList(test.findRoute());
		//List <Node> path = new ArrayList <Node>();
		//Node vk= test.findRoute();
		System.out.println("pathX"+list.get(2).getPoint().getX()+"==pathZ=="+list.get(2).getPoint().getZ());
		System.out.println("===========pathX"+list.get(4).getPoint().getX()+"==pathZ=="+list.get(4).getPoint().getZ());
		//path.add(vk);
		com = new Point [path.size()];
		int h=0;
		System.out.println("Comsize="+com.length);
		for (int m=0;m<path.size();m++){
			System.out.println(">>>>>PointX======"+path.get(m).getPoint().getX()+" PointZ======"+path.get(m).getPoint().getZ());
			com[h]=path.get(m).getPoint();
			h++;
		}
		for (Point g:com){
			System.out.println("PointX======"+g.getX()+" PointZ======"+g.getZ());
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

