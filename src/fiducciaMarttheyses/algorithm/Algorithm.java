package fiducciaMarttheyses.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import fiducciaMarttheyses.net.Net;
import fiducciaMarttheyses.net.NetFactory;
import fiducciaMarttheyses.nodes.Node;
import fiducciaMarttheyses.nodes.NodeFactory;
import fiducciaMarttheyses.partition.Cut;
import fiducciaMarttheyses.partition.Partition;
import fiducciaMarttheyses.partition.PartitionFactory;

public class Algorithm {
	private static HashMap<Integer,ArrayList<Node>> gainBucket=new HashMap<Integer,ArrayList<Node>>();
	private static int maxGainIndex=-10000;
	private static int nodeCount;
	public static void partition(Result result, int sort) {
		int minCutSet=Cut.getCutsetSize();
		boolean improvement=true;
		int loopCounter=0;
		Cut.makeSnapshot();
		for(Partition partitions:PartitionFactory.getPartitions()) {
			partitions.createSnapshot();
		}
		while(improvement) {
			loopCounter++;
			improvement=false;
			initializeGainBucket();
			
			for(Node node: PartitionFactory.getPartitions()[0].getNodes()) {
			}
			for(Node node: PartitionFactory.getPartitions()[1].getNodes()) {
			}
			for(int i=0;i<nodeCount;i++) {
				boolean maxGainIncreased=false;
				Node nodeToMove=getNodeToMove(sort);
				if(nodeToMove==null) {
					break;
				}
				nodeToMove.moveToOtherPartition();
				Set<Node> neighbors=nodeToMove.getNeighbours();

				updateSelectedNodeProperties(nodeToMove);

				updateCutSet(nodeToMove);
				maxGainIncreased = updateNeighbourGain(neighbors);
				updateMaxGainPointer(maxGainIncreased);
				if(Cut.getCutsetSize()<minCutSet) {
					Cut.makeSnapshot();
					for(Partition partitions:PartitionFactory.getPartitions()) {
						partitions.createSnapshot();
					}
					minCutSet=Cut.getCutsetSize();
					improvement=true;
				}
			}
			Cut.recreateFromSnapshot();
			for(Partition partitions:PartitionFactory.getPartitions()) {
				partitions.recreateFromSnapshot();
			}
			for(Node node:NodeFactory.getAllNodes()) {
				node.calculateGain();
				node.setLocked(false);
				node.setTouched(false);
			}
			for(Net net:NetFactory.getNetList()) {
				net.updateIsCut();
			}
		}

		result.iteration=loopCounter;
		result.minCutSize=minCutSet;
	}


	private static Node getNodeToMove(int sort) {
		Node node=null;
		for(int i=maxGainIndex;i>=-Globals.maxGain;i--) {
			switch(sort) {
			case 0:
				gainBucket.get(i).sort(new AlphabeticSort());
				break;
			case 1:
				gainBucket.get(i).sort(new AreaWiseSort());
				break;
			case 2:
				gainBucket.get(i).sort(new NetWiseSort());
				break;
			case 3:
				gainBucket.get(i).sort(new NeighbourWiseSorting());
				break;
			}
			for(Node maxGainNode:gainBucket.get(i)) {
				double finalArea=(maxGainNode.getPartition().getArea()-maxGainNode.getArea())/(double)Globals.totalArea;

				double minArea=(Globals.areaConstraint-((double)Globals.areaMax/(double)Globals.totalArea));
				double maxArea=(Globals.areaConstraint+((double)Globals.areaMax/(double)Globals.totalArea));
				
				if(((double)(maxGainNode.getPartition().getArea())/(double)Globals.totalArea)<.5) {
					double minAreaDup=minArea;
					minArea=1-maxArea;
					maxArea=1-minAreaDup;
				}
				minArea=(double)Math.round(minArea*1000000000)/(double)1000000000;
				maxArea=(double)Math.round(maxArea*1000000000)/(double)1000000000;
				
				if(finalArea<minArea ||
					finalArea>maxArea) {
					continue;
				}
				else {
					return maxGainNode;
				}
			}
		}
		return node;
	}


	private static void updateMaxGainPointer(boolean maxGainIncreased) {
		if(!maxGainIncreased) {
			for(int j=maxGainIndex;j>=-maxGainIndex;j--) {
				if(gainBucket.get(j).size()>0)
				{
					maxGainIndex=j;
					break;
				}
			}
		}
	}


	private static boolean updateNeighbourGain(Set<Node> neighbors) {
		boolean maxGainIncreased=false;
		for(Node neighbour:neighbors) {
			neighbour.setTouched(true);
			if(neighbour.isLocked()) {
				continue;
			}
			int currentGain=neighbour.getGain();
			ArrayList<Node> currentBucket=gainBucket.get(currentGain);
			neighbour.calculateGain();
			int newGain=neighbour.getGain();
			if(newGain!=currentGain) {
				currentBucket.remove(neighbour);
				gainBucket.get(newGain).add(neighbour);

				if(newGain>maxGainIndex) {
					maxGainIndex=newGain;
					maxGainIncreased=true;
				}
			}
		}
		return maxGainIncreased;
	}


	private static void updateCutSet(Node nodeToMove) {
		for(Net net:nodeToMove.getNets()) {
			Cut.removeNet(net);
			net.updateIsCut();
			if(net.isCut()) {
				Cut.addNet(net);
			}
		}
	}


	private static void updateSelectedNodeProperties(Node nodeToMove) {
		gainBucket.get(nodeToMove.getGain()).remove(nodeToMove);
		nodeToMove.setLocked(true);
	}


	private static void initializeGainBucket() {
		for(int i=-Globals.maxGain;i<=Globals.maxGain;i++) {
			ArrayList<Node> bucketList=new ArrayList<Node>();
			gainBucket.put(i, bucketList);
		}
		for(Node node:NodeFactory.getAllNodes()) {
			int gain=node.getGain();
			if(maxGainIndex<gain) {
				maxGainIndex=gain;
			}
			ArrayList<Node> bucketList=gainBucket.get(gain);
			bucketList.add(node);
		}
	}
	
	public static void clear() {
		gainBucket=new HashMap<Integer,ArrayList<Node>>();
		maxGainIndex=-10000;
		nodeCount=NodeFactory.getAllNodes().size();
	}
}
