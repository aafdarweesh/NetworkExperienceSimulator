package entity;

import java.io.Serializable;
import java.util.ArrayList;

import common.*;

@SuppressWarnings("serial")
public class PacketQueue implements Serializable{
	public ArrayList<Packet> packetQueue; //Buffer
	public int bufferSize = 10; //Buffer size
	
	
	PacketQueue(int bufferSize) {
		this.packetQueue = new ArrayList<Packet>();
		this.bufferSize = bufferSize;
	}

	//Pop packet from the beginning of the queue
	public Packet Pop(int id) {
		Packet temp;
		temp = this.packetQueue.get(0);
		this.packetQueue.remove(0);
		return temp;
	}

	//Check if the buffer is full or not
	public boolean full() {
		return packetQueue.size() == bufferSize;
	}
	
	//Check if the buffer is empty or not
		public boolean empty() {
			return packetQueue.size() == 0;
		}

	//PRINT the buffer content (all packets in the buffer in details)
	public void print() {
		for (int i = 0; i < packetQueue.size(); i++) {
			System.out.print("ID "+packetQueue.get(i).id);
			System.out.print(", Type "+packetQueue.get(i).type);
			System.out.print(", arrival time "+packetQueue.get(i).arrivelTime);
			System.out.print(", service time "+packetQueue.get(i).serviceTime);
			System.out.print(", destination "+packetQueue.get(i).destination);
			System.out.println(", source "+packetQueue.get(i).source);
			
		}
	}

	
	//Push Packet to the end of the buffer
	public void Push(Packet newPacket, GeneralInfo g) {
		System.out.println("ID "+newPacket.id);
		if(this.full() == true) {
		
			System.out.println("meeee"+this.packetQueue.size());
			g.dropped.add(newPacket);
			g.numberOfDroppedPackets++;
			System.out.println("Dropped");
		}
		else {
			System.out.println("NOT Dropped");
			this.packetQueue.add(newPacket);
		}
	}
	
	
}
