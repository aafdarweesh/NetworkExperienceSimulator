package netexpRandom;

import java.util.ArrayList;
import java.util.Random;

import common.GeneralInfo;
import netexpMinDistant.CalculateGraph;

@SuppressWarnings("serial")
public class NetExpServer extends entity.Server {
	GeneralInfo general; // Current info about the system
	CalculateGraph c = new CalculateGraph(); // Calculate all possible paths for all nodes
	int pathWeightCounter = 0; // count the time to measure the weight of the node at that moment
	ArrayList<int[]> timeNodesWeight = new ArrayList<int[]>(); // will be used as for calculating the expected weight on
																// the node
	ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> previousCalculatedPaths;

	boolean isConnected = false;

	public NetExpServer(GeneralInfo g) {
		super();
		this.general = g;

		// System.out.println("\nServerOurApproach\nNumber of Nodes : " +
		// g.numberOfNodes);

		for (int i = 0; i < 100; ++i) {
			int[] newMeasure = new int[g.numberOfNodes];
			timeNodesWeight.add(newMeasure);
			for (int j = 0; j < general.numberOfNodes; ++j) {
				timeNodesWeight.get(i)[j] = 0;
			}
		}
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

		ArrayList<ArrayList<Integer>> possiblePath = new ArrayList<ArrayList<Integer>>();// possible paths to consider
																							// having the condition of
																							// Minimum distance

		if (allPaths == null)
			return null; // no path from source node to destination node

		ArrayList<Integer> loadList = new ArrayList<Integer>();// the load of each path
		boolean checkPath = false;

		for (int i = 0; i < allPaths.size(); ++i) {// calculate the paths
			int pathvalue = 0; // the value of the path (weight)
			checkPath = true; // will consider the path

			// Calculate the weight of the path (estimating the weight in future)
			for (int j = 0; j < allPaths.get(i).size(); ++j) {
				// the current estimated weight of that path
				pathvalue += timeNodesWeight.get((pathWeightCounter + j) % 100)[allPaths.get(i).get(j)];

				if (pathvalue > general.limitedTimeForPacket + size + 5) // check if the packet will be re-requests with
																			// a margin of 5
					checkPath = false;
			}

			if (checkPath) {// if the path will be dropped or not
				// add the path to possible paths
				possiblePath.add(allPaths.get(i));
				loadList.add(pathvalue);
			}

		}

		// found no possible path from source to destination that fulfill the conditions
		if (possiblePath.size() == 0)
			return null;

		Random rand = new Random();
		int randomNum = rand.nextInt(possiblePath.size());

		// Add the new estimation to the prediction table
		for (int j = 0; j < possiblePath.get(randomNum).size(); ++j) {
			timeNodesWeight.get((pathWeightCounter + j) % 100)[possiblePath.get(randomNum).get(j)]++;
			if (j == possiblePath.get(randomNum).size() - 1)// I think it is wrong : possiblePath.get(randomNum).size()
															// - 1
				timeNodesWeight.get((pathWeightCounter + j) % 100)[possiblePath.get(randomNum).get(0)] += 10;
		}

		// System.out.println("Path size : " + possiblePath.get(randomNum).size());
		// Check if the path in the right format or not
		if (possiblePath.get(randomNum).get(possiblePath.get(randomNum).size() - 1) != source)
			possiblePath.get(randomNum).add(source);

		ArrayList<Integer> resultedPath = new ArrayList<Integer>();// to make sure
		// doesn't change
		// the original
		// version

		for (int i = 0; i < possiblePath.get(randomNum).size(); ++i)
			resultedPath.add(possiblePath.get(randomNum).get(i));

		return resultedPath;// return the path
	}

	// Update the prediction weight table (and erase all previous not needed data)
	public void UpdateServer() {
		int sum = 0;
		// delete the data for the past moment
		for (int i = 0; i < general.numberOfNodes; ++i) {
			sum += timeNodesWeight.get(pathWeightCounter)[i];
			timeNodesWeight.get(pathWeightCounter)[i] = 0;
		}
		// change the value of the pointer (path weight pointer)
		System.out.println("Number in that column : " + sum); // the network load at the past moment
		pathWeightCounter = (pathWeightCounter + 1) % 100;
	}

}
