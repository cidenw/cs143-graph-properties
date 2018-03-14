/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtheory;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 *
 * @author mk
 */
public class GraphProperties {

	public int[][] adjacencyMatrix;
	public int[][] distanceMatrix;
	public Vector<VertexPair> vpList;
	static int time = 0;

	public int[][] generateAdjacencyMatrix(Vector<Vertex> vList, Vector<Edge> eList) {
		adjacencyMatrix = new int[vList.size()][vList.size()];

		for (int a = 0; a < vList.size(); a++)// initialize
		{
			for (int b = 0; b < vList.size(); b++) {
				adjacencyMatrix[a][b] = 0;
			}
		}

		for (int i = 0; i < eList.size(); i++) {
			adjacencyMatrix[vList.indexOf(eList.get(i).vertex1)][vList.indexOf(eList.get(i).vertex2)] = 1;
			adjacencyMatrix[vList.indexOf(eList.get(i).vertex2)][vList.indexOf(eList.get(i).vertex1)] = 1;
		}
		/*
		 * for(int i=0; i<adjacencyMatrix.length; i++) { for(int j=0;
		 * j<adjacencyMatrix[0].length; j++) { System.out.print(adjacencyMatrix[i][j]);
		 * } System.out.println(); }
		 */
		/*System.out.println("Cycle: " + checkCycle());
		System.out.println("Is connected: " + checkConnected());
		System.out.println("No. of components: " + countComponents());
		findBridge();*/
		minVertexCover(vList, eList);
		return adjacencyMatrix;
	}

	public void minVertexCover(Vector<Vertex> vList, Vector<Edge> eList) {
		for(int i=0; i<vList.size(); i++) {
			System.out.println(vList.get(i));
		}
	}
	
	public boolean edgeConnectivity() {

		return false;
	}

	public int countComponents() {
		int count = 0;
		int index = 0;
		int noOfVertex = adjacencyMatrix.length;
		boolean isVisited[] = new boolean[noOfVertex];
		// System.out.println("Components:");
		for (int i = 0; i < noOfVertex; i++) {
			if (!isVisited[i]) {
				DFS(isVisited, i);
				// System.out.println();
				count++;
			}

		}
		return count;

	}

	public void DFS(boolean isVisited[], int vertex) {
		isVisited[vertex] = true;
		// System.out.print(vertex);
		ArrayList<Integer> adjacentNodes = getAdjacentNodes(vertex);
		for (int i = 0; i < adjacentNodes.size(); i++) {
			if (!isVisited[adjacentNodes.get(i)]) {
				DFS(isVisited, adjacentNodes.get(i));
			}
		}
	}

	public ArrayList<Integer> getAdjacentNodes(int vertex) {
		ArrayList<Integer> adjacentNodes = new ArrayList<Integer>();
		for (int i = 0; i < adjacencyMatrix[vertex].length; i++) {
			if ((i != vertex) && (adjacencyMatrix[vertex][i] == 1)) {
				adjacentNodes.add(i);
			}
		}
		// System.out.println(adjacentNodes);
		return adjacentNodes;
	}

	public boolean checkConnected() {
		for (int j = 0; j < adjacencyMatrix[0].length; j++) {
			boolean hasOne = false;
			for (int i = 0; i < adjacencyMatrix.length; i++) {
				if (adjacencyMatrix[i][j] == 1) {
					hasOne = true;
					break;
				}
			}
			if (hasOne) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean checkCycle() {
		int noOfVertex = adjacencyMatrix.length;
		boolean isVisited[] = new boolean[noOfVertex];
		for (int i = 0; i < noOfVertex; i++) {


			System.out.println("Node " + i);
			if (DFSCycle(isVisited, i, i, i))
				return true;

		}
		return false;
	}

	public boolean DFSCycle(boolean isVisited[], int vertex, int start, int parent) {
		isVisited[vertex] = true;
		System.out.println("VERTEX :" + vertex);
		ArrayList<Integer> adjacentNodes = getAdjacentNodes(vertex);
		System.out.println(adjacentNodes);

		/*
		 * if(adjacentNodes.size() == 1) { return false; }
		 */
		for (int i = 0; i < adjacentNodes.size(); i++) {
			if (!isVisited[adjacentNodes.get(i)]) {
				return DFSCycle(isVisited, adjacentNodes.get(i), start, vertex);
			} else if (start == adjacentNodes.get(i) && parent != start) {
				System.out.println(parent + "-" + adjacentNodes.get(i));
				return true;
			}
		}
		return false;
	}

	public void bridgeUtil(int u, boolean visited[], int disc[], int low[], int parent[]) {

		visited[u] = true;
		disc[u] = low[u] = ++time;
		// System.out.println("TIME: " + time);

		ArrayList<Integer> adjacentNodes = getAdjacentNodes(u);

		// System.out.println("NEW NODE: " + u);

		for (int i = 0; i < adjacentNodes.size(); i++) {
			int v = adjacentNodes.get(i);

			if (!visited[v]) {
				// System.out.println("NOT VISITED" + v);
				parent[v] = u;
				bridgeUtil(v, visited, disc, low, parent);

				low[u] = Math.min(low[u], low[v]);

				if (low[v] > disc[u]) {
					// System.out.println("Bridge:" + u + " " + v);
				}
			}

			else if (v != parent[u]) {
				low[u] = Math.min(low[u], disc[v]);
			}

		}
	}

	public void findBridge() {
		// System.out.println("\n\n\n\n");
		int noOfVertex = adjacencyMatrix.length;
		boolean visited[] = new boolean[noOfVertex];
		int disc[] = new int[noOfVertex];
		int low[] = new int[noOfVertex];
		int parent[] = new int[noOfVertex];

		for (int i = 0; i < noOfVertex; i++) {
			parent[i] = -1;
			visited[i] = false;
		}

		for (int i = 0; i < noOfVertex; i++) {
			if (!visited[i]) {
				// System.out.println(i);
				bridgeUtil(i, visited, disc, low, parent);
			}
		}
		// System.out.println("\n\n\n\n");
	}

	public int[][] generateDistanceMatrix(Vector<Vertex> vList) {
		distanceMatrix = new int[vList.size()][vList.size()];

		for (int a = 0; a < vList.size(); a++)// initialize
		{
			for (int b = 0; b < vList.size(); b++) {
				distanceMatrix[a][b] = 0;
			}
		}

		VertexPair vp;
		int shortestDistance;
		for (int i = 0; i < vList.size(); i++) {
			for (int j = i + 1; j < vList.size(); j++) {
				vp = new VertexPair(vList.get(i), vList.get(j));
				shortestDistance = vp.getShortestDistance();
				distanceMatrix[vList.indexOf(vp.vertex1)][vList.indexOf(vp.vertex2)] = shortestDistance;
				distanceMatrix[vList.indexOf(vp.vertex2)][vList.indexOf(vp.vertex1)] = shortestDistance;
			}
		}
		return distanceMatrix;
	}

	public void displayContainers(Vector<Vertex> vList) {
		vpList = new Vector<VertexPair>();
		int[] kWideGraph = new int[10];
		for (int i = 0; i < kWideGraph.length; i++) {
			kWideGraph[i] = -1;
		}

		VertexPair vp;

		for (int a = 0; a < vList.size(); a++) { // assign vertex pairs
			for (int b = a + 1; b < vList.size(); b++) {
				vp = new VertexPair(vList.get(a), vList.get(b));
				vpList.add(vp);
				int longestWidth = 0;
				/// System.out.println(">Vertex Pair " + vList.get(a).name + "-" +
				/// vList.get(b).name + "\n All Paths:");
				vp.generateVertexDisjointPaths();
				for (int i = 0; i < vp.VertexDisjointContainer.size(); i++) {// for every container of the vertex pair
					int width = vp.VertexDisjointContainer.get(i).size();
					Collections.sort(vp.VertexDisjointContainer.get(i), new descendingWidthComparator());
					int longestLength = vp.VertexDisjointContainer.get(i).firstElement().size();
					longestWidth = Math.max(longestWidth, width);
					/// System.out.println("\tContainer " + i + " - " + "Width=" + width + " -
					/// Length=" + longestLength);

					for (int j = 0; j < vp.VertexDisjointContainer.get(i).size(); j++) // for every path in the
																						// container
					{
						/// System.out.print("\t\tPath " + j + "\n\t\t\t");
						for (int k = 0; k < vp.VertexDisjointContainer.get(i).get(j).size(); k++) {
							/// System.out.print("-" +
							/// vp.VertexDisjointContainer.get(i).get(j).get(k).name);
						}
						/// System.out.println();
					}

				}
				// d-wide for vertexPair
				for (int k = 1; k <= longestWidth; k++) { // 1-wide, 2-wide, 3-wide...
					int minLength = 999;
					for (int m = 0; m < vp.VertexDisjointContainer.size(); m++) // for each container with k-wide select
																				// shortest length
					{
						minLength = Math.min(minLength, vp.VertexDisjointContainer.get(m).size());
					}
					if (minLength != 999) {
						/// System.out.println(k + "-wide for vertexpair(" + vp.vertex1.name + "-" +
						/// vp.vertex2.name + ")=" + minLength);
						kWideGraph[k] = Math.max(kWideGraph[k], minLength);
					}
				}
			}
		}

		for (int i = 0; i < kWideGraph.length; i++) {
			if (kWideGraph[i] != -1) {
				/// System.out.println("D" + i + "(G)=" + kWideGraph[i]);
			}
		}

	}

	public void drawAdjacencyMatrix(Graphics g, Vector<Vertex> vList, int x, int y) {
		int cSize = 20;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y - 30, vList.size() * cSize + cSize, vList.size() * cSize + cSize);
		g.setColor(Color.black);
		g.drawString("AdjacencyMatrix", x, y - cSize);
		for (int i = 0; i < vList.size(); i++) {
			g.setColor(Color.RED);
			g.drawString(vList.get(i).name, x + cSize + i * cSize, y);
			g.drawString(vList.get(i).name, x, cSize + i * cSize + y);
			g.setColor(Color.black);
			for (int j = 0; j < vList.size(); j++) {
				g.drawString("" + adjacencyMatrix[i][j], x + cSize * (j + 1), y + cSize * (i + 1));
			}
		}
	}

	public void drawDistanceMatrix(Graphics g, Vector<Vertex> vList, int x, int y) {
		int cSize = 20;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y - 30, vList.size() * cSize + cSize, vList.size() * cSize + cSize);
		g.setColor(Color.black);
		g.drawString("ShortestPathMatrix", x, y - cSize);
		for (int i = 0; i < vList.size(); i++) {
			g.setColor(Color.RED);
			g.drawString(vList.get(i).name, x + cSize + i * cSize, y);
			g.drawString(vList.get(i).name, x, cSize + i * cSize + y);
			g.setColor(Color.black);
			for (int j = 0; j < vList.size(); j++) {
				g.drawString("" + distanceMatrix[i][j], x + cSize * (j + 1), y + cSize * (i + 1));
			}
		}
	}

	public Vector<Vertex> vertexConnectivity(Vector<Vertex> vList) {
		Vector<Vertex> origList = new Vector<Vertex>();
		Vector<Vertex> tempList = new Vector<Vertex>();
		Vector<Vertex> toBeRemoved = new Vector<Vertex>();
		Vertex victim;

		origList.setSize(vList.size());
		Collections.copy(origList, vList);

		int maxPossibleRemove = 0;
		while (graphConnectivity(origList)) {
			Collections.sort(origList, new ascendingDegreeComparator());
			maxPossibleRemove = origList.firstElement().getDegree();

			for (Vertex v : origList) {
				if (v.getDegree() == maxPossibleRemove) {
					for (Vertex z : v.connectedVertices) {
						if (!tempList.contains(z)) {
							tempList.add(z);
						}
					}
				}
			}

			while (graphConnectivity(origList) && tempList.size() > 0) {
				Collections.sort(tempList, new descendingDegreeComparator());
				victim = tempList.firstElement();
				tempList.removeElementAt(0);
				origList.remove(victim);
				for (Vertex x : origList) {
					x.connectedVertices.remove(victim);
				}
				toBeRemoved.add(victim);
			}
			tempList.removeAllElements();
		}

		return toBeRemoved;
	}

	private boolean graphConnectivity(Vector<Vertex> vList) {

		Vector<Vertex> visitedList = new Vector<Vertex>();

		recurseGraphConnectivity(vList.firstElement().connectedVertices, visitedList); // recursive function
		if (visitedList.size() != vList.size()) {
			return false;
		} else {
			return true;
		}
	}

	private void recurseGraphConnectivity(Vector<Vertex> vList, Vector<Vertex> visitedList) {
		for (Vertex v : vList) {
			{
				if (!visitedList.contains(v)) {
					visitedList.add(v);
					recurseGraphConnectivity(v.connectedVertices, visitedList);
				}
			}
		}
	}

	private class ascendingDegreeComparator implements Comparator {

		public int compare(Object v1, Object v2) {

			if (((Vertex) v1).getDegree() > ((Vertex) v2).getDegree()) {
				return 1;
			} else if (((Vertex) v1).getDegree() > ((Vertex) v2).getDegree()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	private class descendingDegreeComparator implements Comparator {

		public int compare(Object v1, Object v2) {

			if (((Vertex) v1).getDegree() > ((Vertex) v2).getDegree()) {
				return -1;
			} else if (((Vertex) v1).getDegree() > ((Vertex) v2).getDegree()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	private class descendingWidthComparator implements Comparator {

		public int compare(Object v1, Object v2) {

			if (((Vector<Vertex>) v1).size() > (((Vector<Vertex>) v2).size())) {
				return -1;
			} else if (((Vector<Vertex>) v1).size() < (((Vector<Vertex>) v2).size())) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
