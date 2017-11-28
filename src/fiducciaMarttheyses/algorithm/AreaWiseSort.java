package fiducciaMarttheyses.algorithm;

import java.util.Comparator;

import fiducciaMarttheyses.nodes.Node;

public class AreaWiseSort implements Comparator<Node>{
	
		@Override
		public int compare(Node node1, Node node2) {
			return (node1.getArea()-node2.getArea());
		}
		
}
