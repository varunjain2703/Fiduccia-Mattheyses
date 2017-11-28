package fiducciaMarttheyses.net;

import java.util.ArrayList;

import fiducciaMarttheyses.algorithm.AlphabeticSort;
import fiducciaMarttheyses.nodes.Node;
import fiducciaMarttheyses.partition.Partition;

public class Net {
	private ArrayList<Node> allNodes=new ArrayList<>();
	private boolean isCut;
	
	public boolean isCut() {
		return isCut;
	}
	
	public void addNode(Node node) {
		allNodes.add(node);
	}
	
	public ArrayList<Node> getNodes() {
		return allNodes;
	}
	
	public void updateIsCut() {
		Partition partition=null;
		isCut=false;
		for(Node node:allNodes) {
			if(partition!=null && !partition.equals(node.getPartition())){
				isCut=true;
				return;
			}
			partition=node.getPartition();
		}
	}

	public int getDeltaGainByMovement(Node cell,Partition p) {
		int toReturn=0;
		boolean previousIsCut=isCut;
		Partition previousPart=cell.getPartition();
		
		cell.setPartition(p);
		updateIsCut();
		
		if(previousIsCut && !isCut) {
			toReturn= 1;
		}
		else if(!previousIsCut && isCut) {
			toReturn=-1;
		}

		cell.setPartition(previousPart);
		isCut=previousIsCut;
		return toReturn;
	}
	

	
	public boolean equals(Net net) {
		if(this.toString().equals(net.toString())) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String primaryKey="";
		ArrayList<Node> all=(ArrayList<Node>) allNodes.clone();
		all.sort(new AlphabeticSort());
		for(Node node:allNodes) {
			primaryKey=primaryKey+node.getName();
		}
		return primaryKey;
	}
}
