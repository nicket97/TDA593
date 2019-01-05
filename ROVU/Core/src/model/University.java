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

public class University extends Environment {
	private List<Boundary> bounds;
	private List<Wall> walls;

	public University(Double coefficient, EnvironmentDescription e) {
		super(coefficient,e);
		bounds = new ArrayList<Boundary>();
		walls = new ArrayList<Wall>();

		Color color = Color.GRAY;

		Boundary w1 = new HorizontalBoundary(-8.0f, -4.0f, -0.7f, e, color);
		Boundary w2 = new HorizontalBoundary(-8.0f, 0.7f, 4.0f, e, color);
		Boundary w3 = new HorizontalBoundary(8.0f, 4.0f, -4.0f, e, color);
		bounds.add(w1);
		bounds.add(w2);
		bounds.add(w3);
		Boundary w4 = new VerticalBoundary(4.0f, -8f, 8.0f, e, color);
		Boundary w5 = new VerticalBoundary(-4.0f, -8.0f, 8.0f, e, color);
		bounds.add(w4);
		bounds.add(w5);

		AbstractWall roomWallH1 = new HorizontalWall(-5f, -4f, -1f, e, color);
		AbstractWall roomWallH2 = new HorizontalWall(0f, -4f, -1f, e, color);
		AbstractWall roomWallH3 = new HorizontalWall(2.5f, -4f, -1f, e, color);
		AbstractWall roomWallH4 = new HorizontalWall(6.5f, -4f, -1f, e, color);
		walls.add(roomWallH1);
		walls.add(roomWallH2);
		walls.add(roomWallH3);
		walls.add(roomWallH4);

		AbstractWall roomWallV1 = new VerticalWall(-1f, -8f, -7f, e, color);
		AbstractWall roomWallV2 = new VerticalWall(-1f, -5.5f, -4.5f, e, color);
		AbstractWall roomWallV3 = new VerticalWall(-1f, -3f, 0f, e, color);
		AbstractWall roomWallV4 = new VerticalWall(-1f, 2.5f, 4.5f, e, color);
		AbstractWall roomWallV5 = new VerticalWall(-1f, 6f, 6.5f, e, color);
		walls.add(roomWallV1);
		walls.add(roomWallV2);
		walls.add(roomWallV3);
		walls.add(roomWallV4);
		walls.add(roomWallV5);

		AbstractWall roomWallH5 = new HorizontalWall(-1f, 1f, 2f, e, color);
		AbstractWall roomWallH6 = new HorizontalWall(-1f, 3.5f, 4f, e, color);
		AbstractWall roomWallH7 = new HorizontalWall(2f, 1f, 4f, e, color);
		walls.add(roomWallH5);
		walls.add(roomWallH6);
		walls.add(roomWallH7);

		AbstractWall roomWallV6 = new VerticalWall(1f, -8f, -6.0f, e, color);
		AbstractWall roomWallV7 = new VerticalWall(1f, -4.5f, 2f, e, color);
		walls.add(roomWallV6);
		walls.add(roomWallV7);

		List<Rectangle2D.Double> building = new ArrayList<Rectangle2D.Double>();
		building.add(new Rectangle2D.Double(-8, -4, 16, 8));
		super.defineInnerSpace(building);
		
		List<Rectangle2D.Double> corridor = new ArrayList<Rectangle2D.Double>();
		corridor.add(new Rectangle2D.Double(-8, -1, 16, 2));
		super.defineNonRoomSpace(corridor);

		super.addLogicalArea(new Rectangle2D.Double(-5.5, -0.8, 8, 1.2), "Wifi");
		super.addLogicalArea(new Rectangle2D.Double(2.5, 2, 3, 2), "Eating");

		super.addPhysicalArea(new Rectangle2D.Double(-8, -4, 3, 3), "Chemistry laboratory 01");
		super.addPhysicalArea(new Rectangle2D.Double(-5, -4, 5, 3), "Chemistry laboratory 02");
		super.addPhysicalArea(new Rectangle2D.Double(0, -4, 2.5, 3), "Meeting hall");
		super.addPhysicalArea(new Rectangle2D.Double(2.5, -4, 4, 3), "Office 01");
		super.addPhysicalArea(new Rectangle2D.Double(-8, 1, 7, 3), "Physics laboratory 01");
		super.addPhysicalArea(new Rectangle2D.Double(-1, 1, 3, 3), "Reactor chamber");

		super.addPhysicalArea(new Rectangle2D.Double(-8, -4, 8, 3), "Chemistry division");
		super.addPhysicalArea(new Rectangle2D.Double(0, -4, 6.5, 3), "Administration");
		super.addPhysicalArea(new Rectangle2D.Double(-8, 1, 10, 3), "Physics division");
	}
}
