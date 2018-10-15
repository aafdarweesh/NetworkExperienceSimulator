package carApproach;

import java.util.ArrayList;

import common.GeneralInfo;

@SuppressWarnings("serial")
public class CarServer extends entity.Server{

	GeneralInfo general;
	boolean isConnected = false;
	
	
	public CarServer(GeneralInfo g){
		this.general = g;
	}
	
	//Check if there is a path from source to destination
	public boolean CheckConnected(int n, int source, int destination, int[][] connections) {
		this.GetPath(n, source, destination, connections, 0);	
		return isConnected;
	}
	
	//Get a path, according to the algorithm (approach) used
	public ArrayList<Integer> GetPath(int n, int source, int destination, int[][] connections, int size){
		//Shortest path using Dijkstra algorithm
		
		int[] prev = new int[n];
		int[] dist = new int[n];
		boolean[] check = new boolean[n];
		for(int i = 0; i < n; ++i){
			dist[i] = 2000000000;
			prev[i] = -1; 
			if(general.allNodes.get(i).notfailure == false) check[i] = true;
			else check[i] = false;
		}

		dist[source] = 0;
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(source);
		while(arr.size() > 0){
			int u = arr.get(0);
			arr.remove(0);
			
			for(int j = 0; j < n; ++j){
				if(check[j] == false && connections[u][j] == 1 && dist[j] > dist[u] + 1) {
					prev[j] = u;
					dist[j] = dist[u] + 1;
					arr.add(j);
				}
			}
		}
		
		
		//check if there source and destination nodes are connected
		if(dist[destination] != 2000000000 || prev[destination] != -1) isConnected = true;
		else {
			isConnected = false;
			return null;
		}

		ArrayList<Integer> path = new ArrayList<Integer>();
		int cnt = destination;
		
		System.out.print("path from  "+ source + " to "+destination);
	
		for(int i = 0; i < n; ++i){
			System.out.print(" "+ cnt);
			
			path.add(cnt);
			if(cnt == source || cnt > n) break;
			cnt = prev[cnt];
		}
		
		System.out.print("\n");
		
		return path;
	}
	

}
