package fiducciaMarttheyses.algorithm;

import java.util.ArrayList;
import java.util.Scanner;

import fiducciaMarttheyses.net.NetFactory;
import fiducciaMarttheyses.nodes.NodeFactory;
import fiducciaMarttheyses.partition.Cut;
import fiducciaMarttheyses.partition.Partition;
import fiducciaMarttheyses.partition.PartitionFactory;

/**
 * This is the main class which is called by the executor
 * 
 * @author Varun
 * 
 */
public class MainClass {
	/**
	 * This is the main function that is called by the executor. It takes 4
	 * essential console inputs
	 * 		Ratio 				: ratio constraint as a double between 0 to 1
	 * 		File Path			: The path containing the nets and area. The file 
	 * 							  name should be added to the path without extensions.
	 * 							  Both .netD and .are file should have the same name.
	 * 		Initial Partitioning: The initial partitioning for the algorithm . 
	 * 							  0 for naive partitioning, 
	 * 							  1 for area wise partitioning and
	 * 							  2 for net wise partitioning.
	 * 		Ordering 			: method for deciding which cell to move when the 
	 * 							  list returned by max gain pointer contained more 
	 * 							  than one cells which could be moved while satisfying 
	 * 							  the area constraints. 
	 * 							  0 for input wise ordering,
	 * 							  1 for area wise ordering,
	 * 							  2 for net wise ordering and 
	 * 							  3 for neighbor wise ordering.
	 * Input Example:
	 * Ratio				:0.6
	 * File Path			:E:\Fiduccia Mattheyses\bin\inputs\ibm01\ibm01
	 * Initial Partitioning	:1
	 * Ordering				:1
	 * @param strings
	 */
	public static void main(String... strings) {
		Scanner in = new Scanner(System.in);
		System.out.print("Ratio\t\t\t:");
		double ratio = Double.parseDouble(in.nextLine());
		System.out.print("File Path\t\t:");
		String fileName = in.nextLine();
		System.out.print("Initial Partitioning\t:");
		int initialPart = Integer.parseInt(in.next());
		System.out.print("Ordering\t\t:");
		int ordering = Integer.parseInt(in.next());
		System.out.println();
		in.close();
		
		ArrayList<Result> results = new ArrayList<>();
		Globals.areaConstraint = ratio;

		Result result = new Result();
		results.add(result);
		result.areaConstraint = ratio;
		result.fileName = fileName;
		Util.readNetDFile(fileName + ".netD");
		Util.readAreaFile(fileName + ".are");
		Partition[] partitions;
		if (initialPart == 0) {
			partitions = PartitionFactory.naivePartition();
		} else if (initialPart == 1) {
			partitions = PartitionFactory.areaWisePartition();
		} else {
			partitions = PartitionFactory.netCountWisePartition();
		}
		Algorithm.clear();
		long start = System.currentTimeMillis();
		Algorithm.partition(result, ordering);
		long end = System.currentTimeMillis();

		result.time = end - start;
		result.netSize = NetFactory.getNetList().size();
		result.cellSize = NodeFactory.getAllCells().length;
		NetFactory.clear();
		NodeFactory.clear();
		PartitionFactory.clear();
		Cut.clear();
		Globals.clearGlobals();

		System.out.println("Result :");

		System.out.println("File :" + result.fileName);
		System.out.println("Area constraint :" + result.areaConstraint);
		System.out.println("Initial cutset size: " + result.initialCustset);
		System.out.println("Final cutset size :" + result.minCutSize);
		System.out.println("Iterations :" + result.iteration);
		System.out.println("Time :" + result.time + " miliseconds");

	}
}
