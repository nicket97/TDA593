package utility;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;
import project.AbstractSimulatorMonitor;
import project.Point;
import robot.RobotHandler;
import simbad.sim.EnvironmentDescription;

public class SimulatorMonitor extends AbstractSimulatorMonitor<RobotHandler> {
	private List<RobotHandler> robotList;
	private Set<RobotHandler> r;

	public SimulatorMonitor(Set<RobotHandler> robots, EnvironmentDescription e) {
		super(robots, e);
		r = robots;
		robotList = new ArrayList<>();
	}

	public void update(RobotHandler arg0) {
		robotList.addAll(r);
		robotList.get(0).setFin(1);

		for(int i=0; i < robotList.size(); i++){
			RobotHandler robot=robotList.get(i);
            Point [] dest = robot.getPath();
            if (dest == null || !(dest.length > 0)) {
                return;
            } else {
				if (!robot.isAtPosition(dest[dest.length - 1]) && (robot.getFin() == 1)) {
					MoveRobot(robot, robot.getPath(), robot.getStartingPoint());
				}

				if ((robot.getFin() != 3) && robot.isAtPosition(dest[dest.length - 1]) && !robot.isAvailable()) {
					robot.setAvailable(true);
					robot.setFin(3);

					if (robotList.get(i).getRobotIndex() != robotList.size() - 1) {
						System.out.println("Next=========================:" + robotList.get(robotList.indexOf(robot) + 1).getRobotIndex());
						robotList.get(robotList.indexOf(robot) + 1).setFin(1);
					}
				}
			}
		}
	}
	
	private void MoveRobot (RobotHandler robot, Point[] commands, Point position){
		if(robot.isAtPosition(position)){
			robot.setDestination(commands[0]);	
		}
		
		for (int i=1;i<commands.length;i++){
			Point start = commands[i-1];
			Point end = commands[i];
			
			if (robot.isAtPosition(start)){
	    		robot.setDestination(end);
			}	
		}
	}
}

