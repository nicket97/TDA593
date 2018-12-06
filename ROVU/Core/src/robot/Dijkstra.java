package robot;

import java.util.ArrayList;
import java.util.List;

import model.Mission;


/**
 * Dummy non-working algorithm
 * @author Anthony
 *
 */

public class Dijkstra {

	private static List <Node> settledNodes;
	private static List <Node> unsettledNodes;
	private static int tempPath;
	private static Node destination;
	private static Node start;

	

	
	
	private static Node Find(){
		
		System.out.println("Suke"+ (start==null));
		settledNodes.add(start);
		Node current = start;
		while (!unsettledNodes.isEmpty()){ //while there are still unvisited nodes
			Node check = Evaluate(current);
			boolean shortest = false;
			if (check.equals(destination)){
				return destination;
			}
			int newDist = current.distanceFromStart+check.weight; //distance between nodes
			
			for (Node e:settledNodes){ //check if evaluated node's distance from start is shortest
				if (e.distanceFromStart>check.distanceFromStart){
					shortest=true;
				}else{
					shortest=false;
				}				
			}
			System.out.println("Shortest  "+shortest);
			if (newDist>tempPath ){ 
				tempPath=newDist;
			}			
			unsettledNodes.remove(current);
			settledNodes.add(current);	
			current=check;
			
		}
		return null;
	}
	
	private static Node Evaluate (Node check){
		for (Node e:check.neighbours){
					if (!settledNodes.contains(e))	{ //choose yet unvisited node            // && !e.wall
						e.predecessors.add(check);		//add current node to predecessors
						int distStart = check.distanceFromStart+e.weight;
						e.setDistStart(distStart); 
						return e;  //return new node
					}		
		}
		return null;
	}
	


	
	public List getPath(Mission mission){
		if (!destination.predecessors.isEmpty()){
			return destination.predecessors;
		}
		return null;
	}
	
	
	/*
	 //Testing method
	public static void main (String [] args){
		settledNodes = new ArrayList <Node>();
		unsettledNodes = new ArrayList <Node>();
		Node n0 = new Node(false, false, false, 5000, null);
		Node n1 = new Node(false, false, false, 5000, null);
		Node n2 = new Node(false, false, false, 5000, null);
		Node n3 = new Node(false, false, false, 5000, null);
		Node n4 = new Node(false, false, false, 5000, null);//N1>N3>N4
		Node n5 = new Node(false, false, false, 5000, null);//
		Node [] test1 = {n1,n2};
		Node [] test2 = {n1,n3};
		Node [] test3 = {n2,n4};
		
		Node [] test4 = {n0,n2};
		Node [] test5 = {n3,n5};
		Node [] test6 = {n4};
		n1.neighbours=test4;
		unsettledNodes.add(n1);
		n2.neighbours=test2;
		unsettledNodes.add(n2);
		n3.neighbours=test3;
		unsettledNodes.add(n3);
		n4.neighbours=test5;
		unsettledNodes.add(n4);
		n5.neighbours=test6;
		unsettledNodes.add(n5);
		n0.neighbours=test1;
		start=n0;
		destination=n5;
		Node jk=Find();
		System.out.println("System  "+(jk.predecessors.get(1).eating));
		
		
	}
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
