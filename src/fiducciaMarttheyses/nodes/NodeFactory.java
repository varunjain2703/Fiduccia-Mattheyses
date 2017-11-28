package fiducciaMarttheyses.nodes;

import java.util.ArrayList;

public class NodeFactory {

	static Node[] cells=null;


	public static Node getNodeByName(String nodeName) {
		nodeName=nodeName.trim();
		int nodeNumber=Integer.parseInt(nodeName.substring(1));
		Node toReturn=null;
		if(nodeName.startsWith("a")) {
			toReturn=cells[nodeNumber];
			if(toReturn==null) {
				toReturn=new Cell(nodeName);
				cells[nodeNumber]=toReturn;
			}
		}
		return toReturn;
	}

	public static void setCellCount(int cellCount) {
		cells=new Node[cellCount];
	}

	
	public static Node[] getAllCells() {
		return cells;
	}
	

	
	public static ArrayList<Node> getAllNodes() {
		ArrayList<Node> toReturn=new ArrayList<Node>();
		for(Node cell:cells) {
			toReturn.add(cell);
		}

		return toReturn;
	}
	
	public static void clear() {
		cells=null;
	}
}
