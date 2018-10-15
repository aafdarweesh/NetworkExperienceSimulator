package netexpMinWeight;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import common.DynamicAllocation;
import common.GeneralInfo;
import common.Generator;
import entity.Packet;
import measurement.Measure;
import store.StoreObject;

public class RunNetExp {

	// The sources of the data
	String NetworkFile = "";
	String PacketFile = "";
	String NodesMovementFile = "";

	// The Measurements file
	String testResultPath = "";

	@SuppressWarnings("unused")
	public void RUN() {

		try {

			StoreObject store = new StoreObject();

			FileInputStream fi = new FileInputStream(new File(NetworkFile));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read GeneralInfo object
			store.g = (GeneralInfo) oi.readObject();

			oi.close();
			fi.close();

			fi = new FileInputStream(new File(PacketFile));
			oi = new ObjectInputStream(fi);

			// Read Generator object
			store.generator = (Generator) oi.readObject();

			oi.close();
			fi.close();

			// Dynamic changes
			fi = new FileInputStream(new File(NodesMovementFile));
			oi = new ObjectInputStream(fi);

			DynamicAllocation dy = (DynamicAllocation) oi.readObject();

			fi.close();
			oi.close();

			NetExpServer server = new NetExpServer(store.g); // Server

			store.GenerateNodes(server); // Generate the system nodes
			// Dynamic changes
			dy.general = store.g;
			dy.generator = store.generator;

			dy.AssignInitialLocations();
			dy.UpdateNetworkConnections();

			server.CalculatePossiblePath(0, store.g.numberOfNodes, 0, 0, store.g.nodesConnections);

			int counter = 0;

			int numberPacketsArrived = 0;

			while (counter++ < 1000000) {// 1000000

				// Dynamic changes
				dy.UpdateNodesLocations(counter);
				dy.UpdateNetworkConnections();
				// dy.UpdateNetworkPaths(counter, server);
				server.CalculatePossiblePath(counter, store.g.numberOfNodes, 0, 0, store.g.nodesConnections);

				// if all packet have been delivered
				if (store.g.delivered.size() == store.generator.totalNumOfPackets)
					break;

				// System.out.println("=======================");

				// Go through the list of packets to add them to the system
				for (int i = 0; i < store.generator.totalNumOfPackets; ++i) {
					Packet currentPacket = store.generator.genaratedPackets.get(i);
					if (currentPacket.arrivelTime == store.g.timeCounter) {

						int source = currentPacket.source;

						// Check if there is a path from source to destination (eliminate the packet if
						// not)
						if (!server.CheckConnected(store.g.numberOfNodes, source, currentPacket.destination,
								store.g.nodesConnections, 0)) {
							store.generator.totalNumOfPackets--;
							System.out.print("Continue without requesting a path");
							continue;
						}

						// Check if the buffer of the source node is full or not
						boolean checkIfFullBuffer = store.g.allNodes.get(source).buffer.full();

						// Check if there is a space and there is a path between source and destination
						if (checkIfFullBuffer == false) {
							store.g.allNodes.get(source).buffer.Push(currentPacket, store.g);
							store.g.addToNodeExpected(currentPacket); // add it to the expected in the destination node
							numberPacketsArrived++;
						} else {
							currentPacket.arrivelTime++;
						}
					}
				}
				// RUN all nodes
				for (int i = 0; i < store.g.allNodes.size(); ++i) {
					store.g.allNodes.get(i).Run();
				}

				store.g.timeCounter++; // change the current time

				// Update the server Data
				server.UpdateServer();

				// g.printToSystem();
			}
			// store.g.LastInfo();

			// Measure the data collected for the system
			Measure m = new Measure(store.g, store.generator, counter, numberPacketsArrived, testResultPath);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void BufferSizeTest(String netPath, String packetPath, String NodesMovementFile, String testResultPath) {
		NetworkFile = netPath;
		PacketFile = packetPath;
		this.NodesMovementFile = NodesMovementFile;
		this.testResultPath = testResultPath;
	}

	public void NumberOfNodesTest(String netPath, String packetPath, String NodesMovementFile, String testResultPath) {
		NetworkFile = netPath;
		PacketFile = packetPath;
		this.NodesMovementFile = NodesMovementFile;
		this.testResultPath = testResultPath;
	}

	public void NumberOfPacketsTest(String netPath, String packetPath, String NodesMovementFile,
			String testResultPath) {
		NetworkFile = netPath;
		PacketFile = packetPath;
		this.NodesMovementFile = NodesMovementFile;
		this.testResultPath = testResultPath;
	}

	public void ArrivalRateTest(String netPath, String packetPath, String NodesMovementFile, String testResultPath) {
		NetworkFile = netPath;
		PacketFile = packetPath;
		this.NodesMovementFile = NodesMovementFile;
		this.testResultPath = testResultPath;
	}

	public void DifferentPacketsTest(String netPath, String packetPath, String NodesMovementFile,
			String testResultPath) {
		NetworkFile = netPath;
		PacketFile = packetPath;
		this.NodesMovementFile = NodesMovementFile;
		this.testResultPath = testResultPath;
	}

}
