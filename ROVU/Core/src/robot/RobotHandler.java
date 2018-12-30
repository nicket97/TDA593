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
    private int fin;
    private	Point[] path;
    private boolean available;
    private PriorityQueue<MissionPoint> missionPoints = new PriorityQueue<>();

    public RobotHandler(Point position, String name, int i) {
        super(position, name);
        startingPoint = position;
        robotIndex = i;
	}

    public void executeMission(){
    	List<Point> commands = new ArrayList<>();
		while (missionPoints.size() > 1) {
			Point currentPoint = missionPoints.poll().getPoint();
			Point nextPoint = missionPoints.peek().getPoint();
			commands.remove(currentPoint);
			commands.addAll(Arrays.asList(getCommands(currentPoint, nextPoint)));
		}
		missionPoints.clear();
		Point [] path = new Point[commands.size()];
		setPath(commands.toArray(path));
    }
    
    @Override
    public void run() {
    	executeMission();
//		while (true) {
////			System.out.println(missionPoints.size());
//			if (missionPoints.size() == 0) {
//				available = true;
//                System.out.println(this.robotIndex + " is available");
//			}
//			else {
//				available = false;
//			}
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//		}
    }

	private Point [] getCommands (Point start, Point finish){
        System.out.println(this.getName() + ": " + start + "\t   " + finish);
		A_Star aStar = new A_Star();
		Environment environment = RobotController.getController().getEnvironment();
		aStar.init(environment.pointNode(start, 0.5), environment.pointNode(finish, 0.5)); //-6.8,-2.5
		List<Node> robotPath = aStar.getRouteList(aStar.findRoute());
		Point [] commands = new Point [robotPath.size()+1];
		for (int m=0; m < robotPath.size(); m++){
			commands[m] = environment.getNodeCenter(robotPath.get(m), 0.5);//test.getNodeCenter(path.get(m), 1);
		}
		commands[commands.length-1]=finish;
		return commands;
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
		missionPoints.offer(p);
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
