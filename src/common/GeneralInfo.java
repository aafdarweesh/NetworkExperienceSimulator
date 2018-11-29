package common;

import java.io.Serializable;
import java.util.ArrayList;

import entity.Node;
import entity.Packet;

@SuppressWarnings("serial")
public class GeneralInfo implements Serializable {
	public int timeCounter; // Count the number of iterations
	public int numberOfDroppedPackets; // Count number of dropped packets
	public int rerequestedPackets; // Count number of re-requested packets
	public int limitedTimeForPacket; // Limited time for a packet before re-requesting
	public int packetDeliveredMoreThanOneTime; // Count number of packets delivered more than once
	public int numberOfNodes; // Number of nodes in the system

	public int bufferSize; // Buffer size for each node (changes according to the test case)

	public ArrayList<Node> allNodes; // List of all nodes in the system
	public int[][] nodesConnections; // Connection between nodes in the system

	public ArrayList<Packet> delivered; // List of delivered packets
	public ArrayList<Packet> dropped; // List of dropped packets

	// Constructor, initialize the variables
	public GeneralInfo() {

		this.timeCounter = 0;
		this.numberOfDroppedPackets = 0;
		this.rerequestedPackets = 0;
		this.limitedTimeForPacket = 30;
		this.packetDeliveredMoreThanOneTime = 0;

		this.allNodes = new ArrayList<Node>();
		this.delivered = new ArrayList<Packet>();
		this.dropped = new ArrayList<Packet>();

	}

	// Check the status of a node
	public boolean CheckNodeStatus(int nodeId) {
		return this.allNodes.get(nodeId).notfailure;
	}

	// Add packet to list of expected packets in the destination node
	/**
	 * This function works as the initial connection between the nodes (source,
	 * destination) so it is the first request, followed by the data that is
	 * requested
	 **/
	public void addToNodeExpected(Packet newPacket) {
		int destination;
		destination = newPacket.destination;
		
		if (CheckNodeStatus(destination))
			allNodes.get(destination).expectedPackets.add(newPacket);
	}

	// PRINT the status of all nodes in the given time
	public void printToSystem() {
		System.out.println("//////////////////////////////////////");
		System.out.println("The time counter   " + timeCounter);
		System.out.println("//////////////////////////////////////");
		for (int i = 0; i < allNodes.size(); ++i) {
			System.out.println("Node : " + allNodes.get(i).id + ", Not Failure  :" + allNodes.get(i).notfailure
					+ ", The status : " + allNodes.get(i).status);

			System.out.println("the buffer content   ");
			allNodes.get(i).buffer.print();
			System.out.println("the expected Packets content   ");
			allNodes.get(i).printExpectedPacketsContent();
			System.out.println();
			System.out.println("=========================================");

		}

	}

	/**
	 * Dynamic Modifications
	 **/
	/*
	 * //For static Network (For Dynamic will use the DynamicAllocation class)
	 * public void GenerateConnectionBetweenNodes() { int n =
	 * this.numberOfNodes;//general.allNodes.size(); this.nodesConnections = new
	 * int[n][n]; Random rand = new Random(); int rando; for (int i = 0; i < n; ++i)
	 * { for (int j = 0; j < n; ++j) {
	 * 
	 * if (i == j || this.nodesConnections[i][j] == 1) continue;
	 * this.nodesConnections[i][j] = this.nodesConnections[j][i] = 0; rando =
	 * rand.nextInt(50) + 1; if (rando > 40) this.nodesConnections[i][j] =
	 * this.nodesConnections[j][i] = 1; } } }
	 * 
	 */

	// RUN Generalinfo class's main function, mainly used for storing objects
	public void Run(int numOfNodes, int bufSize) {

		numberOfNodes = numOfNodes;
		this.bufferSize = bufSize;
		// GenerateConnectionBetweenNodes();

	}

	// PRINT information about the Generalinfo class (detailed one)
	public void LastInfo() {
		// Print general info about the network
		System.out.println("numberOfDroppedPackets : " + numberOfDroppedPackets);
		System.out.println("rerequestedPackets : " + rerequestedPackets);
		System.out.println("limitedTimeForPacket : " + limitedTimeForPacket);
		System.out.println("packetDeliveredMoreThanOneTime : " + packetDeliveredMoreThanOneTime);

		// Print list of delivered packets
		System.out.println("List of Delievered packets : " + this.delivered.size());
		for (int i = 0; i < delivered.size(); ++i) {
			System.out.print("ID " + delivered.get(i).id);
			System.out.print(", Type " + delivered.get(i).type);
			System.out.print(", arrival time " + delivered.get(i).arrivelTime);
			System.out.print(", service time " + delivered.get(i).serviceTime);
			System.out.print(", destination " + delivered.get(i).destination);
			System.out.println(", source " + delivered.get(i).source);
		}

		// Print list of dropped packets
		System.out.println("List of Dropped packets : ");
		for (int i = 0; i < dropped.size(); ++i) {
			System.out.print("ID " + dropped.get(i).id);
			System.out.print(", Type " + dropped.get(i).type);
			System.out.print(", arrival time " + dropped.get(i).arrivelTime);
			System.out.print(", service time " + dropped.get(i).serviceTime);
			System.out.print(", destination " + dropped.get(i).destination);
			System.out.println(", source " + dropped.get(i).source);
		}
	}

}
