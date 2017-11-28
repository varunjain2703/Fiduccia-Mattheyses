package fiducciaMarttheyses.net;

import java.util.HashSet;

public class NetFactory {
	private static HashSet<Net> netList=new HashSet<Net>();

	public static HashSet<Net> getNetList() {
		return netList;
	}

	public static Net createNewNet() {
		Net net=new Net();
		netList.add(net);
		return net;
	}
	
	public static void addNet(Net net) {
		netList.add(net);
	}
	
	public static void clear() {
		netList.clear();
	}
}
