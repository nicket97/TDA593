package robot;
import java.awt.Point;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Madeleine
 */


public class SensorProcessor implements ErrorHandler{

	private PriorityQueue<Error> errors = new PriorityQueue<Error>(11, new CompareError() );

	public Error getError(){
		return errors.peek();
	}
	
	public PriorityQueue<Error> getErrorData(){
		return errors;
	}
	
	public Point getPosition(){
		return null;
	}

	public void addError(Error e){
		errors.add(e);
	}


	private class CompareError implements Comparator<Error>{

		@Override
		public int compare(Error o1, Error o2) {
			if(o1.priority > o2.priority){
				return -1;
			}
			if (o1.priority < o2.priority){
				return 1;
			}
			return 0;
		}
	}
}

