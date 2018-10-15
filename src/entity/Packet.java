package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Packet implements Serializable{
	public int type;
	public int destination;
	public int source;
	public ArrayList<Integer> path;
	public int arrivelTime;
	public int serviceTime;
	public int startServiceTime; 
	public int distanceTraveled; 
	public int size; 
	public int id; 
	
	
	public int marked; //count how many times it is pushed back to the buffer
	
	//Constructor, initialize the packet info
	public Packet(int ty, int des, int so, int arrT, int sT, int size, int id){
		this.type = ty;
		this.destination = des;
		this.source = so;
		this.arrivelTime = arrT;
		this.serviceTime = sT;
		this.startServiceTime = 0;
		this.distanceTraveled = 0;
		this.size = size;
		this.id = id;
		this.marked = 0;
		path = new ArrayList<Integer>();
	}
	
	
}


