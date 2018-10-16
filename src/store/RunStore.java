package store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import carApproach.CarServer;
import common.DynamicAllocation;
import common.GeneralInfo;
import common.Generator;

public class RunStore {

	int numberOfNodes;
	int bufferSize;
	ArrayList<Integer> packetServiceTime;
	ArrayList<Integer> numberOfPacketsType; // for packet type 0 will have value of [0]

	int arrivalRatevariable;

	String NetworkFile = "";
	String PacketFile = "";

	String NodesMovementFile = "";

	public void RUN() {

		try {

			GeneralInfo g = new GeneralInfo();
			g.Run(numberOfNodes, bufferSize);

			GeneralInfo g2 = new GeneralInfo();
			g2.Run(numberOfNodes, bufferSize);

			Generator generator = new Generator(g2, arrivalRatevariable);
			generator.Run(numberOfPacketsType, packetServiceTime);
			generator.arrivalRateVariable = arrivalRatevariable;

			DynamicAllocation dynamic = new DynamicAllocation(g2, generator);
			dynamic.GenerateInitialLocation();
			dynamic.GenerateMovement();

			// Trial to make sure all packets arrive to the system successfully
			/***/
			StoreObject store = new StoreObject();
			store.g = g2;
			store.generator = generator;
			CarServer server = new CarServer(store.g);
			store.GenerateNodes(server);
			/*
			 * dynamic.AssignInitialLocations(); dynamic.UpdateNetworkConnections();
			 */

			int cnt = 0;
			dynamic.AssignInitialLocations();
			dynamic.UpdateNetworkConnections();
			while (cnt <= 1000000) {
				dynamic.UpdateNodesLocations(cnt);
				dynamic.UpdateNetworkConnections();

				generator.CheckPacketNetworkAcceptance(cnt, server);

				cnt++;
			}

			/***/

			generator.general = g;

			FileOutputStream f = new FileOutputStream(new File(NetworkFile));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(g);

			o.close();
			f.close();

			f = new FileOutputStream(new File(PacketFile));
			o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(generator);

			o.close();
			f.close();

			f = new FileOutputStream(new File(NodesMovementFile));
			o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(dynamic);

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}

	}

	public void BufferSizeTest(String NetworkFile, String PacketFile, String NodesMovementFile, int numberOfNodes,
			int bufferSize, ArrayList<Integer> packetServiceTime, ArrayList<Integer> numberOfPacketsType,
			int arrivalRatevariable) {
		this.NetworkFile = NetworkFile;
		this.PacketFile = PacketFile;
		this.NodesMovementFile = NodesMovementFile;
		this.numberOfNodes = numberOfNodes;
		this.bufferSize = bufferSize;
		this.packetServiceTime = packetServiceTime;
		this.numberOfPacketsType = numberOfPacketsType;
		this.arrivalRatevariable = arrivalRatevariable;

	}

	public void NumberOfNodesTest(String NetworkFile, String PacketFile, String NodesMovementFile, int numberOfNodes,
			int bufferSize, ArrayList<Integer> packetServiceTime, ArrayList<Integer> numberOfPacketsType,
			int arrivalRatevariable) {
		this.NetworkFile = NetworkFile;
		this.PacketFile = PacketFile;
		this.NodesMovementFile = NodesMovementFile;
		this.numberOfNodes = numberOfNodes;
		this.bufferSize = bufferSize;
		this.packetServiceTime = packetServiceTime;
		this.numberOfPacketsType = numberOfPacketsType;
		this.arrivalRatevariable = arrivalRatevariable;
	}

	public void NumberOfPacketsTest(String NetworkFile, String PacketFile, String NodesMovementFile, int numberOfNodes,
			int bufferSize, ArrayList<Integer> packetServiceTime, ArrayList<Integer> numberOfPacketsType,
			int arrivalRatevariable) {
		this.NetworkFile = NetworkFile;
		this.PacketFile = PacketFile;
		this.NodesMovementFile = NodesMovementFile;
		this.numberOfNodes = numberOfNodes;
		this.bufferSize = bufferSize;
		this.packetServiceTime = packetServiceTime;
		this.numberOfPacketsType = numberOfPacketsType;
		this.arrivalRatevariable = arrivalRatevariable;
	}

	public void ArrivalRateTest(String NetworkFile, String PacketFile, String NodesMovementFile, int numberOfNodes,
			int bufferSize, ArrayList<Integer> packetServiceTime, ArrayList<Integer> numberOfPacketsType,
			int arrivalRatevariable) {
		this.NetworkFile = NetworkFile;
		this.PacketFile = PacketFile;
		this.NodesMovementFile = NodesMovementFile;
		this.numberOfNodes = numberOfNodes;
		this.bufferSize = bufferSize;
		this.packetServiceTime = packetServiceTime;
		this.numberOfPacketsType = numberOfPacketsType;
		this.arrivalRatevariable = arrivalRatevariable;
	}

	public void DifferentPacketsTest(String NetworkFile, String PacketFile, String NodesMovementFile, int numberOfNodes,
			int bufferSize, ArrayList<Integer> packetServiceTime, ArrayList<Integer> numberOfPacketsType,
			int arrivalRatevariable) {
		this.NetworkFile = NetworkFile;
		this.PacketFile = PacketFile;
		this.NodesMovementFile = NodesMovementFile;
		this.numberOfNodes = numberOfNodes;
		this.bufferSize = bufferSize;
		this.packetServiceTime = packetServiceTime;
		this.numberOfPacketsType = numberOfPacketsType;
		this.arrivalRatevariable = arrivalRatevariable;
	}

}
