package model;

import java.awt.Point;
import java.util.Map;

public class Environment implements IEnvironment {
    private Node[][] map;
    int xSize;
    int ySize;
    
	public Environment (Node[][] map){

		xSize = map.length;
		ySize = map[0].length;
		this.map=map;

	}
	
	public Node getEnvironment (Point position){

		return map[(int)position.getX()][(int)position.getY()];
	}
}
