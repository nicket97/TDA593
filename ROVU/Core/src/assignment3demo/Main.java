package assignment3demo;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import project.AbstractSimulatorMonitor;
import project.Point;
import simbad.sim.AbstractWall;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;

@SuppressWarnings("unused")
public class Main {
	
	static Robot robot1;
	static Robot robot2;
	static Robot robot3;
	static Robot robot4;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {


		EnvironmentDescription e = new EnvironmentDescription();
		
		Color color = Color.GRAY;

		Boundary H1 = new HorizontalBoundary(-5.0f, 3.0f, 5.0f, e, color);
		Boundary H1_2 = new HorizontalBoundary(-5.0f, -2.0f, 2.0f, e, color);
		Boundary H1_3 = new HorizontalBoundary(-5.0f, -3.0f, -5.0f, e, color);
				
		Boundary H2_1 = new HorizontalBoundary(5.0f, 3.0f, 5.0f, e, color);
		Boundary H2_2 = new HorizontalBoundary(5.0f, -2.0f, 2.0f, e, color);
		Boundary H2_3 = new HorizontalBoundary(5.0f, -3.0f, -5.0f, e, color);
		
		Boundary V3 = new VerticalBoundary(5.0f, 5.0f, -5.0f, e, color);
		Boundary V4 = new VerticalBoundary(-5.0f, -5.0f, 5.0f, e, color);

		AbstractWall roomWall1 = new HorizontalWall(0f, 3.0f, 5.0f, e, color);
		AbstractWall roomWall1_2 = new HorizontalWall(0f, -2.0f, 2.0f, e, color);
		AbstractWall roomWall1_3 = new HorizontalWall(0f, -3.0f, -5.0f, e, color);
				
		AbstractWall roomWall2 = new VerticalWall(0f, 3.0f, 5.0f, e, color);
		AbstractWall roomWall2_2 = new VerticalWall(0f, -2.0f, 2.0f, e, color);
		AbstractWall roomWall2_3 = new VerticalWall(0f, -3.0f, -5.0f, e, color);

		Set<Robot> robots = new HashSet<>();
		
		 robot1 = new Robot(new Point(-6,-2.5), "XM1");
		 robot2 = new Robot(new Point(6,-2.5), "XM2");
		 robot3 = new Robot(new Point(6, 2.5), "XM3");
		 robot4 = new Robot(new Point(-6, 2.5), "XM4");

		robots.add(robot1);
		robots.add(robot2);
		robots.add(robot3);
		robots.add(robot4);
				
		AbstractSimulatorMonitor controller = new SimulatorMonitor(robots, e);

		
	}

}
