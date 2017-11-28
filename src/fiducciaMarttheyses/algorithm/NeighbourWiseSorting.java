package fiducciaMarttheyses.algorithm;

import java.util.Comparator;

import fiducciaMarttheyses.nodes.Node;

public class NeighbourWiseSorting implements Comparator<Node>{

	@Override
	public int compare(Node n1, Node n2) {
		int compare= (n1.getNeighbours().size()-(n2.getNeighbours().size()));
		if(compare==0) {
			return n1.getArea()-n2.getArea();
		}
		return compare;
	}

}
