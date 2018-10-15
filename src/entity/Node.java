package entity;

import java.io.Serializable;
import java.util.ArrayList;

import common.GeneralInfo;

@SuppressWarnings("serial")
public class Node implements Serializable {
	public int id;// Unique to identify the node
	public PacketQueue buffer; // Buffer
	public Packet currentPacket; // currentPacket being diagnosed
	public boolean notfailure; // Check if there is a failure in the node or not
	public boolean status; // Define the status of the node

	public Server server; // Cloud server

	public GeneralInfo generalInfo; // Generalinfo shared
	public ArrayList<Packet> expectedPackets; // expected packets list

	// The coordinates of the node (will be used for the Dynamic network)
	public int x;
	public int y;

	// Constructor
	public Node(int newid, Server s, GeneralInfo g, int bufferSize) {
		this.id = newid;
		this.buffer = new PacketQueue(bufferSize);
		this.notfailure = true;
		this.status = true;
		this.server = s;
		this.generalInfo = g;
		this.expectedPackets = new ArrayList<Packet>();
	}

	public void Run() {

		if (status == true) { // The node is not executing any packet at the moment

			if (buffer.empty() == false) { // check if the buffer is not empty
				this.currentPacket = buffer.Pop(this.id);

				if (this.currentPacket.destination == this.id) { // Check if the packet is for this node ?

					this.status = false; // change status to busy

					this.currentPacket.startServiceTime = generalInfo.timeCounter;
					boolean checkDeliveredBefore = true;
					// remove the packet from the expected list
					for (int i = 0; i < this.expectedPackets.size(); ++i) {
						if (this.currentPacket.id == expectedPackets.get(i).id) {
							expectedPackets.remove(i);
							checkDeliveredBefore = false;
						}
					}

					// Check if the packet was delivered before or not
					if (checkDeliveredBefore == true) {
						generalInfo.packetDeliveredMoreThanOneTime++;
						this.status = true;
					}

					// Check if the packet has a path or not
				} else if (this.currentPacket.path.size() > 0
						&& this.currentPacket.path.get(this.currentPacket.path.size() - 1) == this.id) {

					this.currentPacket.path.remove(this.currentPacket.path.size() - 1); // remove current node from the
																						// path

					this.status = true;
					// Check which node is the following node in the path to the destination
					int nextNodeId = this.currentPacket.path.get(this.currentPacket.path.size() - 1);

					// Check the following node status, and send it
					if (generalInfo.CheckNodeStatus(nextNodeId) == true) {
						currentPacket.distanceTraveled++;
						generalInfo.allNodes.get(nextNodeId).buffer.Push(currentPacket, generalInfo);

					} else { // Find a new path from source to destination for the current packet

						// Ask the server for a path
						ArrayList<Integer> newPath = server.GetPath(generalInfo.allNodes.size(), this.id,
								this.currentPacket.destination, generalInfo.nodesConnections, this.currentPacket.size);
						// if there is a path
						if (newPath != null && newPath.size() > 0 && newPath.get(newPath.size() - 1) == this.id) {

							newPath.remove(newPath.size() - 1); // remove current node from the path

							this.currentPacket.path = newPath;
							nextNodeId = this.currentPacket.path.get(this.currentPacket.path.size() - 1);
							currentPacket.distanceTraveled++;
							generalInfo.allNodes.get(nextNodeId).buffer.Push(currentPacket, generalInfo);

						} else { // There is no path to the destination

							currentPacket.distanceTraveled++;

							// Check how many times this packet has been pushed back into the end of buffers
							if (currentPacket.marked >= 5) {
								generalInfo.dropped.add(currentPacket);
								generalInfo.numberOfDroppedPackets++;
							} else { // otherwise push back to the end of the buffer
								this.buffer.Push(currentPacket, generalInfo);
								currentPacket.marked++;
							}

						}

					}

					// If the current packet has no path to the destination
				} else {
					this.status = true;
					// Request a path from the server
					ArrayList<Integer> newPath = server.GetPath(generalInfo.allNodes.size(), this.id,
							this.currentPacket.destination, generalInfo.nodesConnections, this.currentPacket.size);

					// Check if there is a path or not (Send to the following node in the path)
					if (newPath != null && newPath.size() > 0 && newPath.get(newPath.size() - 1) == this.id) {
						this.currentPacket.path = newPath;
						int nextNodeId = this.currentPacket.path.get(this.currentPacket.path.size() - 1);

						currentPacket.distanceTraveled++;
						generalInfo.allNodes.get(nextNodeId).buffer.Push(currentPacket, generalInfo);

					} else { // No path to the destination

						currentPacket.distanceTraveled++;
						// Check how many times this packet was pushed to the end of buffers
						if (currentPacket.marked == 5) {
							generalInfo.dropped.add(currentPacket);
							generalInfo.numberOfDroppedPackets++;
						} else { // Push back to the end of the buffer
							this.buffer.Push(currentPacket, generalInfo);
							currentPacket.marked++;
						}
					}

				}
			}
		}
		// The node currently executing a packet
		else {

			int executingTime = generalInfo.timeCounter - this.currentPacket.startServiceTime;

			if (executingTime >= this.currentPacket.serviceTime - 1) {

				generalInfo.delivered.add(this.currentPacket);

				this.status = true;
			}

		}

		for (int i = 0; i < expectedPackets.size(); ++i) {

			// In case no path between source and destination don't expect anything
			if (!server.CheckConnected(generalInfo.numberOfNodes, expectedPackets.get(i).source,
					expectedPackets.get(i).destination, generalInfo.nodesConnections))
				continue;

			// Check if the expected packet exceeded the limited time
			if (generalInfo.timeCounter - expectedPackets.get(i).arrivelTime >= generalInfo.limitedTimeForPacket) {
				expectedPackets.get(i).arrivelTime = generalInfo.timeCounter;

				generalInfo.rerequestedPackets++;

				generalInfo.allNodes.get(expectedPackets.get(i).source).RequestedPacket(expectedPackets.get(i));
			}
		}

	}

	// Request a packet from the current node to the destination (usually used be
	// the destination node, expecting a packet)
	public void RequestedPacket(Packet newPacket) {
		Packet p = new Packet(newPacket.type, newPacket.destination, newPacket.source, generalInfo.timeCounter,
				newPacket.serviceTime, newPacket.size, newPacket.id);

		this.buffer.Push(p, generalInfo);
	}

	// PRINT expected packet content
	public void printExpectedPacketsContent() {
		for (int i = 0; i < expectedPackets.size(); ++i) {
			System.out.print(" Packets ID " + expectedPackets.get(i).id);
			System.out.print(", Packets arrivel Time " + expectedPackets.get(i).arrivelTime);
			System.out.print(", Packets service Time " + expectedPackets.get(i).serviceTime);
			System.out.print(", Packets destination " + expectedPackets.get(i).destination);
			System.out.print(", Packets source  " + expectedPackets.get(i).source);

		}
	}
}
