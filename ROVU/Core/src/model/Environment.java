package model;

import java.awt.Point;

public class Environment implements IEnvironment {
    private Node[][] map;
    int xSize;
    int ySize;
    
	
	/**
	Hospital map for assignment 5 with 4 surgery rooms and consulting room
	
		Boundary H1 = new HorizontalBoundary(-9.0f, 5.0f, -5.0f, e, color);
		Boundary H2 = new HorizontalBoundary(9.0f, 5.0f, -5.0f, e, color);
		Boundary V3 = new VerticalBoundary(5.0f, 9.0f, -9.0f, e, color);
		Boundary V4 = new VerticalBoundary(-5.0f, -9.0f, 9.0f, e, color);
		
		AbstractWall roomWallH1 = new HorizontalWall(4.5f, 0.0f, -5.0f, e, color);
		AbstractWall roomWallH2 = new HorizontalWall(0f, 0.0f, -5.0f, e, color);
		AbstractWall roomWallH3 = new HorizontalWall(-4.5f, 0.0f, -5.0f, e, color);
		
		AbstractWall roomWallV1 = new VerticalWall(0f, -9.0f, -8.0f, e, color);
		AbstractWall roomWallV2 = new VerticalWall(0f, -5.5f, -3.5f, e, color);
		AbstractWall roomWallV3 = new VerticalWall(0f, -1.0f, 1.0f, e, color);
		AbstractWall roomWallV4 = new VerticalWall(0f, 5.5f, 3.5f, e, color);
		AbstractWall roomWallV5 = new VerticalWall(0f, 8.0f, 9.0f, e, color);
		*/
	
	public Environment (Node[][] map){

		xSize = map.length;
		ySize = map[0].length;
		this.map=map;

	}
	
	@Override
	public Node getEnvironment (Point position){

		return map[(int)position.getX()][(int)position.getY()];
	}
}
