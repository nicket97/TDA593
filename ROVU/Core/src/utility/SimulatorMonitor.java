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
		robotList.addAll(r);
		// Start with first robot moving
        robotList.get(0).setFin(1);
	}

	public void update(RobotHandler arg0) {
		for(int i=0; i < robotList.size(); i++){
			RobotHandler robot = robotList.get(i);
            Point [] dest = robot.getPath();
            if (dest == null || !(dest.length > 0)) {
                return;
            } else {
				if (!robot.isAtPosition(dest[dest.length - 1]) && robot.getFin() == 1) {
					robot.move();
				} else if (robot.getFin() == 3 && (robotList.indexOf(robot) + 1) < robotList.size()) {
                    if (robotList.get(robotList.indexOf(robot) + 1).getFin() == 3) {
                        continue;
                    } else {
                        robotList.get(robotList.indexOf(robot) + 1).setFin(1);
                    }
				}
			}
		}
	}
}

