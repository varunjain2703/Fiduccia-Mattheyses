package fiducciaMarttheyses.nodes;

import java.util.ArrayList;
import java.util.HashSet;

import fiducciaMarttheyses.algorithm.Globals;
import fiducciaMarttheyses.net.Net;
import fiducciaMarttheyses.partition.Partition;
import fiducciaMarttheyses.partition.PartitionFactory;

public class Node {

	private boolean locked=false;
	private Partition partition;
	private String name;
	private int area;
	private boolean touched=false;
	ArrayList<Net> nets=new ArrayList<Net>();
	
	private int gain;
	
	public Node(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public int getGain() {
		return gain;
	}
	public void setGain(int gain) {
		this.gain = gain;
	}
	
	public ArrayList<Net> getNets() {
		return nets;
	}

	public void addNet(Net connection) {
		nets.add(connection);
	}
	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}
	
	public void moveToOtherPartition() {
		Partition[] partitions=PartitionFactory.getPartitions();
		Partition destinationPartition=partitions[0];
		if(destinationPartition.equals(partition)) {
			destinationPartition=partitions[1];
		}
		partition.deleteNode(this);
		destinationPartition.addNode(this);
	}
	
	public void calculateGain() {
		Partition[] partitions=PartitionFactory.getPartitions();
		int gain=0;
		int maxGain=nets.size();
		Partition destinationPartition=partitions[0];
		if(destinationPartition.equals(partition)) {
			destinationPartition=partitions[1];
		}
		for(Net net:nets) {

				gain+=net.getDeltaGainByMovement(this,destinationPartition);
		}
		if(maxGain>Globals.maxGain) {
			Globals.maxGain=maxGain;
		}
		this.gain=gain;
	}
	
	public HashSet<Node> getNeighbours(){
		HashSet<Node> neighbors=new HashSet<>();
		for(Net net:nets) {
			neighbors.addAll(net.getNodes());
		}
		neighbors.remove(this);
		return neighbors;
	}
	public String toString() {
		return "Node :"+name +"("+gain+")";
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
}
