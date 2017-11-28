package fiducciaMarttheyses.algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fiducciaMarttheyses.net.Net;
import fiducciaMarttheyses.net.NetFactory;
import fiducciaMarttheyses.nodes.Node;
import fiducciaMarttheyses.nodes.NodeFactory;

public class Util {

	public static void readNetDFile(String fileName) {
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(fileName));
			String line;
			reader.readLine();//ignore 0
			reader.readLine();//ignore pins
			reader.readLine();//ignoreNetCount
			int modules=Integer.parseInt(reader.readLine().trim());
			int padOffset=Integer.parseInt(reader.readLine().trim());
			int padCount=modules-padOffset;

			NodeFactory.setCellCount(padOffset+1);

			ArrayList<String> netConfig=new ArrayList<String>();

			line=reader.readLine();
			while(line!=null) {
				if(line.contains(" s ")) {
					processNet(netConfig);
					netConfig.clear();
				}
				netConfig.add(line);
				line=reader.readLine();
			}
			processNet(netConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static void processNet(ArrayList<String> netConfig) {
		if(netConfig.size()<2) {
			return;
		}
		for(String input:netConfig) {
			if(input.contains("p") && netConfig.size()==2) {
				return;
			}
		}
		Net net=new Net();
		for(String input:netConfig) {
			if(input.contains("p")){
				continue;
			}
			String[] inputSplit=input.split(" ");
			Node node=NodeFactory.getNodeByName(inputSplit[0]);
			net.addNode(node);
		}
		Node firstNode=net.getNodes().get(0);
		for(Net nodeNet:firstNode.getNets()) {
			if(nodeNet.equals(net)) {
				return;
			}
		}
		for(Node node:net.getNodes()) {
			node.addNet(net);
		}
		NetFactory.addNet(net);
	}

	public static void readAreaFile(String fileName) {
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(fileName));
			String line=reader.readLine();
			while(line!=null) {
				String[] input=line.split(" ");
				if(!input[0].contains("p")) {
					int area=Integer.parseInt(input[1].trim());
					if(area>Globals.areaMax) {
						Globals.areaMax=area;
					}
					Globals.totalArea+=area;

					Node node=NodeFactory.getNodeByName(input[0]);
					node.setArea(area);
				}
				line=reader.readLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
