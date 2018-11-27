package model;

import java.awt.Point;

public interface IEnvironment {

	
	/**
	 * Returns type of environment for provided position 
	 * @param position
	 * @return
	 */
	String getEnvironment (Point position);
	
}
