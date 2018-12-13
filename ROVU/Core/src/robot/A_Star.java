package robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import project.Point;
import model.Mission;


/**
 * 
 * Working A* algorithm
 * @author Anthony
 *
 */

public class A_Star extends PathFinder{


	private static List <Node> map;
	private double [] distances;
	private static Node start;
	private static Node destination;
	
	private static List <Node> fringe;
	private static List <Node> closed;
	static TreeMap<Double, Node> tk;
	
	
	public void findPath(){
		//fringe.add(start);
		findRoute();
	}
	
	private static double linearPath(Point start, Point end){
		double zDif = Math.abs(Math.abs(start.getZ())-Math.abs(end.getZ()));
		double xDif = Math.abs(Math.abs(start.getX())-Math.abs(end.getX()));
		return xDif+zDif;
	}
	
	public static void computeLinearPath(){
		/*Map tk = new TreeMap();
		
		for (int i=0;i<map.size();i++){
			double dist =linearPath(map.get(i).point,destination.point); //compute heuristic distance for each node
			tk.put(dist, map.get(i));
		}
		fringe = new ArrayList<>(tk.values());
		fringe.remove(0);//remove goal*/
		tk = new TreeMap();
		
		for (int i=0;i<start.neighbours.length;i++){
			double dist =linearPath(start.neighbours[i].point,destination.point); //compute heuristic distance for each node
			start.neighbours[i].distance=(dist+1);
			tk.put(dist+1, start.neighbours[i]);
		}
		fringe = new ArrayList<>(tk.values());
		System.out.println("Frin  "+fringe.size());
		//fringe.remove(0);
	}
	
	private static Node findRoute(){
		if(fringe.size()!=0){
			System.out.println("Roomnow "+fringe.get(0).roomID);
		}
		System.out.println("Listsize "+fringe.size());
		if (fringe.isEmpty()){
			return null;
		} else if ((fringe.get(0).roomID==(destination.roomID))){
			System.out.println("YES "+  fringe.get(0).roomID);
			return fringe.get(0); //found goal
		} else if (!(fringe.get(0).roomID==(destination.roomID))){
			//fringe.remove(0);
			System.out.println("Here  "+ fringe.get(0).roomID);
			Node temp=fringe.remove(0);
			if (!listContains(temp,closed)){
				System.out.println("Tempclosed "+!closed.contains(temp));
				closed.add(temp);
				neighborFringe (temp);
				findRoute();
		}
			

					
		}
		//System.out.println(fringe.get(0).roomID);
		return findRoute();

	}
	
	
	private static void neighborFringe (Node node){
		int i=0;
		tk = new TreeMap<Double, Node>();
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
				System.out.println("Tempmap "+tk.size());
				
				i++;
			}
		}

		//fringe = new ArrayList<>(tk.values());
		//System.out.println("frin2  "+fringe.get(0).roomID);
	}
	
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
	
	//Testing class
	public static void main (String [] args){
		map = new ArrayList <Node>();
		closed=  new ArrayList <Node>();
		fringe=new ArrayList <Node>();
		Node n1= new Node(false, false, false, 0, new Point (0,0));
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
		map.add(n1);
		map.add(n2);
		map.add(n3);
		map.add(n4);
		start=n1;
		destination=n2;
		

		
		fringe.add(start);
				Node nk =findRoute();
		System.out.println("Final "+nk.roomID);
		
	}
		
	public static List <Node> getRouteList(Node finalNode){
		List <Node> path = new ArrayList<Node>();
		Node par = new Node();
		Node temp = new Node();
		temp=finalNode;
		while (par!=null){
			path.add(temp);
			par=temp.getParent();
			temp=par;
		}
		return path;
	}
	
	private static boolean listContains (Node check, List <Node>list){
		for (Node temp:list){
			if (temp.roomID==check.roomID){
				return true;
			}
		}
		return false;		
	}
	
	
	public List getPath(Mission mission){
		return null;
	}
	
}
