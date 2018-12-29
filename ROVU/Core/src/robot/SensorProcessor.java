package robot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Madeleine
 */

import static robot.Error.Component.HARDWARE;

public class SensorProcessor {

	private List<Error> errors = new ArrayList<>();

	public Error getError(){
		return new Error(0,0, HARDWARE);
	}
	
	public List getErrorData(){
		return errors;
	}
	
	public Point getPosition(){
		return null;
	}
}
