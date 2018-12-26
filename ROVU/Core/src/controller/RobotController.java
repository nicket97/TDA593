package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.*;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.RobotHandler;
import utility.SimulatorMonitor;

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
	private Environment currentEnvironment;

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

	public void setEnvironment(Environment e) {
	    currentEnvironment = e;
    }

	public Mission getMission(){
	     return currentMission;
	}

	public void setMission(Mission mission) {
	    currentMission = mission;
    }

	public void executeMission(){
	    if (currentMission == null) throw new Error ("Mission is null");
		boolean notDone = true;
        currentMission.getMission().forEach(missionPoint -> {
            int robotIndex = missionPoint.getRobot();
            switch (robotIndex) {
                case 1:
                    robots.get(0).addMissionPoint(missionPoint);
                    break;
                case 2:
                    robots.get(1).addMissionPoint(missionPoint);
                    break;
                case 3:
                    robots.get(2).addMissionPoint(missionPoint);
                    break;
                case 4:
                    robots.get(3).addMissionPoint(missionPoint);
                    break;
                default:
                    //TODO add general mission
                    break;
            }
        });

        robotThreads.forEach(Thread::start);

		while (notDone){
			currentMission.updateMissionList();
			if(currentMission.getMission().size() > 0)
			for (RobotHandler r : robots){
				if (r.isAvailable()){
					if (currentMission.getMission().size() == 0){
						notDone = false;
					}
				}
			}
		}
	}
	
	public void cancelExecution(){
		for (RobotHandler r : robots)
			r.stop();
		
	}

	public void initSimulator() {
	    if (currentEnvironment == null || currentEnvironment.getEnvironmentDescription() == null) {
	        throw new Error("There's no environment description set!");
        }

        if (robots == null) {
	        throw new Error("No existing robots");
        }

        AbstractSimulatorMonitor simulator = new SimulatorMonitor(new HashSet<>(robots), currentEnvironment.getEnvironmentDescription());
    }
	
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

	public List<Node> getNodes() {
	    if (currentEnvironment == null) {
	        throw new Error ("There's no environment set!");
        }
	    List<Node> nodes = new ArrayList<>();
	    robots.forEach(robot -> {
	        nodes.add(currentEnvironment.getEnvironment(robot.getPosition()));
        });
	    return nodes;
    }
}
