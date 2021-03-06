package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class DynamicAllocation implements Serializable {

	public GeneralInfo general; // Pointer to Generalinfo variable
	public Generator generator; // Pointer to Generator variable

	public ArrayList<ArrayList<Integer>> xMovement = new ArrayList<>(); // List of xMovements for all nodes
	public ArrayList<ArrayList<Integer>> yMovement = new ArrayList<>(); // List of yMovements for all nodes

	public ArrayList<Integer> xInitial = new ArrayList<>();
	public ArrayList<Integer> yInitial = new ArrayList<>();

	// Constructor, Update the nodes (from General and Generator class)
	public DynamicAllocation(GeneralInfo general, Generator generator) {
		this.general = general;
		this.generator = generator;
	}

	// Generates new connections each iteration,
	// will rely on the garbage collector to deal with the previous data
	public void UpdateNetworkConnections() {

		// System.out.println("UpdateNetworkConnections");

		int n = general.numberOfNodes;
		general.nodesConnections = new int[n][n];

		double distant = 0;
		for (int i = 0; i < n; ++i) {
			boolean anyConnection = false;
			for (int j = 0; j < n; ++j) {
				distant = Math.pow(Math.abs(general.allNodes.get(i).x - general.allNodes.get(j).x), 2);
				distant += Math.pow(Math.abs(general.allNodes.get(i).y - general.allNodes.get(j).y), 2);

				distant = Math.sqrt(distant);

				if (distant <= 10) {
					anyConnection = true;
					general.nodesConnections[i][j] = general.nodesConnections[j][i] = 1;
				} else
					general.nodesConnections[i][j] = general.nodesConnections[j][i] = 0;
			}

			if (!anyConnection)
				general.allNodes.get(i).notfailure = false;
		}
	}

	/*
	 * //Updates the network paths (used in the new proposed approach) public void
	 * UpdateNetworkPaths(int timeCounter, Server server) {
	 * server.CalculatePossiblePath(timeCounter, general.numberOfNodes, 0, 0,
	 * general.nodesConnections); }
	 */
	// Updates the nodes locations according to the movement file
	public void UpdateNodesLocations(int time) {
		int n = general.numberOfNodes;// general.allNodes.size();

		for (int i = 0; i < n; ++i) {

			if (time % 1000 == 0) { // So it doesn't diverge
				//Negate all movements to make sure that the boundary condition is always satisfied
				for (int j = 0; j < 1000; ++j) {
					xMovement.get(i).set(j, xMovement.get(i).get(j) * -1);
					yMovement.get(i).set(j, yMovement.get(i).get(j) * -1);
				}
			}

			general.allNodes.get(i).x += xMovement.get(i).get(time % 1000);
			general.allNodes.get(i).y += yMovement.get(i).get(time % 1000);
		}
	}

	// Generates the movements of the nodes (Generate every 10 iterations)
	// 30% negative, 30% no movement, 30% positive movement
	public void GenerateMovement() {
		Random rand = new Random();

		ArrayList<Integer> xLocations;
		ArrayList<Integer> yLocations;

		// These two arrays to make sure that during the movements the nodes still under
		// coverage
		ArrayList<Integer> allNodesXLocation = new ArrayList<Integer>();
		ArrayList<Integer> allNodesYLocation = new ArrayList<Integer>();

		for (int i = 0; i < general.numberOfNodes; ++i) {
			allNodesXLocation.add(xInitial.get(i));
			allNodesYLocation.add(yInitial.get(i));
		}

		for (int i = 0; i < general.numberOfNodes; ++i) {

			xLocations = new ArrayList<Integer>(1000);
			yLocations = new ArrayList<Integer>(1000);

			int cnt = 0;

			for (int j = 0; j < 1000; ++j) {
				int randomNumX = rand.nextInt(9);
				int randomNumY = rand.nextInt(9);

				// change the locations every 10 iterations
				if (cnt % 10 == 0) {
					if (randomNumX < 3)
						xLocations.add(-1);
					else if (randomNumX < 6)
						xLocations.add(0);
					else
						xLocations.add(1);

					if (randomNumY < 3)
						yLocations.add(-1);
					else if (randomNumY < 6)
						yLocations.add(0);
					else
						yLocations.add(1);

				} else {
					xLocations.add(0);
					yLocations.add(0);
				}

				// update the location with the current value
				allNodesXLocation.set(i, allNodesXLocation.get(i) + xLocations.get(j));
				allNodesYLocation.set(i, allNodesYLocation.get(i) + yLocations.get(j));

				// Update X location if the node is out of the boundary
				if (allNodesXLocation.get(i) > general.numberOfNodes * 2) {
					allNodesXLocation.set(i, allNodesXLocation.get(i) - 1);
					xLocations.set(j, -1);
				} else if (allNodesXLocation.get(i) < 0) {
					allNodesXLocation.set(i, allNodesXLocation.get(i) + 1);
					xLocations.set(j, 1);
				}
				// Update Y location if the node is out of the boundary
				if (allNodesYLocation.get(i) > general.numberOfNodes * 2) {
					allNodesYLocation.set(i, allNodesYLocation.get(i) - 1);
					yLocations.set(j, -1);
				} else if (allNodesYLocation.get(i) < 0) {
					allNodesYLocation.set(i, allNodesYLocation.get(i) + 1);
					yLocations.set(j, 1);
				}

				cnt++;
			}
			xMovement.add(xLocations);
			yMovement.add(yLocations);

		}

	}

	// Generates the initial location of all nodes (within a range of 4 steps from
	// nearest one)
	public void GenerateInitialLocation() {
		Random rand = new Random();

		/*
		 * general.allNodes.get(0).x = 0; general.allNodes.get(0).y = 0;
		 */
		for (int i = 0; i < general.numberOfNodes; ++i) {
			int randomNumX = rand.nextInt(general.numberOfNodes * 2);
			int randomNumY = rand.nextInt(general.numberOfNodes * 2);

			xInitial.add(randomNumX);
			yInitial.add(randomNumY);
			
		}

	}

	public void AssignInitialLocations() {

		System.out.println("Assign locations!!!!");

		for (int i = 0; i < general.allNodes.size(); ++i) {
			general.allNodes.get(i).x = xInitial.get(i);
			general.allNodes.get(i).y = yInitial.get(i);
		}
	}
}
