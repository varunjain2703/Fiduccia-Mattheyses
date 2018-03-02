@author Varun
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
