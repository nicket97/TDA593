package controller;

import java.util.List;

import project.Point;

public class DataObject {
	private Point position;
	private List <Error> errors;
	
	public DataObject (Point position, List<Error> errors){
		this.position=position;
		this.errors=errors;
	}
	
	public Point extractPosition(){
		return this.position;
	}
	
	public List<Error> extractErrors(){
		return this.errors;
	}
}
