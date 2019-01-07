package utility;

import project.AbstractSimulatorMonitor;

import project.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import controller.RobotController;
import model.Constraint;
import model.Environment;
import model.Hospital;
import model.Mission;
import model.MissionPoint;
import model.Node;
import robot.A_Star;
import robot.RobotHandler;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
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
	

		@Test
		/**
		 * Test #1: Robot's initial positioning
		 */
		public void robotPositioningTest() {
			testSubject = new RobotHandler(new Point(2.0,2.0), "XM1", 0, new Environment(0.5,new EnvironmentDescription()));
			testSet.add(testSubject);
			testController = new SimulatorMonitor(testSet, testEnv);
			Point[] startingPoints = {new Point(2.0,2.0)};
			RobotController rc = RobotController.getController();
			rc.addRobots(1 ,startingPoints);
		    Point initialPosition;
		    Point testPoint = new Point (2.0,2.0);
		    initialPosition = testSubject.getStartingPoint();
		    Assert.assertTrue(initialPosition.getX()==testPoint.getX());
		    Assert.assertTrue(initialPosition.getZ()==testPoint.getZ());
		}
		
		@Test
		/**
		 * Test #2: Robot's movement to one point
		 */
		public void robotMoveTest_1() {
			Point[] startingPoints = {new Point(6,-2.0),new Point(-2.7,-2.5)};
			RobotController rc = RobotController.getController();
			List <MissionPoint> mpoints = new ArrayList<MissionPoint>();
			mpoints.add(new MissionPoint(5.5, 1, Constraint.ROBOT2));
			Mission testMis = new Mission(mpoints);
			Hospital testH = new Hospital(0.5, new EnvironmentDescription());
			testH.generateEmptyGrid(40, 0.5);
			rc.setEnvironment(testH);
			rc.setMission(testMis);
			rc.addRobots(2 ,startingPoints);
			rc.executeMission();
			rc.initSimulator();
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Assert.assertTrue(rc.getData().get(1).extractPosition().getX()>5.4 && rc.getData().get(1).extractPosition().getZ()>0.91
					&& rc.getData().get(1).extractPosition().getX()<5.6 && rc.getData().get(1).extractPosition().getZ()<1.1);
			
		}
		
		@Test
		/**
		 * Test #3: Robot's movement to two points
		 */
		public void robotMoveTest_2() {
			
			RobotController rc = RobotController.getController();
			List <MissionPoint> mpoints = new ArrayList<MissionPoint>();
			mpoints.add(new MissionPoint(-1.7, -3, Constraint.ROBOT3));
			mpoints.add(new MissionPoint(-2.7, -4, Constraint.ROBOT3));
			Mission testMis = new Mission(mpoints);
			Hospital testH = new Hospital(0.5, new EnvironmentDescription());
			testH.generateEmptyGrid(40, 0.5);
			rc.setEnvironment(testH);
			rc.setMission(testMis);
			rc.executeMission();
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Assert.assertTrue(rc.getData().get(2).extractPosition().getX()>-2.8 && rc.getData().get(2).extractPosition().getZ()>-4.1
					&& rc.getData().get(2).extractPosition().getX()<-2.6 && rc.getData().get(2).extractPosition().getZ()<-3.9);
			
		}
		
		@Test 
		/**
		 * Test #4: Environment grid generation without any objects
		 */
		public void gridGenerationTest(){
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);
			testEnv.generateEmptyGrid(40, 0.5);
			Assert.assertTrue(testEnv.getMap().size()==1600);
		}
		
		@Test
		/**
		 * Test #5: Environment grid generation with walls and boundaries
		 */
		public void wallBoundaryTest(){
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);
			
			List <Boundary> testBounds = new ArrayList <Boundary>();
			Boundary test1 = new HorizontalBoundary(-5.0f, -3.0f, 3.0f, etest, Color.BLUE);
			Boundary test2 = new VerticalBoundary(-8.0f, -5.7f, 0.1f, etest, Color.GREEN);
			testBounds.add(test1);
			testBounds.add(test2);
			
			List <Wall> testWalls = new ArrayList <Wall>();
			Wall test3 = new HorizontalWall(-3.5f,-7.0f,-6.1f,etest,Color.GRAY);
			Wall test4 = new VerticalWall(8.3f,-9.2f,-4.0f,etest,Color.MAGENTA);
			testWalls.add(test3);
			testWalls.add(test4);
				
			testEnv.setWalls(testBounds, testWalls);
			testEnv.generateEmptyGrid(40, 0.5);
			
			//Check if nodes occupied by physical walls have Wall type
			for (double i=-3;i<3;i+=0.5){
				Assert.assertTrue(testEnv.getEnvironmentNode(new Point (-5.0,i)).isWall());
			}
			for (double i=-6;i<0.5;i+=0.5){
				Assert.assertTrue(testEnv.getEnvironmentNode(new Point (i,-8.0)).isWall());
			}
			for (double i=-7;i<-6;i+=0.5){
				Assert.assertTrue(testEnv.getEnvironmentNode(new Point (-3.5,i)).isWall());
			}
			for (double i=-9.5;i<-4;i+=0.5){
				Assert.assertTrue(testEnv.getEnvironmentNode(new Point (i,8.0)).isWall());
			}
		}
		
		@Test
		/**
		 * Test #6: Environment grid generation with physical and logical areas
		 */
		public void physicalLogicalAreasTest(){
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);
			
			testEnv.addLogicalArea(new Rectangle2D.Double(-2, -1, 3, 2), "Wifi");
			testEnv.addPhysicalArea(new Rectangle2D.Double(-9, -3, 4, 3), "Office");
			testEnv.generateEmptyGrid(40, 0.5);
			
			for (double i=-9;i<-5;i+=0.5){
				for (double z=-3;z<0;z+=0.5){
					Assert.assertTrue(testEnv.getEnvironmentNode(new Point (i,z)).isPhysical() && testEnv.getEnvironmentNode(new Point (i,z)).getPhysical().get(0).equalsIgnoreCase("Office"));
				}
			}
			
			for (double i=-2;i<1;i+=0.5){
				for (double z=-1;z<1;z+=0.5){
					Assert.assertTrue(testEnv.getEnvironmentNode(new Point (i,z)).isWifi() && testEnv.getEnvironmentNode(new Point (i,z)).getLogical().get(0).equalsIgnoreCase("Wifi"));
				}
			}
		}
		
		@Test
		/**
		 * Test #7: Status of node's neighbors
		 */
		public void nodeNeighborTest(){
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);
			testEnv.generateEmptyGrid(40, 0.5);
			
			Node testNode = testEnv.getMap().get(47);
			double tX=testNode.getPoint().getX();
			double tZ=testNode.getPoint().getZ();
			int test=0;
			
			//Checking if node has four neighbors around it
			for (Node neigh:testNode.getNeighbors()){
				if (neigh.getPoint().getX()==(tX+0.5) && neigh.getPoint().getZ()==tZ){
					test++;
				}
				if (neigh.getPoint().getX()==(tX-0.5) && neigh.getPoint().getZ()==tZ){
					test++;
				}
				if (neigh.getPoint().getX()==tX && neigh.getPoint().getZ()==(tZ+0.5)){
					test++;
				}
				if (neigh.getPoint().getX()==tX && neigh.getPoint().getZ()==(tZ-0.5)){
					test++;
				}
			}
			Assert.assertTrue(test==4);
		}
		
		@Test
		/**
		 * Test #8: Route planning without obstacles in path
		 */
		public void A_Star_Test_1(){
			A_Star atest= new A_Star();
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);
			
			testEnv.generateEmptyGrid(40, 0.5);
			atest.findPath(testEnv.getEnvironmentNode(new Point (-2.3,4.5)), testEnv.getEnvironmentNode(new Point (-1.8,9.5)));
			Node sample = atest.findRoute();
			Assert.assertTrue(sample.getPoint().getX()==-2 && sample.getPoint().getZ()==9.5);
		}
		
		@Test
		/**
		 * Test #9: Route planning with obstacles in path
		 */
		public void A_Star_Test_2(){
			A_Star atest= new A_Star();
			EnvironmentDescription etest = new EnvironmentDescription();
			Environment testEnv =  new Environment(0.5, etest);			
			
			List <Boundary> testBounds = new ArrayList <Boundary>();
			Boundary test1 = new HorizontalBoundary(-6.0f, 2.0f, -2.0f, etest, Color.BLACK);
			testBounds.add(test1);
			
			List <Wall> testWalls = new ArrayList <Wall>();
			Wall test2 = new VerticalWall(-1.8f,-6.0f,-2.5f,etest,Color.ORANGE);
			testWalls.add(test2);
				
			testEnv.setWalls(testBounds, testWalls);
			testEnv.generateEmptyGrid(40, 0.5);
			atest.findPath(testEnv.getEnvironmentNode(new Point (-7.2,1.0)), testEnv.getEnvironmentNode(new Point (-5.0,0.2)));
			Node sample = atest.findRoute();
			Assert.assertTrue(sample.getPoint().getX()==-5 && sample.getPoint().getZ()==0.0);
		}
		
		
}
