package model;

import java.awt.Point;

public interface IEnvironment {

	
	/**
	 * Returns type of environment for provided position 
	 * @param position
	 * @return
	 */
	Node getEnvironment (Point position);
	
}
