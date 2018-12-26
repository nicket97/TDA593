package robot;



import java.util.ArrayList;
import java.util.List;
import model.Node;
import project.Point;
import model.Mission;


/**
 * 
 * Working A* algorithm
 * @author Anthony
 *
 */

public class A_Star extends PathFinder{
	private  Node start;
	private  Node destination;
	
	private  List <Node> fringe;
	private  List <Node> closed;

	/**
	 * Method to get route to the destination node
	 * @return
	 */
	public List <Node> findPath(){
		return getRouteList(findRoute());
	}
	
	/**
	 * Method to set start and destination for calculating the route
	 * @param start
	 * @param destination
	 */
	public void init(Node start, Node destination){
		this.closed=  new ArrayList <Node>();
		this.fringe = new ArrayList <Node>();
		this.start=start;
		this.destination=destination;
		fringe.add(start);
	}
	
	/**
	 * Method for obtaining estimation of distance between two points
	 * @param start
	 * @param end
	 * @return
	 */
	private static double linearPath(Point start, Point end){
		double zDif = Math.abs(Math.abs(start.getZ())-Math.abs(end.getZ()));
		double xDif = Math.abs(Math.abs(start.getX())-Math.abs(end.getX()));
		return xDif+zDif;
	}

	/**
	 * Method for calculating route to the destination node
	 * @return
	 */
	public  Node findRoute(){
		if (fringe == null || fringe.isEmpty()) {
            System.out.println("Unable to find path");
		    return null;
        }
//		System.out.println("Current room"+fringe.get(0).getNodeID());
        
        if (fringe.get(0).getNodeID() == destination.getNodeID()) {
		    return fringe.get(0); // Found goal
        } else {
		    Node temp = fringe.remove(0);
		    if (!listContains(temp, closed)) {
		        closed.add(temp);
		        neighborFringe(temp);
		        findPath();
            }
        }
		return findRoute();
	}
	
	/**
	 * Method to check neighbors of current evaluated node in algorithm
	 * @param node
	 */
	private  void neighborFringe (Node node){
		for (Node e:node.getNeighbors()){
			if (!listContains(e,closed) && !e.isWall()){
				Node temp = new Node(false, false, false, e.getNodeID(), e.getPoint());
				temp.setParent(node);
				temp.setNeighbors(e.getNeighbors());
				temp.setDistance(node.getDistance()+linearPath(node.getPoint(),temp.getPoint()));//linearPath=1, always the same
				double tempLin = linearPath(node.getPoint(),destination.getPoint());
				temp.setLinear(temp.getDistance()+tempLin);
				sortedPut(temp,fringe);
			}
		}
	}
	
	
	/**
	 * Method to put node in the evaluation list according to its distance from destination
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
			if (list.get(i).getDistance()>=put.getDistance()){
				list.add(i, put);
				return true;
			}
		}
		list.add(put); 
		return false;
	}

	/**
	 * Method to get list of nodes forming the route
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
			//System.out.println("Inside==PointX======"+temp.getPoint().getX()+" PointZ======"+temp.getPoint().getZ());
			path.add(0,temp);
			par=temp.getParent();
			temp=par;
		}	

		for (Node node:path){
			System.out.println("Returning==PointX======"+node.getPoint().getX()+" PointZ======"+node.getPoint().getZ());
		}
		return path;
	}
	
	/**
	 * Method for checking if list contains certain node
	 * @param check
	 * @param list
	 * @return
	 */
	private boolean listContains (Node check, List <Node>list){
		for (Node temp:list){
			if (temp.getNodeID()==check.getNodeID()){
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
