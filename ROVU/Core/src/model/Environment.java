package model;

import java.awt.Point;
import java.util.Map;

public class Environment implements IEnvironment {
    private Map <Point, String> map;
    
	public Environment (Map <Point, String> map){
		this.map=map;
	}
	
	public String getEnvironment (Point position){
		String environment = map.get(position);
		return environment;
	}
}
