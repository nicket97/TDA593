package model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import project.Point;

/**
 * Node class for use by algorithms
 * @author  Niclas
 */

public class Node {
	
	
    private int weight;
    private List <String> physicalArea;
    private String logicalArea;
    private boolean physical;
    private boolean wall;
    private boolean wifi;
    private boolean eating;
    private boolean room;
    private int nodeID;
    private int roomID;
    private Point point;
    private Node[] neighbours;
    private int distanceFromStart;
    
    private double distance;
    private double linear;
    private Node parent;
    private List <Node> predecessors;

    public int getWeight() {
        return weight;
    }

    public void setWeigth(int weight) {
        this.weight = weight;
    }

    public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isEating() {
        return eating;
    }

    public void setEating(boolean eating) {
        this.eating = eating;
    }

    public boolean isRoom() {
        return room;
    }

    public void setRoom(boolean room) {
        this.room = room;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int roomID) {
        this.nodeID = roomID;
    }

    public Point getPoint() {
        return point;
    }

    public void setDistStart(int startDist){   	
    	this.setDistanceFromStart(startDist);
    }
	
    public Node getParent(){
    	return this.parent;
    }
	
    public List<String> getPhysical(){
    	return this.physicalArea;
    }
    
    public Node() {
		//for pathfinder algorithm
    }
    
    public void setNeighbors(Node [] neighbors){
    	this.setNeighbours(neighbors);
    }
	
    public void setPoint(Point point) {
        this.point = point;
    }
    
    public Node(boolean wall, boolean wifi, boolean eat, int id, Point p){
    	setPredecessors(new ArrayList <Node>());
        this.wall = wall;
        this.wifi = wifi;
        this.eating = eat;
        this.nodeID = id;
        this.point = p;
        this.physicalArea= new ArrayList <String>();
        this.logicalArea="default";
        if (wall){
            weight = 999999;
        }
        else{
            weight = 1;
        }

    }

	public void setLogical(String value) {
		if (value.equalsIgnoreCase("wifi")){
			this.wifi=true;
			this.logicalArea=value;
		}else if (value.equalsIgnoreCase("eating")){
			this.eating=true;
			this.logicalArea=value;
		}
		
	}

	public void setPhysical(String value) {
			this.physical=true;
			this.physicalArea.add(value);		
	}
	
	@Override
    public String toString() {
        return point.toString() + " w:" + wall + " wifi:" + wifi + " eat:" + eating + " id:" + roomID + "\n";
    }

	public List <Node> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(List <Node> predecessors) {
		this.predecessors = predecessors;
	}

	public Node[] getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Node[] neighbours) {
		this.neighbours = neighbours;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public double getLinear() {
		return linear;
	}

	public void setLinear(double linear) {
		this.linear = linear;
	}

	public int getDistanceFromStart() {
		return distanceFromStart;
	}

	public void setDistanceFromStart(int distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}
}
