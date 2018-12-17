package model;

import java.util.List;

import project.Point;

public interface IEnvironment {

	
	/**
	 * Returns type of environment for provided position 
	 * @param position
	 * @return
	 */
	public Node getEnvironment (Point position);
	
	public List<Node> getMap();
	
}
