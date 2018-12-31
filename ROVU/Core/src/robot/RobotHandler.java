package robot;



import controller.DataObject;
import controller.MissionEditor;
import controller.RobotController;
import model.*;


import project.AbstractRobotSimulator;
import project.Point;

import java.util.ArrayList;
import java.util.Arrays;
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
    private volatile int fin; // STATUS OF ROBOT moving around, 1 = Moving, 3 = Completed
    private	Point[] path;
    private boolean available;
    private PriorityQueue<MissionPoint> missionPoints = new PriorityQueue<>();
    private int pointer = 0;

    public RobotHandler(Point position, String name, int i) {
        super(position, name);
        startingPoint = position;
        robotIndex = i;
	}

    private void executeMission(Point currentPoint, Point nextPoint){
        setPath(getCommands(currentPoint, nextPoint));
    }
    
    @Override
    public void run() {
		while (true) {
            while (missionPoints.size() > 1 && this.isAtPosition(missionPoints.peek().getPoint())) {
                MissionPoint currentPoint = missionPoints.poll();
                Point nextPoint = missionPoints.peek().getPoint();
                executeMission(currentPoint.getPoint(), nextPoint);
                currentPoint.done();
            }

            if (missionPoints.size() == 1 && this.isAtPosition(missionPoints.peek().getPoint())) {
                setFin(3);
                missionPoints.poll().done();
				setAvailable(true);
                System.out.println(this.robotIndex + " is available");
			} else {
				setAvailable(false);
			}

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
    }

	private Point [] getCommands (Point start, Point finish){
		A_Star aStar = new A_Star();
		Environment environment = RobotController.getController().getEnvironment();
		aStar.init(environment.pointNode(start, 0.5), environment.pointNode(finish, 0.5));
		List<Node> robotPath = aStar.getRouteList(aStar.findRoute());
		Point [] commands = new Point [robotPath.size()+1];
		for (int m=0; m < robotPath.size(); m++){
			commands[m] = environment.getNodeCenter(robotPath.get(m), 0.5);
		}
		commands[commands.length-1]=finish;
		return commands;
	}

	public void move() {
        if (isEqual(this.getPosition(), getPath()[pointer])) {
            pointer++;
        }
        this.setDestination(getPath()[pointer]);
    }

    private boolean isEqual(Point p1, Point p2){
        return ((p1.getX() < p2.getX()+0.1 && p1.getX() > p2.getX()-0.1) && (p1.getZ() < p2.getZ()+0.1 && p1.getZ() > p2.getZ()-0.1));
    }

    public synchronized void setFin (int fin){
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
		missionPoints.offer(p);
	}

	public Point getStartingPoint() { return this.startingPoint; }

	public int getRobotIndex() { return this.robotIndex; }

	public synchronized int getFin() { return this.fin; }

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
