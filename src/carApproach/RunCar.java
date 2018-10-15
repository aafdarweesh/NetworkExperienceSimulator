package carApproach;

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

public class RunCar {

	String NetworkFile = "";
	String PacketFile = "";
	String NodesMovementFile = "";

	String testResultPath = "";

	@SuppressWarnings("unused")
	public void RUN() {

		try {
			StoreObject store = new StoreObject();

			FileInputStream fi = new FileInputStream(new File(NetworkFile));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			store.g = (GeneralInfo) oi.readObject();

			oi.close();
			fi.close();

			FileInputStream fi2 = new FileInputStream(new File(PacketFile));
			ObjectInputStream oi2 = new ObjectInputStream(fi2);

			// Read objects
			store.generator = (Generator) oi2.readObject();

			oi2.close();
			fi2.close();

			// Dynamic changes
			fi = new FileInputStream(new File(NodesMovementFile));
			oi = new ObjectInputStream(fi);

			DynamicAllocation dy = (DynamicAllocation) oi.readObject();

			fi.close();
			oi.close();

			CarServer server = new CarServer(store.g);

			store.GenerateNodes(server);
			// Dynamic changes
			dy.general = store.g;
			dy.generator = store.generator;

			dy.AssignInitialLocations();

			// dy.UpdateNodesLocations(counter%1000);
			dy.UpdateNetworkConnections();

			int counter = 0;

			int numberPacketsArrived = 0;

			while (counter++ < 1000000) {

				// Dynamic Allocation for the nodes
				dy.UpdateNodesLocations(counter % 1000);
				dy.UpdateNetworkConnections();

				if (store.g.delivered.size() == store.generator.totalNumOfPackets)
					break;

				// System.out.println("=======================" +
				// store.generator.totalNumOfPackets);

				// Check which packet will arrive to the system at the moment
				for (int i = 0; i < store.generator.totalNumOfPackets; ++i) {
					Packet currentPacket = store.generator.genaratedPackets.get(i);

					if (currentPacket.arrivelTime == store.g.timeCounter) {

						int source = currentPacket.source;

						// Check if there is a path from source to destination (eliminate the packet if
						// not)
						if (!server.CheckConnected(store.g.numberOfNodes, source, currentPacket.destination,
								store.g.nodesConnections)) {
							store.generator.totalNumOfPackets--;
							System.out.print("Continue without requesting a path");
							continue;
						}

						// In case buffer is full let packet arrive in the following iteration
						boolean checkIfFullBuffer = store.g.allNodes.get(source).buffer.full();
						if (checkIfFullBuffer == false) {
							store.g.allNodes.get(source).buffer.Push(currentPacket, store.g);
							store.g.addToNodeExpected(currentPacket);
							numberPacketsArrived++;
						} else {
							currentPacket.arrivelTime++;
						}
					}
				}

				// RUN Nodes
				for (int i = 0; i < store.g.allNodes.size(); ++i) {
					store.g.allNodes.get(i).Run();
				}

				store.g.timeCounter++; // change the current time

				// g.printToSystem();
			}
			// store.g.LastInfo();

			// Store measured data about the system
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
