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

public class Zoo extends Environment{
	
	private List<Boundary> bounds;
	private List<Wall> walls;
	
	public Zoo(Double coefficient, EnvironmentDescription e) {		
	
	super(coefficient,e);	

	bounds = new ArrayList<Boundary>();
	walls = new ArrayList<Wall>();
	
	
	Color color = Color.GRAY;
	Color steel = Color.LIGHT_GRAY;
	Color wood = Color.getHSBColor(22,0.5f,0.2f);	
	
	Boundary w1 = new HorizontalBoundary(-9.0f, -5.0f, 9.0f, e, color);	
	Boundary w2 = new HorizontalBoundary(7f, -5.0f, 5.0f, e, color);
	Boundary w3 = new HorizontalBoundary(1f, 5.0f, 9.0f, e, color);
	bounds.add(w1);
	bounds.add(w2);
	bounds.add(w3);

	Boundary w4 = new VerticalBoundary(-5.0f, -9f, 7.0f, e, color);
	Boundary w5 = new VerticalBoundary(9.0f, -9.0f, 1.0f, e, color);
	Boundary w6 = new VerticalBoundary(5.0f, 1.0f, 7.0f, e, color);
	bounds.add(w4);
	bounds.add(w5);
	bounds.add(w6);
	
	List<Rectangle2D.Double> building = new ArrayList<Rectangle2D.Double>();
	building.add(new Rectangle2D.Double(-9, -5, 16, 5));
	building.add(new Rectangle2D.Double(-9, 5, 10, 4));
	super.defineInnerSpace(building);
	
	
	//Wooden cages and ticket office
	AbstractWall roomWallH1 = new HorizontalWall(-8.8f, -4.8f, -1f, e, wood);
	AbstractWall roomWallH2 = new HorizontalWall(-8.8f, 3.5f, 9f, e, wood);
	AbstractWall roomWallH3 = new HorizontalWall(-1f, -4.8f, -1f, e, wood);
	AbstractWall roomWallH4 = new HorizontalWall(0.5f, -5f, 1f, e, wood);
	AbstractWall roomWallH5 = new HorizontalWall(1f, 3.6f, 5f, e, color);
	AbstractWall roomWallH6 = new HorizontalWall(0.8f, 3.5f, 9f, e, wood);
	AbstractWall roomWallH7 = new HorizontalWall(4.5f, -4.8f, -2f, e, wood);	
	walls.add(roomWallH1);
	walls.add(roomWallH2);
	walls.add(roomWallH3);
	walls.add(roomWallH4);
	walls.add(roomWallH5);
	walls.add(roomWallH6);
	walls.add(roomWallH7);
	
	AbstractWall roomWallV1 = new VerticalWall(-4.8f, -8.8f, -1f, e, wood);
	AbstractWall roomWallV2 = new VerticalWall(-4.8f, 0.5f, 4.5f, e, wood);		
	AbstractWall roomWallV3 = new VerticalWall(-1f,-8.8f, -8.4f, e, wood);
	AbstractWall roomWallV4 = new VerticalWall(-1f, -7f, -3.5f, e, wood);
	AbstractWall roomWallV5 = new VerticalWall(-1f, -2f, -1f, e, wood);				
	AbstractWall roomWallV6 = new VerticalWall(3.6f, 1f, 5.3f, e, color);		
	AbstractWall roomWallV7 = new VerticalWall(1.0f, 0.5f, 4.5f, e, wood);
	AbstractWall roomWallV8 = new VerticalWall(3.6f, -8.8f, -3f, e, wood);
	AbstractWall roomWallV9 = new VerticalWall(3.6f, -1f, 1f, e, wood);
	AbstractWall roomWallV10 = new VerticalWall(9.0f, -8.8f, 1f, e, wood);
	walls.add(roomWallV1);
	walls.add(roomWallV2);
	walls.add(roomWallV3);
	walls.add(roomWallV4);
	walls.add(roomWallV5);
	walls.add(roomWallV6);
	walls.add(roomWallV7);
	walls.add(roomWallV8);
	walls.add(roomWallV9);
	walls.add(roomWallV10);	
	
	//Steel cages
	AbstractWall roomWallH8 = new HorizontalWall(-8.4f, -4.6f, -1.2f, e, steel);
	AbstractWall roomWallH9 = new HorizontalWall(-4.8f, -4.6f, -1.2f, e, steel);
	AbstractWall roomWallH10 = new HorizontalWall(-1.4f, -4.6f, -1.2f, e, steel);		
	AbstractWall roomWallH11 = new HorizontalWall(4.3f, -4.6f, -2f, e, steel);
	AbstractWall roomWallH12 = new HorizontalWall(0.8f, -4.6f, 0.8f, e, steel);		
	AbstractWall roomWallH13 = new HorizontalWall(-8.6f, 5f, 8.8f, e, steel);
	AbstractWall roomWallH14 = new HorizontalWall(-5.0f, 5f, 8.8f, e, steel);
	AbstractWall roomWallH15 = new HorizontalWall(-2.0f, 5f, 8.8f, e, steel);
	AbstractWall roomWallH16 = new HorizontalWall(0.5f, 4.85f, 8.8f, e, steel);
	walls.add(roomWallH8);
	walls.add(roomWallH9);
	walls.add(roomWallH10);
	walls.add(roomWallH11);
	walls.add(roomWallH12);
	walls.add(roomWallH13);
	walls.add(roomWallH14);
	walls.add(roomWallH15);
	walls.add(roomWallH16);
	
	AbstractWall roomWallV11 = new VerticalWall(-4.5f, -8.4f, -1.4f, e, steel);
	AbstractWall roomWallV12 = new VerticalWall(-1.3f, -7f, -3.5f, e, steel);
	AbstractWall roomWallV13 = new VerticalWall(-1.3f, -2f, -1.25f, e, steel);
	AbstractWall roomWallV14 = new VerticalWall(-4.5f, 0.8f, 4.3f, e, steel);
	AbstractWall roomWallV15 = new VerticalWall(-0.6f, 0.8f, 4.3f, e, steel);
	AbstractWall roomWallV16 = new VerticalWall(0.8f, 0.8f, 4.3f, e, steel);
	AbstractWall roomWallV17 = new VerticalWall(8.7f, -8.6f, 0.8f, e, steel);
	AbstractWall roomWallV18 = new VerticalWall(5f, -8.6f, -7.0f, e, steel);
	AbstractWall roomWallV19 = new VerticalWall(5f, -5.4f, -3.8f, e, steel);
	AbstractWall roomWallV20 = new VerticalWall(5f, -2.4f, -1f, e, steel);
	walls.add(roomWallV11);	
	walls.add(roomWallV12);	
	walls.add(roomWallV13);	
	walls.add(roomWallV14);	
	walls.add(roomWallV15);	
	walls.add(roomWallV16);	
	walls.add(roomWallV17);	
	walls.add(roomWallV18);	
	walls.add(roomWallV19);
	walls.add(roomWallV20);	
	
	List<Rectangle2D.Double> walkways = new ArrayList<Rectangle2D.Double>();
	walkways.add(new Rectangle2D.Double(-9, -1, 9.5, 4.6));
	walkways.add(new Rectangle2D.Double(-1, -5, 1.5, 4));
	walkways.add(new Rectangle2D.Double(0.5, 1, 6.5, 4.6));
	walkways.add(new Rectangle2D.Double(4.5, -4.8, 2.5, 8.4));
	super.defineNonRoomSpace(walkways);
	
	super.addLogicalArea(new Rectangle2D.Double(-7.4, 0, 6.2, 3), "Wifi");
	
	super.addPhysicalArea(new Rectangle2D.Double(-8.4f, -4.6f, 3.6, 3.4), "Buffalo cage");
	super.addPhysicalArea(new Rectangle2D.Double(-4.8f, -4.6f, 3.4, 3.4), "Mountain goat cage");
	super.addPhysicalArea(new Rectangle2D.Double(-8.8f, -4.8f, 7.8, 3.8), "Bovids");
	
	super.addPhysicalArea(new Rectangle2D.Double(0.8f, -4.6f, 3.5, 4), "Flamingo cage");
	super.addPhysicalArea(new Rectangle2D.Double(0.8f, -0.6f, 3.5, 1.4), "Parrot cage");
	super.addPhysicalArea(new Rectangle2D.Double(0.5f, -5f, 4, 6), "Aviarium");
	
	super.addPhysicalArea(new Rectangle2D.Double(1f, 3.6f, 4.3, 1.4), "Ticket office");
	
	super.addPhysicalArea(new Rectangle2D.Double(-8.6f, 5.0f, 3.6, 3.8), "Lion cage");
	super.addPhysicalArea(new Rectangle2D.Double(-5.0f, 5.0f, 3, 3.8), "Bear cage");
	super.addPhysicalArea(new Rectangle2D.Double(-2.0f, 5.0f, 2.5, 3.8), "Wolf cage");
	super.addPhysicalArea(new Rectangle2D.Double(-8.8f, 3.5f, 9.6, 5.5), "Dangerous animals");
	}
}
