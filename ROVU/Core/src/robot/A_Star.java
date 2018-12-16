package robot;


import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import project.Point;
import simbad.sim.Boundary;
import simbad.sim.HorizontalBoundary;
import simbad.sim.HorizontalWall;
import simbad.sim.VerticalBoundary;
import simbad.sim.VerticalWall;
import simbad.sim.Wall;
import utility.Main;
import model.Mission;


/**
 * 
 * Working A* algorithm
 * @author Anthony
 *
 */

public class A_Star extends PathFinder{


	private  List <Boundary> bounds;
	private  List <Wall> walls;
	
	
	private  List <Node> map;

	private  Node start;
	private  Node destination;
	
	private  List <Node> fringe;
	private  List <Node> closed;

	public void setWalls(List <Boundary> bounds, List <Wall> walls){
		this.bounds=bounds;
		this.walls=walls;
	}
	
	public void findPath(){
		//fringe.add(start);
		findRoute();
	}
	
	public void setMap(List <Node> map){
		this.map=map;
		this.closed=  new ArrayList <Node>();
		this.fringe = new ArrayList <Node>();
	}
	
	public void setStartDestination (Node start,Node destination){
		this.start=start;
		this.destination=destination;
		fringe.add(start);
	}
	
	private static double linearPath(Point start, Point end){
		double zDif = Math.abs(Math.abs(start.getZ())-Math.abs(end.getZ()));
		double xDif = Math.abs(Math.abs(start.getX())-Math.abs(end.getX()));
		return xDif+zDif;
	}
	
	public  void computeLinearPath(){
		/*Map tk = new TreeMap();
		
		for (int i=0;i<map.size();i++){
			double dist =linearPath(map.get(i).point,destination.point); //compute heuristic distance for each node
			tk.put(dist, map.get(i));
		}
		fringe = new ArrayList<>(tk.values());
		fringe.remove(0);//remove goal*/
	
		
		for (int i=0;i<start.neighbours.length;i++){
			double dist =linearPath(start.neighbours[i].point,destination.point); //compute heuristic distance for each node
			start.neighbours[i].distance=(dist+1);
		
		}

		System.out.println("Frin  "+fringe.size());
		//fringe.remove(0);
	}
	
	/**
	 * 
	 * @return
	 */
	public  Node findRoute(){
		System.out.println("FRINGE=========="+fringe==null);
		if(fringe.size()!=0){
			System.out.println("Roomnow "+fringe.get(0).roomID);
		}
		if (fringe.isEmpty()){
			return null;
		} else if ((fringe.get(0).roomID==(destination.roomID))){
			return fringe.get(0); //found goal
		} else if (!(fringe.get(0).roomID==(destination.roomID))){
			//fringe.remove(0);
			Node temp=fringe.remove(0);
			if (!listContains(temp,closed)){
				closed.add(temp);
				neighborFringe (temp);
				findRoute();
		}
			

					
		}
		//System.out.println(fringe.get(0).roomID);
		return findRoute();

	}
	
	/**
	 * 
	 * @param node
	 */
	private  void neighborFringe (Node node){
		int i=0;

		System.out.println("Null "+node.roomID);
		for (Node e:node.neighbours){
			if (!listContains(e,closed) && !e.wall){
				Node temp = new Node(false, false, false, e.roomID, e.point);
				temp.parent=node;
				temp.neighbours=e.neighbours;
				System.out.println("ID  "+temp.roomID );
				temp.distance=node.distance+linearPath(node.point,temp.point);//linearPath=1, always the same
				double tempLin = linearPath(node.point,destination.point);
				temp.linear=temp.distance+tempLin;
				//tk.put(temp.linear, temp);
				sortedPut(temp,fringe);

				
				i++;
			}
		}

		//fringe = new ArrayList<>(tk.values());
		//System.out.println("frin2  "+fringe.get(0).roomID);
	}
	
	
	/**
	 * 
	 * @param put
	 * @param list
	 * @return
	 */
	private static boolean sortedPut (Node put, List <Node>list){
		
		if (list.isEmpty()){
			list.add(put);
			return true;
		}
		
		for (int i=0;i<list.size();i++){
			if (list.get(i).distance>=put.distance){
				list.add(i, put);
				return true;
			}
		}
		list.add(put); 
		return false;
	}
	
	
	
	/**
	 * 
	 * @param check
	 * @param scale
	 * @param diagonal
	 * @param map
	 * @return
	 */
	public static Node [] neighbouring (Node check, int scale, boolean diagonal, List <Node> map){
		//-10,-1,+1,+10
		//-11,-10,-9,-1,+1,+9,+10,+11
        List <Node> temp = new ArrayList<Node> ();
		if (!diagonal){ //adding 4 neighbors
			
		try {temp.add(map.get(map.indexOf(check)-scale));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)-1));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)+1));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)+scale));			
			}catch (Exception e){System.out.println("No element here");}
		
		} else { //adding 8 neighbors	
			
		try {temp.add(map.get(map.indexOf(check)-scale-1));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)-scale));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)-scale+1));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)-1));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)+1));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)+scale-1));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)+scale));			
			}catch (Exception e){System.out.println("No element here");}
		try {temp.add(map.get(map.indexOf(check)+scale+1));			
			}catch (Exception e){System.out.println("No element here");}		
		}
		
		Node [] ret = new Node [temp.size()];
		for (int n=0;n<temp.size();n++){
			ret[n]=temp.get(n);
		}
		return ret;	
	}
	
	/**
	 * 
	 * @param p
	 * @param coefficient
	 * @return
	 */	
	public  Node pointNode (Point p, double coefficient) {
		for (Node v:map){
			Rectangle2D.Double temp = nodeRect(v, coefficient);//new Rectangle2D.Double(v.getPoint().getX(),v.getPoint().getX(),coefficient,coefficient);
			if (temp.contains(p.getX(), p.getZ())){
				return v;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param node
	 * @param coefficient
	 * @return
	 */
	public Rectangle2D.Double nodeRect (Node node, double coefficient){
		Rectangle2D.Double temp = new Rectangle2D.Double(node.getPoint().getX(),node.getPoint().getZ(),coefficient,coefficient);
		return temp;
	}
	
	/**
	 * 
	 * @param wall
	 * @return
	 */
	//horizontal->(X,Zbegin,Zend),middle
	//vertical->(Z,Xbegin,Xend),middle

	public Rectangle2D.Double verticalWallRect (VerticalWall wall){ //Float p1z, Float p1x, Float p2x

			Rectangle2D.Double temp = new Rectangle2D.Double(wall.getP1x(),wall.getP1z()-0.15,Math.abs(Math.abs(wall.getP1x()-Math.abs(wall.getP2x()))),0.3);
			return temp;

	}
	
	/**
	 * 
	 * @param wall
	 * @return
	 */
	public Rectangle2D.Double horizontalWallRect (HorizontalWall wall){ //Float p1z, Float p1x, Float p2x

		Rectangle2D.Double temp = new Rectangle2D.Double(wall.getP1x()-0.15,wall.getP1z(),Math.abs(Math.abs(wall.getP1z()-Math.abs(wall.getP2z()))),0.3);
		return temp;

	}
	
	/**
	 * 
	 * @param wall
	 * @return
	 */
	public Rectangle2D.Double verticalBoundaryRect (VerticalBoundary wall){ //Float p1z, Float p1x, Float p2x
		
			Rectangle2D.Double temp = new Rectangle2D.Double(wall.getP1x(),wall.getP1z()-0.15,Math.abs(Math.abs(wall.getP1x()-Math.abs(wall.getP2x()))),0.3);
			return temp;
	
	}
	
	/**
	 * 
	 * @param wall
	 * @return
	 */
	public Rectangle2D.Double horizontalBoundaryRect (HorizontalBoundary wall){ //Float p1z, Float p1x, Float p2x

		Rectangle2D.Double temp = new Rectangle2D.Double(wall.getP1x()-0.15,wall.getP1z(),Math.abs(Math.abs(wall.getP1z()-Math.abs(wall.getP2z()))),0.3);
		return temp;

	}
	
	/**
	 * 
	 * @param node
	 * @param coefficient
	 * @return
	 */
	public Point getNodeCenter(Node node, double coefficient){
		Point temp = new Point (node.getPoint().getX()+coefficient/2,node.getPoint().getZ()+coefficient/2);
		return temp;		
	}
	
	/**
	 * 
	 * @param scale
	 * @param coefficient
	 * @return
	 */
	public  List<Node> generateEmptyGrid(int scale, double coefficient){
		List <Node> exmap = new ArrayList<Node> ();
		int z=0;
		/*for (int i=1;i<scale+1;i++){//i=1;i<scale+1
			for (int k=1;k<scale+1;k++){
			Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
					BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
			exmap.add(temp);
			map.add(temp);
			z++;
			}
			
		}*/
	    
		for (int i=-scale/2;i<0/2;i++){ //upper right
			
			for (int k=-scale/2;k<0;k++){
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				System.out.println("Null "+map==null);
				System.out.println("Null2== "+temp==null);
				map.add(temp);
				z++;
			}
			for (int k=0;k<scale/2+1;k++){
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				map.add(temp);
				z++;
			}
		}
		
		for (int i=0;i<scale/2+1;i++){ //upper right
			for (int k=-scale/2;k<0;k++){
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				map.add(temp);
				z++;
			}
			for (int k=0;k<scale/2+1;k++){
				Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(coefficient*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
						BigDecimal.valueOf(coefficient*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
				exmap.add(temp);
				map.add(temp);
				z++;
			}
		}
		for (Node v:map){
			
			Node [] neigh = neighbouring (v, scale, false, map);

			v.neighbours=neigh;
			
			}
		return map;
	}
	
	
	
	//horizontal->(X,Zbegin,Zend)
	//vertical->(Z,Xbegin,Xend)
	
	/**
	 * 
	 * @param toWall
	 * @param bounds
	 * @param walls
	 * @return
	 */
	public List<Node> walling (List<Node> toWall, double coefficient){
		List <Rectangle2D.Double> obstacles = new ArrayList <Rectangle2D.Double>();
		Rectangle2D.Double temp;
		for (Boundary b:bounds){
		    if (b.getClass()==VerticalBoundary.class){		    	
		    	temp=verticalBoundaryRect((VerticalBoundary)b);
		    	obstacles.add(temp);
		    }else{
		    	temp=horizontalBoundaryRect((HorizontalBoundary)b);
		    	obstacles.add(temp);
		    }
		}
		
		for (Wall w:walls){
		    if (w.getClass()==VerticalWall.class){		    	
		    	temp=verticalWallRect((VerticalWall)w);
		    	obstacles.add(temp);
		    }else{
		    	temp=horizontalWallRect((HorizontalWall)w);
		    	obstacles.add(temp);
		    }
		}
		
		for (Rectangle2D.Double n:obstacles){
			for (Node v:toWall){
			if (nodeRect(v,coefficient).intersects(n)){
				v.setWall(true);
			}
			}
		}
		
		return toWall;		
	}
	
	
	
	
	
	
	
	
	//Testing class
	public static void main (String [] args){
		


	
		//map = new ArrayList <Node>();
		//closed=  new ArrayList <Node>();
		//fringe=new ArrayList <Node>();
		/*Node n1= new Node(false, false, false, 0, new Point (0,0));
		Node n2= new Node(false, false, false, 1, new Point (0,1));
		Node n3= new Node(false, false, false, 2, new Point (0,2));
		Node n4= new Node(false, false, false, 3, new Point (1,1));
		Node [] z1 = {n2};
		Node [] z2 = {n1,n3,n4};
		Node [] z3 = {n2};
		Node [] z4 = {n2};
		n1.neighbours=z1;
		n2.neighbours=z2;
		n3.neighbours=z3;
		n4.neighbours=z4;
		/*map.add(n1);
		map.add(n2);
		map.add(n3);
		map.add(n4);
		start=n1;
		destination=n2;*/
		
		//0.3f - wall thickness
        //20x20f area
		//20/0.3 =66
		//400/0.3 = 1333
		List <Node> exmap = new ArrayList<Node> ();
	/*	int z=0;
		for (int i=1;i<67;i++){//i=1;i<scale+1
			for (int k=1;k<67;k++){
			Node temp = new Node (false, false, false, z, new Point(BigDecimal.valueOf(0.3*i).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(),
					BigDecimal.valueOf(0.3*k).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));	
			exmap.add(temp);
			map.add(temp);
			z++;
			}			
		}

		for (Node v:map){
			
		Node [] neigh = neighbouring (v, 66, false, map);

		v.neighbours=neigh;
		
		}
		

		start=map.get(72);
		destination=map.get(2); 
		
		fringe.add(start);
				Node nk =findRoute();
		System.out.println("Final "+nk.roomID);
		*/
	/*	List <Node> test = generateEmptyGrid(66,0.3);
		start=map.get(72);
		destination=map.get(2); 
		for (Node v:map){
			
			Node [] neigh = neighbouring (v, 66, false, map);

			v.neighbours=neigh;
			
			}
		fringe.add(start);
				Node nk =findRoute();
				List <Node> routeC = getRouteList(nk);
		for (Node x:test){
			System.out.println("X= "+x.getPoint().getX()+ " Z="+x.getPoint().getZ());
		}
		for (Node b:routeC){
			System.out.println("WW="+b.roomID);
		}
		*/
	}
		
	
	/**
	 * 
	 * @param finalNode
	 * @return
	 */
	public  List <Node> getRouteList(Node finalNode){
		List <Node> path = new ArrayList<Node>();
		List <Node> toReturn = new ArrayList<Node>();
		Node par = new Node();
		Node temp = new Node();
		temp=finalNode;
		while (par!=null){
			System.out.println("Inside==PointX======"+temp.getPoint().getX()+" PointZ======"+temp.getPoint().getZ());
			path.add(0,temp);
			par=temp.getParent();
			temp=par;
		}	

		for (Node temp2:path){
			System.out.println("Returning==PointX======"+temp2.getPoint().getX()+" PointZ======"+temp2.getPoint().getZ());
		}
		return path;
	}
	
	/**
	 * 
	 * @param check
	 * @param list
	 * @return
	 */
	private  boolean listContains (Node check, List <Node>list){
		for (Node temp:list){
			if (temp.roomID==check.roomID){
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * 
	 */
	public List getPath(Mission mission){
		return null;
	}
	
}
