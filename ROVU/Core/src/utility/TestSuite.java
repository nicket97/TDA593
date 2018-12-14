package utility;

import project.AbstractSimulatorMonitor;

import project.Point;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.RobotController;
import robot.RobotHandler;
import simbad.sim.EnvironmentDescription;

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
	
	AbstractSimulatorMonitor<RobotHandler> testController;
	
	  @Before
      public void setUp() {	  
		 testSubject = new RobotHandler(new Point(2.0,2.0), "XM1", 0);
		 testSet.add(testSubject);
		 testController = new SimulatorMonitor(testSet, testEnv);
		 Point[] startingPoints = {new Point(2.0,2.0)};
		 RobotController rc = new RobotController(1 ,startingPoints);
	  }

		@Test
		public void robotPositioningTest() {
		    Point initialPosition;
		    Point testPoint = new Point (2.0,2.0);
		    initialPosition = testSubject.startingPoint;
		    Assert.assertTrue(initialPosition.getX()==testPoint.getX());
		    Assert.assertTrue(initialPosition.getZ()==testPoint.getZ());
		}
		
		@Test
		public void robotMoveTest_1() {
			Point[][] testPath = {
					{new Point (4.0,4.0), new Point (-2.0,4.0)}};
			testSubject.setPath(testPath);
		    try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Assert.assertTrue(testSubject.isAtPosition(new Point (-2.0,4.0)));
		}
		
		@Test
		public void robotMoveTest_2() {
			Point[][] testPath = {
					{new Point (-2.0,-2.0)}};
			testSubject.setPath(testPath);
		    try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Assert.assertTrue(testSubject.isAtPosition(new Point (-2.0,-2.0)));
		}
	
	 
}
