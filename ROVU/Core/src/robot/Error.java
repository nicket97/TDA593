package robot;

/**
 * @author Madeleine
 */

public class Error {

    protected int priority;
    private Double value;
    private Component component;

    public enum Component{
        HARDWARE,
        SOFTWARE,
        COLLISION_SENSOR,
    }


    Error (int priority, double value, Component component){
        this.priority = priority;
        this.value = value;
        this.component = component;
    }



}
