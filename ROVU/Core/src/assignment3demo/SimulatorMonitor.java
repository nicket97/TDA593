package assignment3demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import project.AbstractRobotSimulator;
import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.EnvironmentDescription;

public class SimulatorMonitor extends AbstractSimulatorMonitor<Robot> {

	public SimulatorMonitor(Set<Robot> robots, EnvironmentDescription e) {
		super(robots, e);
	}

	

	@Override
	public void update(Robot arg0) {
		System.out.println(arg0.getName());
		System.out.println(arg0.getPosition());
		
		MoveRobot(Main.robot1, new Point[] {new Point (-2.5, -2.5),new Point (2.5, -2.5),new Point (6, -2.5)},new Point(-6,-2.5)); //robot movement
		MoveRobot(Main.robot2, new Point[] {new Point (2.5, -2.5),new Point (2.5, 2.5),new Point (6, 2.5)},new Point(6, -2.5));
		MoveRobot(Main.robot3, new Point[] {new Point (2.5, 2.5),new Point (-6, 2.5)},new Point(6, 2.5)); //,new Point (-2.5, 2.5)
		MoveRobot(Main.robot4, new Point[] {new Point (-2.5, 2.5),new Point (-2.5, -2.5),new Point (-6,-2.5)},new Point(-6, 2.5));
	}
	
	public void MoveRobot (Robot robot, Point[] commands, Point position){
	
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

