package fiducciaMarttheyses.partition;

import java.util.ArrayList;

import fiducciaMarttheyses.nodes.Node;

public class Partition {
	String name;
	ArrayList<Node> nodes=new ArrayList<>();
	private int area;
	Partition snapshotPartition;

	public Partition(String name) {
		this.name=name;
	}

	public void addNode(Node inputNode) {
		nodes.add(inputNode);
		inputNode.setPartition(this);
		area+=inputNode.getArea();
	}
	public void deleteNode(Node inputNode) {
		inputNode.setPartition(null);
		nodes.remove(inputNode);
		area-=inputNode.getArea();
	}

	public ArrayList<Node> getNodes(){
		return nodes;
	}

	public int getArea() {
		return area;
	}

	public void createSnapshot() {
		snapshotPartition=new Partition(this.name);
		snapshotPartition.area=this.area;
		snapshotPartition.nodes=new ArrayList<Node>();
		for(Node node:this.nodes) {
			snapshotPartition.nodes.add(node);
		}
	}

	public void recreateFromSnapshot() {
		if(snapshotPartition!=null) {
			this.area=snapshotPartition.area;
			this.nodes=new ArrayList<Node>();
			for(Node node:snapshotPartition.nodes) {
				this.nodes.add(node);
			}

		}
	}

	public String getName() {
		return name;
	}


}
