package robot;




import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.*;


import project.AbstractRobotSimulator;
import project.Point;

import java.text.DecimalFormat;
import java.util.*;

import controller.RobotController;

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
    private LinkedList<MissionPoint> processedPoints = new LinkedList<>();
    private Environment currentEnv;
    private int pointer = 0;
    private List<List<Point>> concatList = new ArrayList<List<Point>>();
    private boolean noMission=true;
    private long stop = 0;
    private boolean timerActive = false;
    private boolean notFirstMission = false;
	private int missionSize = 0;
	private StringProperty currentPositionProperty = new SimpleStringProperty("");
	private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private StringProperty currentLocationProperty = new SimpleStringProperty("");

    public RobotHandler(Point position, String name, int i, Environment env) {
        super(position, name);
        startingPoint = position;
        robotIndex = i;
        currentEnv=env;
    }

    public void executeMission(){
        System.out.println("misize "+missionPoints.size());
		
        if (!noMission) {
            concatList = new ArrayList<>();
            available = false;
            MissionPoint prev = null;
            MissionPoint thisP = null;
            pointer = 0;
            this.missionSize = missionPoints.size();

            for (int i = 0; i < missionSize; i++){
                if (i == 0){
                    System.out.println("111");
                    List<Point> concat = new ArrayList<>();
                    concat.add(this.startingPoint);
                    prev = missionPoints.poll();
                    processedPoints.add(prev);
                    Point initial;
                    if (!notFirstMission) {
                        initial = this.getStartingPoint();
                        notFirstMission=true;
                    } else {
                        initial=this.getPosition();
                        pointer=1;
                    }
                    concat.addAll(Arrays.asList(task(currentEnv,initial,prev.getPoint())));
                    concatList.add(concat);
                } else {
                    System.out.println("222");
                    List<Point> concat = new ArrayList<>();
                    thisP = missionPoints.poll();
                    processedPoints.add(thisP);
                    concat.addAll(Arrays.asList(task(currentEnv,prev.getPoint(),thisP.getPoint())));
                    prev = thisP;
                    concatList.add(concat);
                }
		    }

            if (concatList.size()!=0){
                for (int h=0;h<concatList.size();h++){
                    List <Point> j=concatList.get(h);
                    for (Point u:j){
                        if (this.robotIndex==0)
                        System.out.println("Route#= "+h+" pointX="+u.getX()+" pointZ="+u.getZ());
                    }
                }
                path = new Point[concatList.get(0).size()];

                concatList.remove(0).toArray(path);
                for (Point p:path){
                    System.out.println("newpath===>"+p.getX()+"--"+p.getZ());
                }
		    }
        }
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
		PathFinder aStar = new A_Star();		
		List<Node> rpath = aStar.findPath(environment.getEnvironmentNode(start), environment.getEnvironmentNode(finish));
		Point [] commands = new Point [rpath.size()+1];
		for (int m=0;m<rpath.size();m++){
			commands[m]=environment.getNodeCenter(rpath.get(m),0.5);//test.getNodeCenter(path.get(m), 1);
		}
		commands[commands.length-1]=finish;
		return commands;
	}

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

        noMission = true;
        pointer = 0;
        path = new Point[1];
        path[0] = this.getPosition();
        missionPoints.clear();
        //startingPoint = this.getPosition();


	}

	public void addMissionPoint(MissionPoint p) {
		missionPoints.offer(p);
		noMission=false;
	}

	public Point getStartingPoint() { return this.startingPoint; }

	public int getRobotIndex() { return this.robotIndex; }

	public int getFin() { return this.fin; }

	public DataObject getData() {
		return new DataObject(this.getPosition(),null);
	}

	private void stop2Sec() {
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ie ) {
            ie.printStackTrace();
        }
    }

    public void move() {
        // Update the robot's current position string property that GUI observes 
        currentPositionProperty
                .setValue(
                        this.getName() + "\t \t" +
                        "x = " + decimalFormat.format(this.getPosition().getX()) + "\t \t" +
                        "z = " + decimalFormat.format(this.getPosition().getZ()));

        currentLocationProperty.setValue(
                this.getName() + " is in " + currentEnv.getEnvironmentNode(this.getPosition()).getPhysical()
        );

        if (noMission || path == null || pointer >= path.length) return;
        try {
            //System.out.println("Robot: " + this.robotIndex + " is at: " + this.getPosition() + " and is moving to: " + path[pointer]);
            if (pointer == path.length-1 && !processedPoints.isEmpty() && isEqual(path[pointer], processedPoints.peek().getPoint())) {
                processedPoints.poll().done(); // Mark mission point as done
            }
            
            if (pointer == path.length - 1 && !concatList.isEmpty()) {
                System.out.println("Robot cycle: " + this.robotIndex);
                path = new Point[concatList.get(0).size()];
                concatList.remove(0).toArray(path);
                pointer = 0;
            }
     
            if (isAtPosition(path[pointer]) && pointer != path.length - 1 
            		&& !RobotController.getController().isAnotherRobotInRoom(currentEnv.getEnvironmentNode(path[pointer+1]).getPhysical(), this)) {
                pointer++;
            }
//            System.out.println("path pointer-->" + pointer);
            if (pointer != path.length - 1 &&
                !currentEnv.getEnvironmentNode(path[pointer + 1]).getPhysical().equals((currentEnv.getEnvironmentNode(path[pointer]).getPhysical())) &&
                !timerActive && currentEnv.getEnvironmentNode(path[pointer + 1]).isRoom()) {
                setTimer();
                this.setDestination(path[pointer]);
            }
            
            if (canMove()) {
                this.setDestination(path[pointer]);
            }
            
            if (missionPoints.size() == 0) {
                available = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private boolean isEqual(Point p1, Point p2){
        return (p1.getX() < p2.getX()+0.1 && p1.getX() > p2.getX()-0.1) && (p1.getZ() < p2.getZ()+0.1 && p1.getZ() > p2.getZ()-0.1);
    }

    public void setTimer(){
        stop = System.currentTimeMillis() + 2000;
        timerActive = true;
    }

    public boolean canMove(){
        if (System.currentTimeMillis() > stop){
            timerActive = false;
            return true;}
        else{
            return false;
        }
    }

    public StringProperty currentPositionPropertyProperty() {
        return currentPositionProperty;
    }

    public StringProperty currentLocationPropertyProperty() {
        return currentLocationProperty;
    }
}
