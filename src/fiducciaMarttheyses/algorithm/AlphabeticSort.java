package fiducciaMarttheyses.algorithm;

import java.util.Comparator;

import fiducciaMarttheyses.nodes.Node;

public class AlphabeticSort implements Comparator<Node>{

		@Override
		public int compare(Node n1, Node n2) {
			return n1.getName().compareTo(n2.getName());
		}
}
