package controller;

import java.util.ArrayList;
import java.util.List;

import model.Mission;
import project.Point;
import robot.RobotHandler;

public class RobotController implements MissionExecutable{
	
	public static RobotController controller;
	public List<RobotHandler> robots = new ArrayList<>();
	private List<Thread> robotThreads = new ArrayList<>();
	
	public RobotController(int numberOfRobots, Point[] startingPoints) {
		for(int i = 0; i <numberOfRobots; i++){
			robots.add(new RobotHandler(startingPoints[i], "Robot " + i+1, i));
		}
		for(RobotHandler r : robots){
			robotThreads.add(new Thread(r));
		}
		for(Thread t: robotThreads){
			t.start();
		}
	}

	public Mission getMission(){
	     return null;
	}
	
	public void executeMission(Mission mission){
		
	}
	
	public void cancelExecution(){
		
	}
	
	public List getData(){
		return null;
	}
	public static void main(String [] args){
		Point[] startingPoints = {new Point(-6,-2.5), new Point(6,-2.5), new Point(6,2.5), new Point(-6,2.5)};
		controller = new RobotController(4 , startingPoints);
	}
}
