package controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.RobotHandler;
import simbad.sim.EnvironmentDescription;
import utility.SimulatorMonitor;
import view.MissionEditorView;

/**
 * Class for controlling all robots
 * @author Anthony
 * @author Niclas
 * @author Madeleine
 */
public class RobotController extends Application implements MissionExecutable{
	//can not be final because it was called both by initialation and by JAVAFX
	private static RobotController controller;
	private List<RobotHandler> robots = new ArrayList<>();
	private List<Thread> robotThreads = new ArrayList<>();
	private Mission currentMission;
	private Environment currentEnvironment;
	private MissionEditorView missionView;

	public RobotController() {
		System.out.print("hej");
		controller = this;
	}
	public void init(){
		Point[] startingPoints = {new Point(-6,-2.5), new Point(6,-2.5), new Point(6,2.5), new Point(-6,2.5)};
		controller.addRobots(4 , startingPoints);
		EnvironmentDescription e = new EnvironmentDescription();
		Hospital hospital = new Hospital(0.5,e);
		setEnvironment(hospital);
		//TODO Should be done here but dont work with current implementation because the simulator tries to move the robots when they have no path assigned
		initSimulator();

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//RobotController.getController().init();
		Application missionEditor = new MissionEditorView();
		missionEditor.start(primaryStage);

	}

	public static RobotController getController() {
		return controller;
	}

	// TODO: Better initialization of robots' starting points
    // Can be confusing since we mix Point and MissionPoint
	public void addRobots(int numberOfRobots, Point[] startingPoints) {
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
        for (Thread t: robotThreads){
            //t.start();
        }

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
                            robots.get(1).addMissionPoint(missionPoint);
                            //currentMission.getMission().remove(missionPoint);
                            break;
                    }
                });


        for (RobotHandler r: robots){
            r.executeMission();
        }

		//TODO Should not be done here
		//initSimulator();

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

	// TODO: REMOVE THIS METHOD, should not expose robots outside of this class
	public List<RobotHandler> getRobots() {
	    return this.robots;
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
	public static void main(String[] args){
		launch();
	}

	public Environment getEnviroment() {
		return currentEnvironment;
	}
}
