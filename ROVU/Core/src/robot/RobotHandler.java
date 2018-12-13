package robot;



import controller.DataObject;
import model.Mission;


import model.MissionPoint;
import project.AbstractRobotSimulator;
import project.Point;

import java.util.PriorityQueue;

/**
 * Class for controlling one robot
 * @author Anthony
 * @author Niclas
 */
public class RobotHandler extends AbstractRobotSimulator implements Runnable{
public Point startingPoint;
public int robotIndex;

private boolean available;
private PriorityQueue<MissionPoint> missionPoints = new PriorityQueue<>();

public RobotHandler(Point position, String name, int i) {
	super(position, name);		
	startingPoint = position;
	robotIndex = i;
	}
	
public void executeMission(Mission mission){
		
	}

@Override
public void run() {
	available = true;
}
/*
 * MoveRobot(Main.robot1, new Point[] {new Point (-2.5, -2.5),new Point (2.5, -2.5),new Point (6, -2.5)},new Point(-6,-2.5)); //robot movement
		MoveRobot(Main.robot2, new Point[] {new Point (2.5, -2.5),new Point (2.5, 2.5),new Point (6, 2.5)},new Point(6, -2.5));
		MoveRobot(Main.robot3, new Point[] {new Point (2.5, 2.5),new Point (-6, 2.5)},new Point(6, 2.5)); //,new Point (-2.5, 2.5)
		MoveRobot(Main.robot4, new Point[] {new Point (-2.5, 2.5),new Point (-2.5, -2.5),new Point (-6,-2.5)},new Point(-6, 2.5));
 */
Point[][] path = {
		{new Point (-6.8, 2.5),new Point (-6.7, -2.3)}
		,{new Point (-2.3, 2.5),new Point (-2.2, -2.3)}
,{new Point (2.3, 2.5),new Point (2.0, -2.3)},
{new Point (6.8, 2.5),new Point (6.3, -2.3)}};

public int fin;
public void setFin (int fin){
	this.fin=fin;
}

public Point[] getPath() {
	return path[robotIndex];
}

public void setPath(Point [][] newpath){
	this.path = newpath;
}

	public boolean isAvailable() {
	return available;
	}
	
	public void setAvailable(Boolean available) {
		this.available=available;;
	}

	public void stop() {

	}

	public void addMissionPoint(MissionPoint p) {
	}

	public DataObject getData() {
		return null;
	}
}
