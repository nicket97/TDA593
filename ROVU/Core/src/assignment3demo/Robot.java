package assignment3demo;

import project.AbstractRobotSimulator;

import project.Point;

public class Robot extends AbstractRobotSimulator {

	
	
	public Robot(Point position, String name) {
		super(position, name);
		
	}

	@Override
	public String toString() {
		return "Robot " + this.getName();
	}

	
}



/*
public Robot (Vector3d position, String name) {     
super(position,name);
}
public void initBehavior() {}

public void performBehavior() { 
if (collisionDetected()) {   //obstacle detected        
    setTranslationalVelocity(0.0); //stop the robot
    setRotationalVelocity(0);
} else { // no obstacle detected
    setTranslationalVelocity(0.8); //move
    if ((getCounter() % 100)==0) 
       setRotationalVelocity(Math.PI/2 * (0.5 - Math.random()));
}
}
*/