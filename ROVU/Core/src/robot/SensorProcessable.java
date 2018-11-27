package robot;

import java.awt.Point;
import java.util.List;

public interface SensorProcessable {

	/**
	 * Gets an error report with type
	 * @return
	 */
	public Error getError();
	
	
	/**
	 * Gets data for the error report
	 * @return
	 */
	public List getErrorData();
	
	
	/**
	 * Gets position of robot
	 * @return
	 */
	public Point getPosition();
}
