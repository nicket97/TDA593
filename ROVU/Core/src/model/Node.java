package model;

import project.Point;

public class Node {
    int weigth;
    boolean wall;
    boolean wifi;
    boolean eating;
    boolean room;
    int roomID;
    Point point;

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
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

    public void setPoint(Point point) {
        this.point = point;
    }
    public Node(boolean wall, boolean wifi, boolean eat, int id, Point p){

        this.wall = wall;
        this.wifi = wifi;
        this.eating = eat;
        this.room = room;
        this.roomID = id;
        this.point = p;
        if (wall){
            weigth = 999999;
        }
        else{
            weigth = 1;
        }

    }
}
