package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Server implements Serializable{

	// Calculate all possible paths for all nodes, depending on the changes occurred
		// to the system
		public ArrayList<ArrayList<Integer>> CalculatePossiblePath(int timer, int n, int source, int destination,
				int[][] connections) {
			
			return null;
		}
	
	//Check if there is a connection between source and destination or not
	public boolean CheckConnected(int n, int source, int destination, int[][] connections) {
	
		return true;
	}
	
	//Get a path from source to destination
	public ArrayList<Integer> GetPath(int n, int source, int destination, int[][] connections, int size) {
		
		return null;
	}

}
