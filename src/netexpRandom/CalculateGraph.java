package netexpRandom;

import java.util.ArrayList;

public class CalculateGraph {

	ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> allRoutes; // All routes between all nodes (limited to 20)

	// Calculate all shortest paths from destination to the neighbors of the source
	// and add the source at the end
	public void Cal(boolean[] check, boolean[] check2, int n, int[][] conn, int realsource, int source, int destination,
			int[] path, int cnt) {

		for (int i = 0; i < n; ++i) {
			if (allRoutes.get(realsource).get(destination).size() >= 20)
				break;
			if (check[i] == true || conn[source][i] == 0)
				continue;
			ArrayList<Integer> p = GetPath(n, check, i, destination, conn, realsource);
			// If there is a path append real source to the end
			if (p != null && p.get(p.size() - 1) == i) {
				p.add(realsource);
				allRoutes.get(realsource).get(destination).add(p);
			}

		}

	}

	// Get path shortest path from source to destination using Dijkstra algorithm
	public ArrayList<Integer> GetPath(int n, boolean[] check, int source, int destination, int[][] connections,
			int realsource) {
		int[] prev = new int[n];
		int[] dist = new int[n];
		for (int i = 0; i < n; ++i) {
			dist[i] = 2000000000;
			prev[i] = -1;
		}

		dist[source] = 0;

		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(source);
		while (arr.size() > 0) {
			int u = arr.get(0);
			arr.remove(0);

			for (int j = 0; j < n; ++j) {
				if (check[j] == false && connections[u][j] == 1 && dist[j] > dist[u] + 1) {
					prev[j] = u;
					dist[j] = dist[u] + 1;
					arr.add(j);
				}
			}
		}

		// check if there source and destination nodes are connected
		if (dist[destination] == 2000000000 && prev[destination] == -1)
			return null;

		ArrayList<Integer> path = new ArrayList<Integer>();
		int cnt = destination;
		// Restructure the path to fit the Node implementation
		for (int i = 0; i < n; ++i) {

			path.add(cnt);
			if (cnt == source || cnt > n)
				break;
			cnt = prev[cnt];
		}

		return path;
	}

	// Calculate all paths for all nodes to all nodes
	public void StartCal(int[][] conn, int n, boolean[] check) {

		allRoutes = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
		boolean[] checkoo = new boolean[n];

		for (int i = 0; i < n; ++i) {
			// Paths from source node to all other nodes (using shortest path to neighbors
			// as factor to determine
			ArrayList<ArrayList<ArrayList<Integer>>> ii = new ArrayList<ArrayList<ArrayList<Integer>>>();
			allRoutes.add(ii);
			for (int j = 0; j < n; ++j) {
				// paths to other nodes
				ArrayList<ArrayList<Integer>> jj = new ArrayList<ArrayList<Integer>>();
				for (int k = 0; k < n; ++k) {
					checkoo[k] = check[k];
				}
				allRoutes.get(i).add(jj);
				// no need for a path to the same node, or failed node
				if (i == j || checkoo[i] || checkoo[j])
					continue;
				int[] path = new int[n];
				// the path shouldn't go through the source node
				checkoo[i] = true;
				Cal(checkoo, check, n, conn, i, i, j, path, 0);
				checkoo[i] = false;
			}

		}
		// PRINT the number of all paths (usually the number of not failed nodes
		// System.out.println(allRoutes.size());

	}

	public ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> GetPaths(int[][] conn,
			boolean[] checked, int n, boolean calculateOrNot) {

			StartCal(conn, n, checked);
		
		return allRoutes;
	}

}