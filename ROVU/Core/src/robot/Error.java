package robot;

/**
 * @author Madeleine
 */

public class Error {

    private int priority;
    private Double value;
    private Component component;

    public enum Component{
        HARDWARE,
        SOFTWARE
    }


    public Error(int priority, double value, Component component){
        this.priority = priority;
        this.value = value;
        this.component = component;
    }



}
