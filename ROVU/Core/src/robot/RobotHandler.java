package robot;



import controller.DataObject;
import model.Mission;


import model.MissionPoint;
import project.AbstractRobotSimulator;
import project.Point;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Class for controlling one robot
 * @author Anthony
 * @author Niclas
 */
	public class RobotHandler extends AbstractRobotSimulator implements Runnable{
	private Point startingPoint;
	private int robotIndex;
	private boolean available;
	private PriorityQueue<MissionPoint> missionPoints = new PriorityQueue<>();
	private List path;
	private PathFinder pathFinder;


	public RobotHandler(Point position, String name, int i) {
	super(position, name);		
	startingPoint = position;
	robotIndex = i;
	pathFinder = new A_Star();
	}
	
public void executeMission(Mission mission){
		this.path = pathFinder.getPath(mission);
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
/*Point[][] path = {
		{new Point (-2.5, -2.5),new Point (2.5, -2.5),new Point (6, -2.5)}
		,{new Point (2.5, -2.5),new Point (2.5, 2.5),new Point (6, 2.5)}
,{new Point (2.5, 2.5),new Point (-6, 2.5)},
{new Point (-2.5, 2.5),new Point (-2.5, -2.5),new Point (-6, -2.5)}};*/
public List<Node> getPath() {
	return path;
}

public void setPath(List<Node> newpath){
	this.path = newpath;
}

	public boolean isAvailable() {
	return available;
	}

	public void stop() {

	}

	public void addMissionPoint(MissionPoint p) {
		missionPoints.add(p);
	}

	public DataObject getData() {
	DataObject data = new DataObject();
	//TODO fix data object creation
		return data;
	}
}
