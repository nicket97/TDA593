package model;

import java.awt.Point;
import java.util.Map;

public class Environment implements IEnvironment {
    private Node[][] map;
    
	public Environment (Node[][] map){
		this.map=map;
	}
	
	public Node getEnvironment (Point position){

		return map[(int)position.getX()][(int)position.getY()];
	}
}
