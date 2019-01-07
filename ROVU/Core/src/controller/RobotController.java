package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.beans.property.StringProperty;
import model.*;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.DataObject;
import robot.RobotHandler;
import simbad.sim.EnvironmentDescription;
import utility.SimulatorMonitor;
import view.ROVUView;

/**
 * Class for controlling all robots
 * @author Anthony
 * @author Niclas
 * @author Madeleine
 */
public class RobotController implements MissionExecutable{
    private static final String ARGUMENTS = "--customMission=TRUE";
	private static final RobotController controller = new RobotController();
	private List<RobotHandler> robots = new ArrayList<>();
	private Mission currentMission;
	private Environment currentEnvironment;
    private List<StringProperty> currentPositions = new ArrayList<>();
    private List<StringProperty> currentLocations = new ArrayList<>();

	private RobotController() {
	}
	public void init(){
		Point[] startingPoints = {new Point(-6,-2.5), new Point(-2.5,-2.5), new Point(2.5,-2.5), new Point(6,-2.5)};
		EnvironmentDescription e = new EnvironmentDescription();
		Hospital hospital = new Hospital(0.5,e);
		hospital.generateEmptyGrid(40, 0.5);
		setEnvironment(hospital);
		controller.addRobots(4 , startingPoints);
		initSimulator();
	}

	public static RobotController getController() {
		return controller;
	}

	// TODO: Better initialization of robots' starting points
    // Can be confusing since we mix Point and MissionPoint
	public void addRobots(int numberOfRobots, Point[] startingPoints) {
		for(int i = 0; i <numberOfRobots; i++){
			robots.add(new RobotHandler(startingPoints[i], "Robot " + i+1, i, currentEnvironment));
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
	
	public boolean isAnotherRobotInRoom (List<String> room , RobotHandler asking){
		for (RobotHandler r:robots){
			if (!r.equals(asking) && currentEnvironment.pointToNode(r.getPosition(),0.5).getPhysical().equals(room)
					&& currentEnvironment.pointToNode(r.getPosition(),0.5).isRoom()){
				return true;
			}
		}
		return false;		
	}


	public void executeMission(){
	    if (currentMission == null) throw new Error ("Mission is null");
		boolean notDone = true;

        System.out.println(currentMission.getMission().size());
        currentMission.getMission().forEach(missionPoint -> {
            int robotIndex = missionPoint.getRobot();
            switch (robotIndex) {
                case 1:
                    robots.get(0).addMissionPoint(missionPoint);
                    //currentMission.getMission().remove(missionPoint);
                    break;
                case 2:
                    robots.get(1).addMissionPoint(missionPoint);
                    //currentMission.getMission().remove(missionPoint);
                    break;
                case 3:
                    robots.get(2).addMissionPoint(missionPoint);
                    //currentMission.getMission().remove(missionPoint);
                    break;
                case 4:
                    robots.get(3).addMissionPoint(missionPoint);

                    break;
                default:
                    /*for (RobotHandler r : robots) {
                        if (r.isAvailable()) {
                            r.addMissionPoint(missionPoint);
                            currentMission.getMission().remove(missionPoint);
                        }
                    }*/
                    robots.get(0).addMissionPoint(missionPoint);
                    //currentMission.getMission().remove(missionPoint);
                    break;
            }
        });
        robots.forEach(RobotHandler::executeMission);
		//needs to be handled
		/*while (notDone){
			currentMission.updateMissionList();
			if(currentMission.getMission().size() > 0)
			for (RobotHandler r : robots){
				if (r.isAvailable()){
					if (currentMission.getMission().size() == 0){
						notDone = false;
					}
				}
			}
		}*/
	}
	
	public void cancelExecution(){
		robots.forEach(RobotHandler::stop);
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

	public List<Node> getNodes() {
	    if (currentEnvironment == null) {
	        throw new Error ("There's no environment set!");
        }
	    List<Node> nodes = new ArrayList<>();
	    robots.forEach(robot -> {
	        nodes.add(currentEnvironment.getEnvironmentNode(robot.getPosition()));
        });
	    return nodes;
    }
	public static void main(String[] args){
	    getController().init();
	    new Thread(new Timer()).start();
		new Thread(() -> {
			javafx.application.Application.launch(ROVUView.class, ARGUMENTS);
		}).start();
	}

	public Environment getEnviroment() {
		return currentEnvironment;
	}

    public List<StringProperty> getCurrentPositions() {
        if (robots == null || robots.size() == 0) return null;
        robots.forEach(robot -> currentPositions.add(robot.currentPositionPropertyProperty()));
        return currentPositions;
    }

    public List<StringProperty> getCurrentLocations() {
	    if (robots == null || robots.size() == 0) return null;
	    robots.forEach(robot -> currentLocations.add(robot.currentLocationPropertyProperty()));
	    return currentLocations;
    }
}
