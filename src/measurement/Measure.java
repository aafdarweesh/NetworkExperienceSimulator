package measurement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import common.*;
//import entity.*;

public class Measure {

	public Measure(GeneralInfo g, Generator gen, int counter, int numberOfPackets, String testResultPath) {
		double endToEnd = 0;
		double avgDistance = 0;
		for (int i = 0; i < g.delivered.size(); ++i) {
			endToEnd += g.delivered.get(i).startServiceTime - g.delivered.get(i).arrivelTime;
			avgDistance += g.delivered.get(i).distanceTraveled;
		}
		endToEnd += (g.limitedTimeForPacket * g.rerequestedPackets);
		endToEnd /= (g.delivered.size());// + g.rerequestedPackets
		avgDistance /= g.delivered.size();
		double throughput = g.delivered.size();
		throughput /= counter;

		System.out.println("Average End-To-End : " + endToEnd);
		System.out.println("Throughput : " + throughput);
		System.out.println("Average Distance Travelled : " + avgDistance);
		System.out.println("Number of Delivered : " + g.delivered.size());
		System.out.println("Number of Dropped packets : " + g.dropped.size());
		System.out.println("Number of Re-requested packets : " + g.rerequestedPackets);
		System.out.println("Number of packets Arrived more than once : " + g.packetDeliveredMoreThanOneTime);

		System.out.println("\nNumber of Nodes : " + g.allNodes.size());
		System.out.println("Measurement Time : " + counter);
		System.out.println("Buffer size  : " + g.allNodes.get(0).buffer.bufferSize);
		// System.out.println("Number of Total Packets generated : " +
		// gen.totalNumOfPackets);
		System.out.println("Number of Packets arrived to the system : " + numberOfPackets);
		System.out.println("Packet Time limit : " + g.limitedTimeForPacket + "\n\n\n\n\n");

		/*
		 * for(int i = 0; i < g.allNodes.size(); ++i){
		 * 
		 * for(int j= 0; j < g.allNodes.size(); ++j){
		 * 
		 * System.out.print(" "+ g.nodesConnections[i][j]);
		 * 
		 * } System.out.print("\n");
		 * 
		 * }
		 */

		
		String resultInLine = "\n" + Double.toString(endToEnd) + " " + Double.toString(throughput) + " "
				+ Double.toString(avgDistance) + " " + Integer.toString(g.delivered.size()) + " "
				+ Integer.toString(g.dropped.size()) + " " + Integer.toString(g.rerequestedPackets) + " "
				+ Integer.toString(g.packetDeliveredMoreThanOneTime) + " " + Integer.toString(g.allNodes.size()) + " "
				+ Integer.toString(counter) + " " + Integer.toString(g.allNodes.get(0).buffer.bufferSize) + " "
				+ Integer.toString(gen.totalNumOfPackets) + " " + Integer.toString(g.limitedTimeForPacket) + "\n";

		try {
			File file = new File(testResultPath);
			FileWriter fr = new FileWriter(file, true);
			fr.write(resultInLine);
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
