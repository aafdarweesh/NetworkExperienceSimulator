package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import entity.Packet;
import entity.Server;

@SuppressWarnings("serial")
public class Generator implements Serializable {

	public int totalNumOfPackets; // Number of packets
	public int numOfpacketType; // Number of packet types
	public ArrayList<Packet> genaratedPackets;
	public ArrayList<Integer> numOfPacketList; // Number of packets for each type
	public ArrayList<Integer> serviceTimeForEachPacket; // Service time for each packet
	public ArrayList<Integer> arrivalRate; // Store the rate at which the packet will appear
	public ArrayList<Integer> arrivalTime; // Arrival time for each packet to the system
	public GeneralInfo general;

	public double arrivalRateVariable; // Change the arrival rate according to the test

	// Constructor, that is connected to GeneralInfo
	public Generator(GeneralInfo g, int arrivalRateVariable) {
		general = g;
		genaratedPackets = new ArrayList<Packet>();
		numOfPacketList = new ArrayList<Integer>();
		serviceTimeForEachPacket = new ArrayList<Integer>();
		arrivalRate = new ArrayList<Integer>();
		arrivalTime = new ArrayList<Integer>();
		totalNumOfPackets = 0;
		numOfpacketType = 0;

		this.arrivalRateVariable = arrivalRateVariable;

		// server = s;
	}

	// Implementation of Poisson distribution
	public static int getPoissonRandom(double mean) {
		Random r = new Random();
		double L = Math.exp(-mean);
		int k = 0;
		double p = 1.0;
		do {
			p = p * r.nextDouble();
			k++;
		} while (p > L);
		return k - 1;
	}

	// Store Number of packets for each type
	public void storeNumberofPacket(ArrayList<Integer> numberOfPacketsType) {
		// total number of packets, and for each type
		for (int i = 0; i < numberOfPacketsType.size(); ++i) {
			numOfPacketList.add(numberOfPacketsType.get(i));
			totalNumOfPackets += numberOfPacketsType.get(i);
		}
		// check the number of different types
		numOfpacketType = numberOfPacketsType.size();
	}

	// Store the service time for each packet type
	public void storeServiceTimeForPacket(ArrayList<Integer> packetServiceTime) {

		for (int i = 0; i < packetServiceTime.size(); ++i)
			serviceTimeForEachPacket.add(packetServiceTime.get(i));
	}

	// The rate at which the following packet will arrive to the network
	public void storeArrivalRate() {
		int rate;

		for (int i = 0; i < totalNumOfPackets; i++) {
			rate = getPoissonRandom(arrivalRateVariable);

			arrivalRate.add(rate);

		}

	}

	// Calculate at which time the packet will arrive to the system
	public void storeArrivalTime() {
		int time = 0;

		for (int i = 0; i < totalNumOfPackets; i++) {
			time = time + arrivalRate.get(i);
			arrivalTime.add(time);
		}
	}

	// Randomly generate packets from source to destination
	public void storeGenaratedPackets() {
		int destination;
		int source;

		int j = 0;
		int i = 0;
		int count = 0;

		int idcounter = 0;

		// For each type generate the list of packets and add it to the generatedPacket
		// (List)
		while (j < numOfpacketType) {
			while (count < numOfPacketList.get(j)) {

				Random rand = new Random();
				Random rand2 = new Random();

				int randomNum = rand.nextInt((general.numberOfNodes - 1) + 1);
				int randomNum2 = rand2.nextInt((general.numberOfNodes - 1) + 1);

				destination = randomNum;
				source = randomNum2;
				while (source == destination) {
					randomNum = rand.nextInt((general.numberOfNodes - 1) + 1);
					destination = randomNum;
				}

				Packet p = new Packet(j + 1, destination, source, 0, serviceTimeForEachPacket.get(j), 2 * j,
						idcounter++);

				Packet p2 = new Packet(j + 1, destination, source, 0, serviceTimeForEachPacket.get(j), 2 * j,
						idcounter++);

				Packet p3 = new Packet(j + 1, destination, source, 0, serviceTimeForEachPacket.get(j), 2 * j,
						idcounter++);

				System.out.println(
						"Packet id" + (idcounter - 1) + ", source : " + source + " destination : " + destination);

				genaratedPackets.add(p);
				genaratedPackets.add(p2);
				genaratedPackets.add(p3);

				count += 3;
			}

			j++;
			count = 0;

		}

		// Swap any packets randomly
		for (i = 0; i < totalNumOfPackets / 2; i++) {
			for (j = totalNumOfPackets / 2; j < totalNumOfPackets; j++) {

				Random rand = new Random();

				int randIndex = rand.nextInt(totalNumOfPackets);

				Packet temp = genaratedPackets.get(i);
				Packet temp2 = genaratedPackets.get(randIndex);

				genaratedPackets.remove(i);
				genaratedPackets.add(i, temp2);

				genaratedPackets.remove(randIndex);
				genaratedPackets.add(randIndex, temp);

			}
		}
		// Assign the arrival time to the new list of packets
		for (i = 0; i < totalNumOfPackets; i++) {

			genaratedPackets.get(i).arrivelTime = arrivalTime.get(i);

		}

		// PRINT the list of the new generated packets
		System.out.println("List of Generated");
		for (i = 0; i < totalNumOfPackets; ++i) {
			System.out.print("ID " + genaratedPackets.get(i).id);
			System.out.print(", Type " + genaratedPackets.get(i).type);
			System.out.print(", arrival time " + genaratedPackets.get(i).arrivelTime);
			System.out.print(", service time " + genaratedPackets.get(i).serviceTime);
			System.out.print(", destination " + genaratedPackets.get(i).destination);
			System.out.println(", source " + genaratedPackets.get(i).source);
		}

	}

	// Check that when the packet arrives, there is a path between source and
	// destination
	// It might not be always the case as one approach might be bad in source
	// utilization...
	public void CheckPacketNetworkAcceptance(int time, Server server) {
		Random rand = new Random();

		int trial = 0;

		for (int i = 0; i < totalNumOfPackets; ++i) {
			if (genaratedPackets.get(i).arrivelTime == time) {
				//As long as there is no path from source to destination keep trying (limited to 50 times)
				while (!server.CheckConnected(general.numberOfNodes, genaratedPackets.get(i).source,
						genaratedPackets.get(i).destination, general.nodesConnections)) {
					genaratedPackets.get(i).source = rand.nextInt(general.numberOfNodes);
					genaratedPackets.get(i).destination = rand.nextInt(general.numberOfNodes);
					if (trial == 50) {
						genaratedPackets.get(i).arrivelTime = (genaratedPackets.get(i).arrivelTime + 1)%1000000;
					}
					trial++;
				}
			}
		}

	}

	// RUN the Generator class main functions
	// This function mainly used for storing the objects
	public void Run(ArrayList<Integer> numberOfPacketsType, ArrayList<Integer> packetServiceTime) {

		storeNumberofPacket(numberOfPacketsType);
		storeServiceTimeForPacket(packetServiceTime);

		storeArrivalRate();
		storeArrivalTime();
		storeGenaratedPackets();

	}

}
