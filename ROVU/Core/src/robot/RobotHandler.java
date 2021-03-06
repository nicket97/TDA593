package robot;




import static robot.Error.Component.COLLISION_SENSOR;

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
 * @author madeleine
 */
public class RobotHandler extends AbstractRobotSimulator
        implements IRobotErrorGraphics, IRobotHandler {
    private Point startingPoint;
    private int robotIndex;
    private boolean available;
    private	Point[] path;
    private int pointer = 0;
    private List<List<Point>> joinedPaths = new ArrayList<>();
    private IPathFinder aStar = new A_Star();
    private int missionSize = 0;
    private PriorityQueue<MissionPoint> missionPoints = new PriorityQueue<>();
    private LinkedList<MissionPoint> processedPoints = new LinkedList<>();
    private Environment currentEnv;

    private SensorProcessor sensorProcessor = new SensorProcessor();
    private StringProperty errorProperty = new SimpleStringProperty(this.getName() + ": Everything's fine!");

    // Unused. Will be used for future purposes if the code is deployed to physical robots.
    private NetworkModule networkModule;

    private boolean noMission = true;
    private long stop = 0;
    private boolean timerActive = false;
    private boolean notFirstMission = false;

	private StringProperty currentPositionProperty = new SimpleStringProperty("");
	private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
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
            joinedPaths = new ArrayList<>();
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
                    concat.addAll(Arrays.asList(getCommands(currentEnv,initial,prev.getPoint())));
                    joinedPaths.add(concat);
                } else {
                    System.out.println("222");
                    List<Point> concat = new ArrayList<>();
                    thisP = missionPoints.poll();
                    processedPoints.add(thisP);
                    concat.addAll(Arrays.asList(getCommands(currentEnv,prev.getPoint(),thisP.getPoint())));
                    prev = thisP;
                    joinedPaths.add(concat);
                }
		    }

            if (joinedPaths.size()!=0){
                for (int h=0;h<joinedPaths.size();h++){
                    List <Point> j=joinedPaths.get(h);
                    for (Point u:j){
                        if (this.robotIndex==0)
                        System.out.println("Route#= "+h+" pointX="+u.getX()+" pointZ="+u.getZ());
                    }
                }
                path = new Point[joinedPaths.get(0).size()];

                joinedPaths.remove(0).toArray(path);
                for (Point p:path){
                    System.out.println("newpath===>"+p.getX()+"--"+p.getZ());
                }
		    }
        }
    }

	private Point [] getCommands (Environment environment, Point start, Point finish){
		List<Node> rpath = aStar.findPath(environment.getEnvironmentNode(start), environment.getEnvironmentNode(finish));
		Point [] commands = new Point [rpath.size()+1];
		for (int m=0;m<rpath.size();m++){
			commands[m]=environment.getNodeCenter(rpath.get(m));//test.getNodeCenter(path.get(m), 1);
		}
		commands[commands.length-1]=finish;
		return commands;
	}

    private void evaluateError(){
        PriorityQueue errors = sensorProcessor.getErrorData();
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

	public DataObject getData() {
		ArrayList <Error> errors = new ArrayList<Error>(sensorProcessor.getErrorData());
		return new DataObject(this.getPosition(),errors);
	}

    public void detectError(){
        if(checkObstacle()){
            sensorProcessor.addError(new Error(0,0, COLLISION_SENSOR));
            errorProperty.setValue(this.getName() + ": OBSTACLE. Can't move. Press Emergency Stop to cancel.");
	    this.stop();
        }
    }

    public void move() {
        if (currentEnv == null) return;
        // Update the robot's current position string property that GUI observes
        currentPositionProperty
                .setValue(
                        this.getName() + "\t \t" +
                        "x = " + DECIMAL_FORMAT.format(this.getPosition().getX()) + "\t \t" +
                        "z = " + DECIMAL_FORMAT.format(this.getPosition().getZ()));

        currentLocationProperty.setValue(
                this.getName() + " is in " + currentEnv.getEnvironmentNode(this.getPosition()).getPhysical()
        );

        if (noMission || path == null || pointer >= path.length) return;

        try {
            //System.out.println("Robot: " + this.robotIndex + " is at: " + this.getPosition() + " and is moving to: " + path[pointer]);
            if (pointer == path.length-1 && !processedPoints.isEmpty() && isEqual(path[pointer], processedPoints.peek().getPoint())) {
                processedPoints.poll().done(); // Mark mission point as done
            }
            
            if (pointer == path.length - 1 && !joinedPaths.isEmpty()) {
                System.out.println("Robot cycle: " + this.robotIndex);
                path = new Point[joinedPaths.get(0).size()];
                joinedPaths.remove(0).toArray(path);
                pointer = 0;
            }
     
            if (isAtPosition(path[pointer]) && pointer != path.length - 1 ){
            		//&& !RobotController.getController().isAnotherRobotInRoom(currentEnv.getEnvironment(path[pointer+1]).getPhysical(), this)) { 
            		// one robot per room constraint is disabled, as we are using only custom missions now
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

    private void setTimer(){
        stop = System.currentTimeMillis() + 2000;
        timerActive = true;
    }

    private boolean canMove(){
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

    @Override
    public StringProperty criticalErrorProperty() {
        return errorProperty;
    }
}
