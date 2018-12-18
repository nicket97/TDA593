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

	
	public void findPath(){
		//fringe.add(start);
		findRoute();
	}
	
	
	public void setStartDestination (Node start,Node destination){
		this.closed=  new ArrayList <Node>();
		this.fringe = new ArrayList <Node>();
		this.start=start;
		this.destination=destination;
		fringe.add(start);
	}
	
	private static double linearPath(Point start, Point end){
		double zDif = Math.abs(Math.abs(start.getZ())-Math.abs(end.getZ()));
		double xDif = Math.abs(Math.abs(start.getX())-Math.abs(end.getX()));
		return xDif+zDif;
	}
	

	
	/**
	 * 
	 * @return
	 */
	public  Node findRoute(){
		System.out.println("FRINGE=========="+fringe==null);
		if(fringe.size()!=0){
			System.out.println("Roomnow "+fringe.get(0).nodeID);
		}
		if (fringe.isEmpty()){
			return null;
		} else if ((fringe.get(0).nodeID==(destination.nodeID))){
			return fringe.get(0); //found goal
		} else if (!(fringe.get(0).nodeID==(destination.nodeID))){
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

		System.out.println("Null "+node.nodeID);
		for (Node e:node.neighbours){
			if (!listContains(e,closed) && !e.wall){
				Node temp = new Node(false, false, false, e.nodeID, e.point);
				temp.parent=node;
				temp.neighbours=e.neighbours;
				//System.out.println("Fringe neighbor = "+ );
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
			if (temp.nodeID==check.nodeID){
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
