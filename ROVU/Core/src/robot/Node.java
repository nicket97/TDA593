package robot;

import java.util.ArrayList;
import java.util.List;

import project.Point;

/**
 * Node class for use by algorithms
 * @author  Niclas
 */

public class Node {
	
    int weight;
    boolean wall;
    boolean wifi;
    boolean eating;
    boolean room;
    int roomID;
    Point point;
    Node[] neighbours;
    int distanceFromStart;
    
    double distance;
    double linear;
    Node parent;
    List <Node> predecessors;

    public int getWeight() {
        return weight;
    }

    public void setWeigth(int weight) {
        this.weight = weight;
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

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Point getPoint() {
        return point;
    }

    public void setDistStart(int startDist){   	
    	this.distanceFromStart=startDist;
    }
	
    public Node getParent(){
    	return this.parent;
    }
	
    public Node() {
		//for pathfinder algorithm
    }
	
    public void setPoint(Point point) {
        this.point = point;
    }
    public Node(boolean wall, boolean wifi, boolean eat, int id, Point p){
    	predecessors = new ArrayList <Node>();
        this.wall = wall;
        this.wifi = wifi;
        this.eating = eat;
        this.room = room;
        this.roomID = id;
        this.point = p;
        if (wall){
            weight = 999999;
        }
        else{
            weight = 1;
        }

    }
}
