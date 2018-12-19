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
	private List<RobotHandler> trs;

	
	Set<RobotHandler> r;
	public SimulatorMonitor(Set<RobotHandler> robots, EnvironmentDescription e,List <Boundary> bounds, List <Wall> walls) {
		super(robots, e);
		r = robots;
		this.walls=walls;
		this.bounds=bounds;
		 trs = new ArrayList<RobotHandler>();
	}



	public void update(RobotHandler arg0) {
		
		for (RobotHandler j:r){
			trs.add(j);
		}
		
		//List <RobotHandler> trs = new ArrayList <RobotHandler>();

		trs.get(0).setFin(1);
		 
		for(int i=0;i<trs.size();i++){
			RobotHandler robot=trs.get(i);
            Point []dest = robot.getPath();
			if (!robot.isAtPosition(dest[dest.length-1]) && (robot.fin==1) ){
			MoveRobot(robot, robot.getPath(), robot.startingPoint);
			}
			//System.out.println("Pos: "+robot.isAtPosition(dest[0])+ " "+(robot.robotIndex)+" "+robot.fin);
			if ((robot.fin!=3) && robot.isAtPosition(dest[dest.length-1]) && !robot.isAvailable()){
				robot.setAvailable(true);
				robot.setFin(3);
				
				
				if (trs.get(i).robotIndex!=trs.size()-1){
				System.out.println ("Next=========================:"+trs.get(trs.indexOf(robot)+1).robotIndex);
				
				trs.get(trs.indexOf(robot)+1).setFin(1);
				}
				
			}
	
		}


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

