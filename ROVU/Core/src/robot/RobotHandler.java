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
    private Point startingPoint;
    private int robotIndex;
    private int fin;
    private	Point[] path;
    private boolean available;
    private PriorityQueue<MissionPoint> missionPoints = new PriorityQueue<>();
    private A_Star astar;
    
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

    public void setFin (int fin){
	this.fin=fin;
}

	public Point[] getPath() {
	return this.path;
}

	public void setPath(Point [] newpath){
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

	public Point getStartingPoint() { return this.startingPoint; }

	public int getRobotIndex() { return this.robotIndex; }

	public int getFin() { return this.fin; }

	public DataObject getData() {
		return null;
	}

	private void stop2Sec() {

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ie ) {
            ie.printStackTrace();
        }
    }
}
