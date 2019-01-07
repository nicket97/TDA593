package model;

import project.Point;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import simbad.sim.Boundary;
import simbad.sim.EnvironmentDescription;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;
import simbad.sim.Wall;

public class Environment implements IEnvironment {
    int xSize;
    int ySize;
    
    private List <Pair<Rectangle2D.Double,String>> logicalAreas;
    private List <Pair<Rectangle2D.Double,String>> physicalAreas;
    private List <Rectangle2D.Double> innerSpace;
    private List <Rectangle2D.Double> noRoom;
    private  List <Boundary> bounds;
	private  List <Wall> walls;
	private double coefficient;
	private EnvironmentDescription environmentDescription;
	private  List <Node> map;

	public Environment (Double coefficient, EnvironmentDescription environmentDescription){
		this.coefficient = coefficient;
		this.environmentDescription = environmentDescription;
		this.physicalAreas = new ArrayList <Pair<Rectangle2D.Double,String>>();
		this.logicalAreas = new ArrayList <Pair<Rectangle2D.Double,String>>();
		this.innerSpace = new ArrayList <Rectangle2D.Double>();
		this.noRoom = new ArrayList<Rectangle2D.Double>();
		this.bounds = new ArrayList <Boundary>();
		this.walls = new ArrayList <Wall>();
		this.map=new ArrayList <Node>();
	}

    /**
	 * Method to add walls to grid
	 * @param bounds
	 * @param walls
	 * @return
	 */
	
	public void setWalls(List <Boundary> bounds, List <Wall> walls){
		this.bounds=bounds;
		this.walls=walls;
	}
	

	
	/**
	 * Method to define space belonging to building(s) on the grid
	 * @param inner
	 */
	public void defineInnerSpace(List <Rectangle2D.Double> inner){
		this.innerSpace=inner;
	}
	
	/**
	 * Method to define non-room building parts (corridors/hallways etc.)
	 * @param otherAreas
	 */
	public void defineNonRoomSpace(List <Rectangle2D.Double> otherAreas){
		this.noRoom=otherAreas;
	}
	
	/**
	 * Method to add physical area to the grid
	 * @param space
	 * @param name
	 */
	public void addPhysicalArea(Rectangle2D.Double space, String name){
		Pair<Rectangle2D.Double,String> temp = new Pair<Rectangle2D.Double,String>(space,name);
		this.physicalAreas.add(temp);
	}
	
	/**
	 * Method to add logical area to the grid
	 * @param space
	 * @param name
	 */
	public void addLogicalArea(Rectangle2D.Double space, String name){
		Pair<Rectangle2D.Double,String> temp = new Pair<Rectangle2D.Double,String>(space,name);
		this.logicalAreas.add(temp);
	}
	
	/**
	 * Method to place added physical and logical areas onto the grid
	 */
	public void addAreasToMap(){
		for (Pair<Rectangle2D.Double,String> log:logicalAreas){
			for (Node n:map){
				if (nodeToRect(n,coefficient).intersects(log.getKey())){
					n.setLogical(log.getValue());
				}
			}
		}
		for (Pair<Rectangle2D.Double,String> phys:physicalAreas){
			for (Node s:map){
				if (nodeToRect(s,coefficient).intersects(phys.getKey())){
					s.setPhysical(phys.getValue());
				}
			}
		}
	}
	
	/**
	 * Method to find and add node's neighbors
	 * @param check
	 * @param coefficient
	 * @param diagonal
	 * @param map
	 * @return
	 */
	public Node [] neighbouring (Node check, double coefficient, boolean diagonal, List <Node> map){
		//-10,-1,+1,+10
		//-11,-10,-9,-1,+1,+9,+10,+11
        List <Node> temp = new ArrayList<Node> ();
		if (!diagonal){ //adding 4 neighbors
			for (Node w:map)	{
				double cX=check.getPoint().getX();
				double cZ=check.getPoint().getZ();
				if (w.getPoint().getX()==cX-coefficient && w.getPoint().getZ()==cZ){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX+coefficient && w.getPoint().getZ()==cZ){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX && w.getPoint().getZ()==cZ-coefficient){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX && w.getPoint().getZ()==cZ+coefficient){
					temp.add(w);
				}
			}

		} else { //adding 8 neighbors	
			for (Node w:map)	{
				double cX=check.getPoint().getX();
				double cZ=check.getPoint().getZ();
				if (w.getPoint().getX()==cX-coefficient && w.getPoint().getZ()==cZ){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX-coefficient && w.getPoint().getZ()==cZ-coefficient){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX+coefficient && w.getPoint().getZ()==cZ){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX+coefficient && w.getPoint().getZ()==cZ+coefficient){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX && w.getPoint().getZ()==cZ-coefficient){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX-coefficient && w.getPoint().getZ()==cZ+coefficient){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX && w.getPoint().getZ()==cZ+coefficient){
					temp.add(w);
				}
				if (w.getPoint().getX()==cX+coefficient && w.getPoint().getZ()==cZ-coefficient){
					temp.add(w);
				}
			}
		}
		
		Node [] ret = new Node [temp.size()];
		for (int n=0;n<temp.size();n++){
			ret[n]=temp.get(n);
		}
		return ret;	
	}
	
	/**
	 * Method to get a Node containing requested point
	 * @param p
	 * @param coefficient
	 * @return
	 */	
	public  Node pointToNode(Point p, double coefficient) {
		for (Node v:map){
			Rectangle2D.Double temp = nodeToRect(v, coefficient);//new Rectangle2D.Double(v.getPoint().getX(),v.getPoint().getX(),coefficient,coefficient);
			if (temp.contains(p.getX(), p.getZ())){
				return v;
			}
		}
		System.out.println("Out of bounds");
		return null;
	}
	
	/**
	 * Method to obtain the space occupied by node (in form of rectangle)
	 * @param node
	 * @param coefficient
	 * @return
	 */
	public Rectangle2D.Double nodeToRect(Node node, double coefficient){
		Rectangle2D.Double temp = new Rectangle2D.Double(node.getPoint().getX(),node.getPoint().getZ(),coefficient,coefficient);
		return temp;
	}
	
	/**
	 * Method to get rectangle representing vertical wall and add it to grid
	 * @param wall
	 * @return
	 */

	public Rectangle2D.Double verticalWallToRect(VerticalWall wall){ //Float p1z, Float p1x, Float p2x
		float side=0;
		if (wall.getP2x()<wall.getP1x()){
			side=wall.getP2x();
		}else{
			side=wall.getP1x();
		}
			Rectangle2D.Double temp = new Rectangle2D.Double(side,wall.getP1z()-0.15,Math.abs(wall.getP1x()-wall.getP2x()),0.3);
			return temp;

	}
	
	/**
	 * Method to get rectangle representing horizontal wall and add it to grid
	 * @param wall
	 * @return
	 */
	public Rectangle2D.Double horizontalWallToRect(HorizontalWall wall){ //Float p1z, Float p1x, Float p2x
//-4.5f, 0.0f, -5.0f
		float side=0;
		if (wall.getP2z()<wall.getP1z()){
			side=wall.getP2z();
		}else{
			side=wall.getP1z();
		}
		Rectangle2D.Double temp = new Rectangle2D.Double(wall.getP1x()-0.15,side,0.3,Math.abs(wall.getP2z()-(wall.getP1z())));
		return temp;

	}
	
	/**
	 * Method to get rectangle representing vertical boundary and add it to grid
	 * @param wall
	 * @return
	 */
	public Rectangle2D.Double verticalBoundaryToRect(VerticalBoundary wall){ //Float p1z, Float p1x, Float p2x
		float side=0;
		if (wall.getP2x()<wall.getP1x()){
			side=wall.getP2x();
		}else{
			side=wall.getP1x();
		}
		  //  System.out.println("InsideRect:"+Math.abs(wall.getP1x())+"  "+Math.abs(wall.getP2x()));
			Rectangle2D.Double temp = new Rectangle2D.Double(side,wall.getP1z()-0.15,Math.abs(wall.getP1x()-wall.getP2x()),0.3);
			return temp;
	
	}
	
	/**
	 * Method to get rectangle representing horizontal boundary and add it to grid
	 * @param wall
	 * @return
	 */
	public Rectangle2D.Double horizontalBoundaryToRect(HorizontalBoundary wall){ //Float p1z, Float p1x, Float p2x
		float side=0;
		if (wall.getP2z()<wall.getP1z()){
			side=wall.getP2z();
		}else{
			side=wall.getP1z();
		}
		Rectangle2D.Double temp = new Rectangle2D.Double(wall.getP1x()-0.15,side,0.3,Math.abs(wall.getP1z()-wall.getP2z()));
		return temp;

	}
	
	/**
	 * Method to get center of the node
	 * @param node
	 * @param coefficient
	 * @return
	 */
	public Point getNodeCenter(Node node, double coefficient){
		Point temp = new Point (node.getPoint().getX()+coefficient/2,node.getPoint().getZ()+coefficient/2);
		return temp;		
	}
	
	/**
	 * Method for generating grid
	 * @param scale
	 * @param coefficient
	 * @return
	 */
	public  List<Node> generateEmptyGrid(int scale, double coefficient){
		List <Node> exmap = new ArrayList<Node> ();
		int z=0;
	    
		for (int i=-scale/2;i<0/2;i++){ //upper part
			
			for (int k=-scale/2;k<0;k++){ //left
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				map.add(temp);
				z++;
			}
			for (int k=0;k<scale/2;k++){ //right
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				map.add(temp);
				z++;
			}
		}
		
		for (int i=0;i<scale/2;i++){ //lower part
			for (int k=-scale/2;k<0;k++){ //left
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				map.add(temp);
				z++;
			}
			for (int k=0;k<scale/2;k++){ //right
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				map.add(temp);
				z++;
			}
		}
		for (Node v:map){
			
			Node [] neigh = neighbouring (v, coefficient, false, map);
			v.setNeighbors(neigh);
			
			}
		
		addAreasToMap();
		
		for (Rectangle2D.Double inn:innerSpace){
			for (Node x:map){
				if (nodeToRect(x,coefficient).intersects(inn)){
					x.setRoom(true);
				}
			}
		}
		
		if (!noRoom.isEmpty()){
		for (Rectangle2D.Double nRoom:noRoom){
			for (Node x:map){
				if (nodeToRect(x,coefficient).intersects(nRoom)){
					x.setRoom(false);
				}
			}
		}}
		walling(map,coefficient);
		return map;
	}
	
	
	
	//horizontal->(X,Zbegin,Zend)
	//vertical->(Z,Xbegin,Xend)
	
	/**
	 * Method to place walls onto the grid
	 * @param toWall
	 * @param bounds
	 * @param walls
	 * @return
	 */
	public List<Node> walling (List<Node> toWall, double coefficient){
		toWall=this.map;//test
		List <Rectangle2D.Double> obstacles = new ArrayList <Rectangle2D.Double>();
		Rectangle2D.Double temp;
		for (Boundary b:bounds){
		    if (b.getClass()==VerticalBoundary.class){		    	
		    	temp= verticalBoundaryToRect((VerticalBoundary)b);
		    	obstacles.add(temp);
		    }else{
		    	temp= horizontalBoundaryToRect((HorizontalBoundary)b);
		    	obstacles.add(temp);
		    }
		}
		
		for (Wall w:walls){
		    if (w.getClass()==VerticalWall.class){		    	
		    	temp= verticalWallToRect((VerticalWall)w);
		    	obstacles.add(temp);
		    }else{
		    	temp= horizontalWallToRect((HorizontalWall)w);
		    	//System.out.println("RectangleX, Y, W, H: "+temp.getX()+" "+temp.getY()+" "+temp.getWidth()+" "+temp.getHeight()+" ");
		    	obstacles.add(temp);
		    }
		}
		
		for (Rectangle2D.Double n:obstacles){
			for (Node v:toWall){
			if (nodeToRect(v,coefficient).intersects(n)){
				v.setWall(true);
			}
			}
		}
		
		return toWall;		
	}
	
	public List<Node> getMap(){
		return this.map;
	}
	
	public Node getEnvironment (Point position){
		Node temp = pointToNode(position, coefficient);
		return temp;
	}

	public EnvironmentDescription getEnvironmentDescription() {
		return environmentDescription;
	}
}
