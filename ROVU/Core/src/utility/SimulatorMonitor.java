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
	private List<RobotHandler> robotList;

	
	Set<RobotHandler> r;
	public SimulatorMonitor(Set<RobotHandler> robots, EnvironmentDescription e) {
		super(robots, e);
		r = robots;

		 robotList = new ArrayList<RobotHandler>();
	}



	public void update(RobotHandler arg0) {
		
		for (RobotHandler j:r){
			robotList.add(j);
		}
		
		//List <RobotHandler> trs = new ArrayList <RobotHandler>();

		robotList.get(0).setFin(1);
		 
		for(int i=0;i<robotList.size();i++){
			RobotHandler robot=robotList.get(i);
            Point []dest = robot.getPath();
			if (!robot.isAtPosition(dest[dest.length-1]) && (robot.fin==1) ){
			MoveRobot(robot, robot.getPath(), robot.startingPoint);
			}
			//System.out.println("Pos: "+robot.isAtPosition(dest[0])+ " "+(robot.robotIndex)+" "+robot.fin);
			if ((robot.fin!=3) && robot.isAtPosition(dest[dest.length-1]) && !robot.isAvailable()){
				robot.setAvailable(true);
				robot.setFin(3);
				
				
				if (robotList.get(i).robotIndex!=robotList.size()-1){
				System.out.println ("Next=========================:"+robotList.get(robotList.indexOf(robot)+1).robotIndex);
				
				robotList.get(robotList.indexOf(robot)+1).setFin(1);
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

