package randomApproach;

import java.util.ArrayList;
import java.util.Random;

import common.*;
import netexpMinDistant.CalculateGraph;

@SuppressWarnings("serial")
public class RandomServer extends entity.Server {

	GeneralInfo general; // Current info about the system
	CalculateGraph c = new CalculateGraph(); // Calculate all possible paths for all nodes
												// the node
	ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> previousCalculatedPaths;

	boolean isConnected = false;

	public RandomServer(GeneralInfo g) {
		super();
		this.general = g;
	}

	// Calculate all possible paths for all nodes, depending on the changes occurred
	// to the system
	public ArrayList<ArrayList<Integer>> CalculatePossiblePath(int timer, int n, int source, int destination,
			int[][] connections) {

		if (timer % 10 == 0) { // In case of new changes in the system (10 as in dynamicAllocation class)
			boolean[] check = new boolean[n];

			for (int i = 0; i < n; ++i) {

				if (general.allNodes.get(i).notfailure == false) {
					check[i] = true;
				} else
					check[i] = false;
			}

			previousCalculatedPaths = c.GetPaths(connections, check, n, true);
		}

		if (previousCalculatedPaths == null || previousCalculatedPaths.get(source) == null
				|| previousCalculatedPaths.get(source).get(destination) == null
				|| previousCalculatedPaths.get(source).get(destination).size() == 0)
			return null;

		return previousCalculatedPaths.get(source).get(destination);
	}

	public boolean CheckConnected(int n, int source, int destination, int[][] connections, int size) {

		// Will not calculate all possible paths, will ask the Dynamic function to do so
		ArrayList<ArrayList<Integer>> allPaths = CalculatePossiblePath(1, n, source, destination, connections);

		// Check if there is a path from source to destination nodes
		if (allPaths == null || allPaths.size() == 0 || allPaths.get(0).size() == 0)
			return false;
		else
			return true;
	}

	public ArrayList<Integer> GetPath(int n, int source, int destination, int[][] connections, int size) {

		ArrayList<ArrayList<Integer>> allPaths = CalculatePossiblePath(1, n, source, destination, connections);
		// Will not calculate the paths (rely on the last data collected)

		if (allPaths == null)
			return null; // no path from source node to destination node

		// found no possible path from source to destination that fulfill the conditions
		if (allPaths == null || allPaths.size() == 0)
			return null;

		Random rand = new Random();
		int randomNum = rand.nextInt(allPaths.size());

		// System.out.println("Path size : " + possiblePath.get(randomNum).size());
		// Check if the path in the right format or not
		if (allPaths.get(randomNum).get(allPaths.get(randomNum).size() - 1) != source)
			allPaths.get(randomNum).add(source);

		ArrayList<Integer> resultedPath = new ArrayList<Integer>();// to make sure
		// doesn't change
		// the original
		// version

		for (int i = 0; i < allPaths.get(randomNum).size(); ++i)
			resultedPath.add(allPaths.get(randomNum).get(i));
		return resultedPath;// return the path
	}

}
