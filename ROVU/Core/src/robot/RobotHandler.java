package robot;



import controller.DataObject;
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
    private	Point[] path = new Point[1];
    private boolean available;
    private PriorityQueue<MissionPoint> missionPoints = new PriorityQueue<>();
    private Environment currentEnv;
    private int pointer = 0;
    
    public RobotHandler(Point position, String name, int i, Environment env) {
        super(position, name);
        startingPoint = position;
        robotIndex = i;
        currentEnv=env;
        }

    public void executeMission(){
        System.out.println(this.getPosition());
		List<Point> concat = new ArrayList<>();
        MissionPoint prev = null;
        MissionPoint thisP = null;
        pointer = 0;
        concat.add(this.startingPoint);
        for (int i = 0; i <= missionPoints.size(); i++){
		    if(i == 0){
                prev = missionPoints.poll();
                concat.addAll(Arrays.asList(task(currentEnv,this.getStartingPoint(),prev.getPoint())));
            }
            else{
                thisP = missionPoints.poll();
                concat.addAll(Arrays.asList(task(currentEnv,prev.getPoint(),thisP.getPoint())));
                prev = thisP;            }


		}
		path = new Point[concat.size()];
		concat.toArray(path);
		System.out.println(path);

    }

    
    @Override
    public void run() {
		executeMission();
		/*while (true) {


			System.out.println(missionPoints.size());
			if (missionPoints.size() == 0) {
				available = true;
                System.out.println(this.robotIndex + " is available");
			}
			else{
				available = false;
			}
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


		}*/
    }
	private static Point [] task (Environment environment, Point start, Point finish){
		A_Star test = new A_Star();
		test.init(environment.pointNode(start, 0.5), environment.pointNode(finish, 0.5)); //-6.8,-2.5
		List<Node> rpath = test.getRouteList(test.findRoute());
		Point [] commands = new Point [rpath.size()+1];
		for (int m=0;m<rpath.size();m++){
			commands[m]=environment.getNodeCenter(rpath.get(m),0.5);//test.getNodeCenter(path.get(m), 1);
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
    public void move(){
        System.out.println("Robot: " + this.robotIndex + " is at: " + this.getPosition() + " and is moving to: " + path[pointer]);
        if (isEqual(this.getPosition(), path[pointer]) ){
            pointer ++;

        }
        this.setDestination(path[pointer]);
    }
    private boolean isEqual(Point p1, Point p2){
        if ((p1.getX() < p2.getX()+0.1 && p1.getX() > p2.getX()-0.1) && (p1.getZ() < p2.getZ()+0.1 && p1.getZ() > p2.getZ()-0.1))
            return true;

        return false;
    }
}
