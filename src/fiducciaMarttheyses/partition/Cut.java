package fiducciaMarttheyses.partition;

import java.util.HashSet;
import java.util.Set;

import fiducciaMarttheyses.net.Net;

public class Cut {
	static Set<Net> cutConnections=new HashSet<Net>();
	static Set<Net> snapShot=new HashSet<Net>();
	
	public static void addNet(Net net) {
		cutConnections.add(net);
	}
	
	public static void removeNet(Net net) {
		cutConnections.remove(net);
	}
	
	public static int getCutsetSize() {
		return cutConnections.size();
	}
	
	public static void clear() {
		cutConnections.clear();
	}
	
	public static void makeSnapshot() {
		snapShot=cutConnections;
	}
	
	public static void recreateFromSnapshot() {
		cutConnections=snapShot;
	}

	public static Set<Net> getCutConnections() {
		return cutConnections;
	}
}
