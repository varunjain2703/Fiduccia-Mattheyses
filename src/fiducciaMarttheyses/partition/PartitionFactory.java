package fiducciaMarttheyses.partition;

import java.util.ArrayList;

import fiducciaMarttheyses.algorithm.AreaWiseSort;
import fiducciaMarttheyses.algorithm.Globals;
import fiducciaMarttheyses.algorithm.NetWiseSort;
import fiducciaMarttheyses.net.Net;
import fiducciaMarttheyses.net.NetFactory;
import fiducciaMarttheyses.nodes.Node;
import fiducciaMarttheyses.nodes.NodeFactory;

public class PartitionFactory {

	private static Partition[] partitions=new Partition[2];

	public static Partition[] getPartitions() {
		return partitions;
	}

	public static Partition[] naivePartition() {
		Partition partition1=new Partition("p1");
		Partition partition2=new Partition("p2");
		Node[] cells=NodeFactory.getAllCells();
		for(int i=0;i<cells.length/2;i++) {
			partition1.addNode(cells[i]);
		}
		for(int i=cells.length/2;i<cells.length;i++) {
			partition2.addNode(cells[i]);
		}

		boolean balanced=false;
		while(!balanced) {
			if(Globals.areaConstraint*Globals.totalArea-Globals.areaMax > partition1.getArea())
			{
				ArrayList<Node> nodes=partition2.getNodes();
				Node firstNode=nodes.get(0);
				partition2.deleteNode(firstNode);
				partition1.addNode(firstNode);
			}
			else if(Globals.areaConstraint*Globals.totalArea+Globals.areaMax < partition1.getArea())
			{
				ArrayList<Node> nodes=partition1.getNodes();
				Node firstNode=nodes.get(0);
				partition1.deleteNode(firstNode);
				partition2.addNode(firstNode);
			}
			else
			{
				balanced=true;
			}
		}
		partitions[0]=partition1;
		partitions[1]=partition2;
		
		updateGainAndCutSet();
		return partitions;
	}

	public static Partition[] areaWisePartition() {
		Partition partition1=new Partition("p1");
		Partition partition2=new Partition("p2");
		Node[] cells=NodeFactory.getAllCells();
		ArrayList<Node> areaSortedCells=new ArrayList<>();
		for(Node node:cells) {
			areaSortedCells.add(node);
		}
		areaSortedCells.sort(new AreaWiseSort());
		int i=0;
		while(true) {
			if(i>=areaSortedCells.size()) {
				break;
			}
			if(partition1.getArea()+areaSortedCells.get(i).getArea()>Globals.areaConstraint*Globals.totalArea+Globals.areaMax) {
				break;
			}
			partition1.addNode(areaSortedCells.get(i));
			i++;
		}
		for(;i<areaSortedCells.size();i++) {
			partition2.addNode(areaSortedCells.get(i));
		}

		partitions[0]=partition1;
		partitions[1]=partition2;
		
		updateGainAndCutSet();
		return partitions;
	}
	public static Partition[] netCountWisePartition() {
		Partition partition1=new Partition("p1");
		Partition partition2=new Partition("p2");
		Node[] cells=NodeFactory.getAllCells();
		ArrayList<Node> netSortedCells=new ArrayList<>();
		for(Node node:cells) {
			netSortedCells.add(node);
		}
		netSortedCells.sort(new NetWiseSort());
		int i=0;
		while(partition1.getArea()<Globals.areaConstraint*Globals.totalArea) {
			partition1.addNode(netSortedCells.get(i));
			i++;
		}
		for(;i<netSortedCells.size();i++) {
			partition2.addNode(netSortedCells.get(i));
		}
		partitions[0]=partition1;
		partitions[1]=partition2;
		
		updateGainAndCutSet();
		return partitions;
	}
	private static void updateGainAndCutSet() {
		ArrayList<Node> allNodes=NodeFactory.getAllNodes();
		for(Net net:NetFactory.getNetList()) {
			net.updateIsCut();
			if(net.isCut()) {
				Cut.addNet(net);
			}
		}
		for(Node cell:allNodes) {
			cell.calculateGain();
		}
	}
	
	public static void clear() {
		partitions=new Partition[2];
	}
}
