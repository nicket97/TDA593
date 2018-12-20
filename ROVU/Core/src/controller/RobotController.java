package controller;

import java.util.ArrayList;
import java.util.List;

import model.Mission;
import model.MissionPoint;
import project.Point;
import robot.RobotHandler;

/**
 * Class for controlling all robots
 * @author Anthony
 * @author Niclas
 * @author Madeleine
 */
public class RobotController implements MissionExecutable{
	
	private static final RobotController controller = new RobotController();
	private List<RobotHandler> robots = new ArrayList<>();
	private List<Thread> robotThreads = new ArrayList<>();
	private Mission currentMission;

	private RobotController(){	}

	public static RobotController getController(){
		return controller;
	}

	public void addRobots(int numberOfRobots, Point[] startingPoints){
		for(int i = 0; i <numberOfRobots; i++){
			robots.add(new RobotHandler(startingPoints[i], "Robot " + i+1, i));
		}
		for(RobotHandler r : robots){
			robotThreads.add(new Thread(r));
		}

	}

	@Override
	public Mission getMission(){
	     return currentMission;
	}
	
	@Override
	public void executeMission(Mission mission){
		currentMission = mission;
		for(MissionPoint p: mission.getMissionPoints()){
			int robotIndex = p.getRobot();
			if(robotIndex <= robots.size()) {
				robots.get(robotIndex).addMissionPoint(p);
			}
			for (Thread t: robotThreads){
				t.start();
			}

		}
		mission_loop:while (true){
			currentMission.updateMissionList();
			if(currentMission.getMission().size() > 0)
			for (RobotHandler r : robots){
				if(r.isAvailable()){
					if (currentMission.getMission().size() == 0){
						break mission_loop;
					}
				}

			}

		}


	}
	
	@Override
	public void cancelExecution(){
		for (RobotHandler r : robots)
			r.stop();
		
	}

	@Override
	public List<DataObject> getData(){
		List<DataObject> d = new ArrayList<>();
		for (RobotHandler r: robots){
			d.add(r.getData());
		}
		return d;
	}

	public List<RobotHandler> getRobots() {
	    return this.robots;
    }

	public static void main(String [] args){
		Point[] startingPoints = {new Point(-6,-2.5), new Point(6,-2.5), new Point(6,2.5), new Point(-6,2.5)};
		controller.addRobots(4 , startingPoints);
	}
}
