package robot;

import java.util.ArrayList;
import java.util.List;

import model.Mission;
import model.Node;

public interface IPathFinder {

	public List<Node> findPath(Node pointNode, Node pointNode2);
	
}
