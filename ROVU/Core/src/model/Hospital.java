package model;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import simbad.sim.AbstractWall;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;
import simbad.sim.Wall;

public class Hospital extends Environment {

	private List<Boundary> bounds;
	private List<Wall> walls;

	public Hospital(Double coefficient, EnvironmentDescription e) {
		
		super(coefficient);
		bounds = new ArrayList<Boundary>();
		walls = new ArrayList<Wall>();

		Color color = Color.BLUE;
		Boundary H1 = new HorizontalBoundary(-9.0f, 5.0f, -5.0f, e, color);
		Boundary H2 = new HorizontalBoundary(9.0f, 5.0f, -5.0f, e, color);
		Boundary V3 = new VerticalBoundary(5.0f, 9.0f, -9.0f, e, color);
		Boundary V4 = new VerticalBoundary(-5.0f, -9.0f, 9.0f, e, color);
		bounds.add(H1);
		bounds.add(H2);
		bounds.add(V3);
		bounds.add(V4);

		AbstractWall roomWallH1 = new HorizontalWall(4.5f, 0.0f, -5.0f, e, color);
		AbstractWall roomWallH2 = new HorizontalWall(0f, 0.0f, -5.0f, e, color);
		AbstractWall roomWallH3 = new HorizontalWall(-4.5f, 0.0f, -5.0f, e, color);
		walls.add(roomWallH1);
		walls.add(roomWallH2);
		walls.add(roomWallH3);

		AbstractWall roomWallV1 = new VerticalWall(0f, -9.0f, -8.0f, e, color);
		AbstractWall roomWallV2 = new VerticalWall(0f, -5.5f, -3.5f, e, color);
		AbstractWall roomWallV3 = new VerticalWall(0f, -1.0f, 1.0f, e, color);
		AbstractWall roomWallV4 = new VerticalWall(0f, 5.5f, 3.5f, e, color);
		AbstractWall roomWallV5 = new VerticalWall(0f, 8.0f, 9.0f, e, color);
		walls.add(roomWallV1);
		walls.add(roomWallV2);
		walls.add(roomWallV3);
		walls.add(roomWallV4);
		walls.add(roomWallV5);

		super.setWalls(bounds, walls);

		List<Rectangle2D.Double> building = new ArrayList<Rectangle2D.Double>();
		building.add(new Rectangle2D.Double(-9, -5, 18, 10));
		super.defineInnerSpace(building);
		super.addLogicalArea(new Rectangle2D.Double(7, -3, 3, 2), "Eating");
		super.addLogicalArea(new Rectangle2D.Double(-5, -5, 7, 8), "Wifi");
		super.addPhysicalArea(new Rectangle2D.Double(-9, -5, 18, 4.85), "Surgery division");
		super.addPhysicalArea(new Rectangle2D.Double(-9, -5, 4.5, 4.85), "Surgery room 1");
		super.addPhysicalArea(new Rectangle2D.Double(-9, -5, 4.5, 4.85), "Surgery room 2");
		super.addPhysicalArea(new Rectangle2D.Double(-9, -5, 4.5, 4.85), "Surgery room 3");
		super.addPhysicalArea(new Rectangle2D.Double(-9, -5, 4.5, 4.85), "Surgery room 3");

		super.addPhysicalArea(new Rectangle2D.Double(-9, 0, 18, 5), "Emergency division");
		super.addPhysicalArea(new Rectangle2D.Double(-9, 0, 18, 5), "Consulting room");
	}

}
