package robot;



import model.Mission;


import project.AbstractRobotSimulator;
import project.Point;

/**
 * Class for controlling one robot
 * @author Anthony
 * @author Niclas
 */
public class RobotHandler extends AbstractRobotSimulator implements Runnable{
public Point startingPoint;
public int robotIndex;
public RobotHandler(Point position, String name, int i) {
	super(position, name);		
	startingPoint = position;
	robotIndex = i;
	}
	
public void executeMission(Mission mission){
		
	}

@Override
public void run() {
	
}
/*
 * MoveRobot(Main.robot1, new Point[] {new Point (-2.5, -2.5),new Point (2.5, -2.5),new Point (6, -2.5)},new Point(-6,-2.5)); //robot movement
		MoveRobot(Main.robot2, new Point[] {new Point (2.5, -2.5),new Point (2.5, 2.5),new Point (6, 2.5)},new Point(6, -2.5));
		MoveRobot(Main.robot3, new Point[] {new Point (2.5, 2.5),new Point (-6, 2.5)},new Point(6, 2.5)); //,new Point (-2.5, 2.5)
		MoveRobot(Main.robot4, new Point[] {new Point (-2.5, 2.5),new Point (-2.5, -2.5),new Point (-6,-2.5)},new Point(-6, 2.5));
 */
Point[][] path = {
		{new Point (-2.5, -2.5),new Point (2.5, -2.5),new Point (6, -2.5)}
		,{new Point (2.5, -2.5),new Point (2.5, 2.5),new Point (6, 2.5)}
,{new Point (2.5, 2.5),new Point (-6, 2.5)},
{new Point (-2.5, 2.5),new Point (-2.5, -2.5),new Point (-6, -2.5)}};
public Point[] getPath() {
	return path[robotIndex];
}

public void setPath(Point [][] newpath){
	this.path = newpath;
}

}
