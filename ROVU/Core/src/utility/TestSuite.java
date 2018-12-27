package utility;

import project.AbstractSimulatorMonitor;

import project.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import controller.RobotController;
import model.Environment;
import model.Node;
import robot.A_Star;
import robot.RobotHandler;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.VerticalWall;
import simbad.sim.Wall;

@SuppressWarnings("unused")


/**
 * Test suite for testing various elements of the system
 * @author Anthony
 *
 */
public class TestSuite {
	EnvironmentDescription testEnv = new EnvironmentDescription();
	
	RobotHandler testSubject;
	
	Set<RobotHandler> testSet = new HashSet<>();
	
	AbstractSimulatorMonitor <RobotHandler>testController;
	
	  @Before
      public void setUp() {	  
		 testSubject = new RobotHandler(new Point(2.0,2.0), "XM1", 0);
		 testSet.add(testSubject);
		 testController = new SimulatorMonitor(testSet, testEnv);
		 Point[] startingPoints = {new Point(2.0,2.0)};
		 RobotController rc = RobotController.getController();
		 rc.addRobots(1 ,startingPoints);
	  }

		@Test
		/**
		 * Test #1: Robot's initial positioning
		 */
		public void robotPositioningTest() {
		    Point initialPosition;
		    Point testPoint = new Point (2.0,2.0);
		    initialPosition = testSubject.getStartingPoint();
		    Assert.assertTrue(initialPosition.getX()==testPoint.getX());
		    Assert.assertTrue(initialPosition.getZ()==testPoint.getZ());
		}
		
		@Test
		/**
		 * Test #2: Robot's movement to two points
		 */
		public void robotMoveTest_1() {
			/*Point[] testPath = {
					new Point (4.0,4.0), new Point (6.0,5.0)};
			testSubject.setPath(testPath);
		    try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Assert.assertTrue(testSubject.isAtPosition(new Point (6.0,5.0)));*/
		}
		
		@Test
		/**
		 * Test #3: Robot's movement to one point
		 */
		public void robotMoveTest_2() {
			/*System.out.println("My position:"+testSubject.getPosition());
			Point[] testPath = 
					{new Point (-2.0,4.0)};
			testSubject.setPath(testPath);
		    try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Assert.assertTrue(testSubject.isAtPosition(new Point (-2.0,-2.0)));*/
		}
	
		@Test
		/**
		 * Test #4: Route planning without obstacles in path
		 */
		public void A_Star_Test_1(){
			A_Star atest= new A_Star();
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);
			
			testEnv.generateEmptyGrid(40, 0.5);
			atest.init(testEnv.pointNode(new Point (-2.3,4.5), 0.5), testEnv.pointNode(new Point (-1.8,9.5), 0.5));
			Node sample = atest.findRoute();
			Assert.assertTrue(sample.getPoint().getX()==-2 && sample.getPoint().getZ()==9.5);
		}
		
		@Test
		/**
		 * Test #5: Route planning with obstacles in path
		 */
		public void A_Star_Test_2(){
			A_Star atest= new A_Star();
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);			
			testEnv.generateEmptyGrid(40, 0.5);
			
			List <Boundary> testBounds = new ArrayList <Boundary>();
			Boundary test1 = new HorizontalBoundary(-6.0f, 2.0f, -2.0f, etest, Color.BLACK);
			testBounds.add(test1);
			
			List <Wall> testWalls = new ArrayList <Wall>();
			Wall test2 = new VerticalWall(-1.8f,-6.0f,-2.5f,etest,Color.ORANGE);
			testWalls.add(test2);
			
			testEnv.setWalls(testBounds, testWalls);
			atest.init(testEnv.pointNode(new Point (-7.2,1.0), 0.5), testEnv.pointNode(new Point (-5.0,0.2), 0.5));
			Node sample = atest.findRoute();
			Assert.assertTrue(sample.getPoint().getX()==-5 && sample.getPoint().getZ()==0.0);
		}
}
