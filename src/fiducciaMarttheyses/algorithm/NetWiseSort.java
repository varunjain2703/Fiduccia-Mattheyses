package fiducciaMarttheyses.algorithm;

import java.util.Comparator;

import fiducciaMarttheyses.nodes.Node;

public class NetWiseSort implements Comparator<Node>{

	@Override
	public int compare(Node n1, Node n2) {
		int compare= (-1)*(n1.getNets().size()-(n2.getNets().size()));
		if(compare==0) {
			return n1.getArea()-n2.getArea();
		}
		return compare;
	}
}
