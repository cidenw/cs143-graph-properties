/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtheory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author mk
 */
public class GraphProperties {

	public int[][] adjacencyMatrix;
	public int[][] distanceMatrix;
	public Vector<VertexPair> vpList;
	static int time = 0;
	double[][] betweenness_centrality;
	double[][] degree_centrality;
	double[][] closeness_centrality;
	ArrayList<Integer[]> edgeConnectivityEdges;
	Vector<Vertex> vList;
	Vector<Edge> eList;
	int cliqueNodes[];
	ArrayList<Integer[]> maximumMatching;
	ArrayList<ArrayList<Integer[]>> blocks;
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

		
//		generateDistanceMatrix(vList);
//		displayContainers(vList);
//		
//		getBetweennessCentrality(vList, eList);
//		ArrayList<Integer[]> minCuts = edgeConnectivity();
//		// System.out.println("MINCUTS");
//		// for(int i=0; i<minCuts.size(); i++) {
//		// System.out.println(minCuts.get(i)[0]+"-"+minCuts.get(i)[1]);
//		// }
//		double[] closenessCentralities = getClosenessCentrality(vList, eList);
//		maximumMatching(vList, eList);
//		// VertexPair vp = vpList.get(0);
//		// for (int i = 0; i < vp.pathList.get(0).size(); i++) {
//		// System.out.print(vp.pathList.get(0).get(i).name + "->");
//		// }
//		// for (int i = 0; i < vpList.size(); i++) {
//		// System.out.println(vpList.get(i).vertex1.name + "+" +
//		// vpList.get(i).vertex2.name);
//		// }
		return adjacencyMatrix;
	}

	public ArrayList<ArrayList<Integer[]>> getBlock() {
		Block b = new Block(this.eList.size()*2);
		for(int i=0; i<eList.size(); i++) {
			System.out.println(eList.get(i).vertex1.name +" == " + eList.get(i).vertex2.name);
			System.out.println(eList.get(i).vertex2.name +" == " + eList.get(i).vertex1.name);
			b.addEdge(Integer.parseInt(eList.get(i).vertex1.name), Integer.parseInt(eList.get(i).vertex2.name));
			b.addEdge(Integer.parseInt(eList.get(i).vertex2.name), Integer.parseInt(eList.get(i).vertex1.name));
		}
		System.out.println("BELOW ARE BLOCKS");
		return b.BCC();
	}

	public void setNodeColor(Vertex aVertex, int color) {
		switch (color) {
		case 0:
			aVertex.setWhite = true;
			break;
		case 1:
			aVertex.setRed = true;
			break;
		case 2:
			aVertex.setBlue = true;
			break;
		case 3:
			aVertex.setGreen = true;
			break;
		case 4:
			aVertex.setYellow = true;
			break;
		default:

			break;
		}
	}

	public void setEdgeColor(Edge anEdge, int color) {
		switch (color) {
		case 0:
			anEdge.setWhite = true;
			break;
		case 1:
			anEdge.setRed = true;
			break;
		case 2:
			anEdge.setBlue = true;
			break;
		case 3:
			anEdge.setGreen = true;
			break;
		case 4:
			anEdge.setYellow = true;
			break;
		default:

			break;
		}
	}

	// public ArrayList<Integer[]> maximumMatching(){
	// return null;
	// }

	public boolean isBlock() {

		return false;
	}

	public ArrayList<Integer[]> maximumMatching(Vector<Vertex> vList, Vector<Edge> eList) {
		ArrayList<Integer[]> edges = new ArrayList<Integer[]>();
		/*
		 * int lowestDegree = vList.size(); int indexOfLowestDegree = -1; for (int i =
		 * 0; i < vList.size(); i++) { // System.out.println(i + "degree:" +
		 * vList.get(i).connectedVertices.size()); if
		 * (vList.get(i).connectedVertices.size() < lowestDegree) { lowestDegree =
		 * vList.get(i).connectedVertices.size(); indexOfLowestDegree = i; } }
		 */
//		int maxPath = 0;
		ArrayList<ArrayList<Vertex>> paths = new ArrayList<ArrayList<Vertex>>();
		for (int k = 0; k < vList.size(); k++) {
			Vertex startNode = vList.get(k);

			ArrayList<Vertex> currentPath = new ArrayList<Vertex>();
			Stack<Vertex> aStack = new Stack<Vertex>();
			aStack.push(startNode);
			ArrayList<Boolean> isVisited = new ArrayList<Boolean>();
			for (int i = 0; i < vList.size(); i++) {
				isVisited.add(false);
			}
			// System.out.println(startNode + "Start");
			while (!aStack.isEmpty()) {
				Vertex currNode = aStack.pop();
				// System.out.println("popped " + currNode.name);
				if (isVisited.get(Integer.parseInt(currNode.name)) == false) {
					currentPath.add(currNode);
					// System.out.print(currNode.name+"->");
					isVisited.set(Integer.parseInt(currNode.name), true);
				}
				// System.out.println("Curr Node " + currNode.name);
				// boolean hasSibling = false;
				for (int i = 0; i < currNode.connectedVertices.size(); i++) {
					if (!isVisited.get(Integer.parseInt(currNode.connectedVertices.get(i).name))) {
						// System.out.print(currNode.connectedVertices.get(i).name+",");
						// hasSibling = true;
						aStack.push(currNode.connectedVertices.get(i));
						// System.out.println("added " + currNode.name);
					}
				}
				/*
				 * System.out.println("b4size" + currentPath.size()); if(!hasSibling) {
				 * currentPath.remove(currentPath.size()-1); }
				 */
				System.out.println("size" + currentPath.size());
				// System.out.println();
			}

			int toDelete = -1;
			for (int i = 0; i < currentPath.size() - 1; i++) {
				System.out.println(currentPath.get(i).name + "-" + currentPath.get(i + 1).name);
				if (!isEdge(eList, currentPath.get(i), currentPath.get(i + 1))) {
					toDelete = i;
				}
			}
			if (toDelete != -1) {
				currentPath.remove(toDelete);
			}

			System.out.println("Maximum Matching Edges: ");
			/*
			 * for (int i = 0; i < currentPath.size() - 1; i++) {
			 * System.out.println(currentPath.get(i).name + "-" + currentPath.get(i +
			 * 1).name);
			 * 
			 * }
			 */

			paths.add(currentPath);
		}

		int longestPath = Integer.MIN_VALUE;
		int indexOfLongestPath = -1;
		for (int i = 0; i < paths.size(); i++) {
			if (paths.get(i).size() > longestPath) {
				longestPath = paths.get(i).size();
				indexOfLongestPath = i;
			}
			System.out.println("Path" + paths.get(i).size());
			for (int j = 0; j < paths.get(indexOfLongestPath).size(); j++) {
				System.out.print(paths.get(indexOfLongestPath).get(j).name + "->");
			}
		}
		System.out.println(indexOfLongestPath + "INDEX LONGEST PATH");
		// System.out.println("EDGES");
		if(indexOfLongestPath == -1) {
			return edges;
		}
		for (int i = 0; i < paths.get(indexOfLongestPath).size() - 1; i += 2) {
			Integer[] edge = new Integer[2];
			edge[0] = Integer.parseInt(paths.get(indexOfLongestPath).get(i).name);
			edge[1] = Integer.parseInt(paths.get(indexOfLongestPath).get(i + 1).name);
			edges.add(edge);
			System.out.println("Edge = " + edge[0] + "-" + edge[1]);
		}
		return edges;

	}

	public boolean isEdge(Vector<Edge> eList, Vertex x, Vertex y) {
		for (int i = 0; i < eList.size(); i++) {
			Edge temp = eList.get(i);
			if (temp.vertex1.name.equals(x.name) && temp.vertex2.name.equals(y.name)) {
				return true;
			}
			if (temp.vertex2.name.equals(x.name) && temp.vertex1.name.equals(y.name)) {
				return true;
			}
		}
		return false;
	}

	public double[] getClosenessCentrality(Vector<Vertex> vList, Vector<Edge> eList) {
		double[] closenessCentralities = new double[vList.size()];
		for (int i = 0; i < vList.size(); i++) {
			int sumOfShortestPaths = 0;
			double closenessCentrality = 0.0;
			for (int j = 0; j < vList.size(); j++) {
				sumOfShortestPaths += distanceMatrix[Integer.parseInt(vList.get(i).name)][j];
			}
			closenessCentrality = (double) (vList.size() - 1) / (double) sumOfShortestPaths;

			closenessCentralities[i] = closenessCentrality;
		}
		return closenessCentralities;
	}

	public double[] getBetweennessCentrality(Vector<Vertex> vList, Vector<Edge> eList) {
		double betweennessCentrality[] = new double[vList.size()];
		for (int i = 0; i < vList.size(); i++) {
			Vertex currNode = vList.get(i);
			// System.out.println();
			// System.out.println("Current node" + currNode.name);
			double betweenness = 0.0;
			for (int j = 0; j < vpList.size(); j++) {
				if (vpList.get(j).vertex1.name.equals(currNode.name)
						|| vpList.get(j).vertex2.name.equals(currNode.name)) {
					continue;
				}
				double numOfShortestPaths = vpList.get(j).getNumberOfShortestPathPassingNode(currNode,
						distanceMatrix[Integer.parseInt(vpList.get(j).vertex1.name)][Integer
								.parseInt(vpList.get(j).vertex2.name)]);
				// System.out.println("VertexPair =
				// "+vpList.get(j).vertex1.name+"-"+vpList.get(j).vertex2.name);
				// System.out.print(numOfShortestPaths);
				betweenness += numOfShortestPaths;

				// System.out.println();
			}
			// System.out.println("Betweenness="+betweenness);
			// System.out.println("\n");
			betweennessCentrality[i] = betweenness;
		}
		return betweennessCentrality;
	}

	public double[] getDegreeCentrality(Vector<Vertex> vList, Vector<Edge> eList) {
		double[] degreeCentralities = new double[vList.size()];

		for (int i = 0; i < vList.size(); i++) {
			ArrayList<Integer> adjacentNodes = getAdjacentNodes(vList, eList, Integer.parseInt(vList.get(i).name));
			degreeCentralities[i] = adjacentNodes.size();
		}

		return degreeCentralities;
	}

	public int[] checkClique(Vector<Vertex> vList, Vector<Edge> eList) {
		// finds k3 in graph
		int[] clique = new int[3];
		for (int i = 0; i < vList.size(); i++) {
			int node1 = Integer.parseInt(vList.get(i).name);
			for (int j = 0; j < vList.size(); j++) {
				int node2 = Integer.parseInt(vList.get(j).name);
				for (int k = 0; k < vList.size(); k++) {
					int node3 = Integer.parseInt(vList.get(k).name);
					if (node1 != node2 && node2 != node3) {
						if (isEdge(eList, node1, node2) && isEdge(eList, node2, node3) && isEdge(eList, node3, node1)) {
							clique[0] = node1;
							clique[1] = node2;
							clique[2] = node3;
							return clique;
						}
					}
				}
			}
		}

		return null;
	}

	// public ArrayList<ArrayList<Integer>> maximumMatching(Vector<Vertex> vList,
	// Vector<Edge> eList) {
	// return null;
	// }

	public ArrayList<Integer[]> edgeConnectivity() {
		ArrayList<ArrayList<Integer[]>> listOfMinCuts = new ArrayList<ArrayList<Integer[]>>();
		for (int i = 0; i < vpList.size(); i++) {
			int s = Integer.parseInt(vpList.get(i).vertex1.name);
			int t = Integer.parseInt(vpList.get(i).vertex2.name);
			listOfMinCuts.add(MinCut.minCut(adjacencyMatrix, s, t));
		}
		int leastMinCuts = Integer.MAX_VALUE;
		int indexOfLeastMinCuts = -1;
		for (int i = 0; i < listOfMinCuts.size(); i++) {
			if (listOfMinCuts.get(i).size() < leastMinCuts) {
				leastMinCuts = listOfMinCuts.get(i).size();
				indexOfLeastMinCuts = i;
			}
		}
		if(indexOfLeastMinCuts==-1) {
			return new ArrayList<Integer[]>();
		}
		return listOfMinCuts.get(indexOfLeastMinCuts);
	}

	public ArrayList<Integer> getAdjacentNodes(Vector<Vertex> vList, Vector<Edge> eList, int node) {
		ArrayList<Integer> adjacentNodes = new ArrayList<Integer>();
		for (int i = 0; i < vList.size(); i++) {
			if (i != node) {
				if (isEdge(eList, node, Integer.parseInt(vList.get(i).name))) {
					adjacentNodes.add(Integer.parseInt(vList.get(i).name));
				}
			}
		}
		return adjacentNodes;
	}

	public boolean isEdge(Vector<Edge> eList, int x, int y) {
		for (int i = 0; i < eList.size(); i++) {
			Edge temp = eList.get(i);
			if (Integer.parseInt(temp.vertex1.name) == x && Integer.parseInt(temp.vertex2.name) == y) {
				return true;
			}
			if (Integer.parseInt(temp.vertex1.name) == y && Integer.parseInt(temp.vertex2.name) == x) {
				return true;
			}
		}
		return false;
	}

	public int countComponents() {
		int count = 0;
		//int index = 0;
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

	public boolean isConnected() {
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
			// System.out.println("Node " + i);
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
//			} else {
//				System.out.println(adjacentNodes.get(index));
//				return true;
//			}
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

	public void showBlocks() {
		resetColors();
		System.out.println("SIZEEEEEEEEE" + this.blocks.size());
		for(int i=0; i<this.blocks.size(); i++) {
			for(int j=0; j<this.blocks.get(i).size(); j++) {
				if(i%2==0) {
					getVertex(this.blocks.get(i).get(j)[0]).setBlue = true;
					getVertex(this.blocks.get(i).get(j)[1]).setBlue = true;
					getEdge(this.blocks.get(i).get(j)[0], this.blocks.get(i).get(j)[1]).setBlue = true;
				}else {
					getVertex(this.blocks.get(i).get(j)[0]).setRed = true;
					getVertex(this.blocks.get(i).get(j)[1]).setRed = true;
					getEdge(this.blocks.get(i).get(j)[0], this.blocks.get(i).get(j)[1]).setRed = true;
				}
			}
		}
	}
	
	public void showCutEdges() {
		resetColors();
		for (int i = 0; i < this.edgeConnectivityEdges.size(); i++) {
			getVertex(edgeConnectivityEdges.get(i)[0]).setBlue = true;
			getVertex(edgeConnectivityEdges.get(i)[1]).setBlue = true;
			getEdge(edgeConnectivityEdges.get(i)[0], edgeConnectivityEdges.get(i)[1]).setBlue = true;
		}
	}

	public void showClique() {
		resetColors();
		if (cliqueNodes == null) {
			return;
		}
		getEdge(cliqueNodes[0], cliqueNodes[1]).setGreen = true;
		getEdge(cliqueNodes[1], cliqueNodes[2]).setGreen = true;
		getEdge(cliqueNodes[2], cliqueNodes[0]).setGreen = true;
		for (int i = 0; i < this.cliqueNodes.length; i++) {
			getVertex(cliqueNodes[i]).setGreen = true;
		}
	}

	public void showMatching() {
		resetColors();
		for (int i = 0; i < this.maximumMatching.size(); i++) {
			getVertex(maximumMatching.get(i)[0].intValue()).setRed = true;
			getVertex(maximumMatching.get(i)[1].intValue()).setRed = true;
			getEdge(maximumMatching.get(i)[0].intValue(), (maximumMatching.get(i)[1].intValue())).setRed = true;

		}
	}

	public void resetColors() {
		for (int i = 0; i < vList.size(); i++) {
			vList.get(i).setBlack = true;
			vList.get(i).setBlue = false;
			vList.get(i).setGreen = false;
			vList.get(i).setRed = false;
			vList.get(i).setWhite = false;
			vList.get(i).setYellow = false;
			vList.get(i).wasFocused = false;
		}
		for (int i = 0; i < eList.size(); i++) {
			eList.get(i).setBlack = true;
			eList.get(i).setBlue = false;
			eList.get(i).setGreen = false;
			eList.get(i).setRed = false;
			eList.get(i).setWhite = false;
			eList.get(i).setYellow = false;
			eList.get(i).wasFocused = false;
		}
	}

	private Edge getEdge(int vertex1, int vertex2) {
		for (int i = 0; i < eList.size(); i++) {
			System.out.println("VERTEX " + vertex1 + " & " + vertex2);
			System.out.println(eList.get(i).vertex1.name + " AND " + eList.get(i).vertex2.name);
			if (Integer.parseInt(eList.get(i).vertex1.name) == vertex1
					&& Integer.parseInt(eList.get(i).vertex2.name) == vertex2) {
				return eList.get(i);
			}
			if (Integer.parseInt(eList.get(i).vertex1.name) == vertex2
					&& Integer.parseInt(eList.get(i).vertex2.name) == vertex1) {
				return eList.get(i);
			}
		}
		return null;
	}

	public void showGraphProperties(Graphics g, int x, int y, Vector<Vertex> vList, Vector<Edge> eList) {
		this.vList = vList;
		this.eList = eList;
		boolean isConnected = isConnected();
		int numOfComponents = countComponents();
		boolean hasCycle = checkCycle();
		ArrayList<Integer[]> edgeConnectivityEdges = edgeConnectivity();
		this.edgeConnectivityEdges = edgeConnectivityEdges;
		int edgeConnectivity = edgeConnectivityEdges.size();
		int[] cliqueNodes = checkClique(vList, eList);
		this.cliqueNodes = cliqueNodes;
		boolean hasClique = true;
		if (cliqueNodes == null) {
			hasClique = false;
		}
		ArrayList<Integer[]> maximumMatching = maximumMatching(vList, eList);
		this.maximumMatching = maximumMatching;
		int numOfPairings = maximumMatching.size();
		g.drawString("Graph Properties", x, y - 10);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x - 10, y, 250, 220);
		g.setColor(Color.black);
		g.drawString("Connected: " + isConnected, x, y + 20);
		g.drawString("Number of Components: " + numOfComponents, x, y + 40);
		g.drawString("Cycle: " + hasCycle, x, y + 60);
		// g.drawString("Node Connectivity: " /* + ADD FXN HERE */, x, y + 80);
		g.drawString("Edge Connectivity: " + edgeConnectivity, x, y + 100);
		g.drawString("Has a Clique: " + hasClique, x, y + 120);
		g.drawString("Maximum Matching: " + numOfPairings, x, y + 140);
		double[] betweenness_centrality;
		double[] degree_centrality;
		double[] closeness_centrality;
		degree_centrality = getDegreeCentrality(vList, eList);
		betweenness_centrality = getBetweennessCentrality(vList, eList);
		closeness_centrality = getClosenessCentrality(vList, eList);
		double[][] bc_formatted = new double[vList.size()][2];
		double[][] dc_formatted = new double[vList.size()][2];
		double[][] cc_formatted = new double[vList.size()][2];
		for (int i = 0; i < vList.size(); i++) {
			bc_formatted[i][0] = i;
			bc_formatted[i][1] = betweenness_centrality[i];
			dc_formatted[i][0] = i;
			dc_formatted[i][1] = degree_centrality[i];
			cc_formatted[i][0] = i;
			cc_formatted[i][1] = closeness_centrality[i];
		}
		this.degree_centrality = dc_formatted;
		this.betweenness_centrality = bc_formatted;
		this.closeness_centrality = cc_formatted;
		blocks = getBlock();
		// g.drawString("Betweenness: " /* + ADD FXN HERE */, x, y + 160);
		// g.drawString("Closeness: ", x, y + 180);
		// g.drawString("Distance: " /* + ADD FXN HERE */, x, y + 200);

	}

	private Vertex getVertex(int name) {
		for (int i = 0; i < vList.size(); i++) {
			if (vList.get(i).name.equals(name + "")) {
				return vList.get(i);
			}
		}
		return null;
	}

	
	public void showTable(String tableType, String[] columnNames, double[][] tableVals) {

		System.out.println("TEST");
		JFrame tableFrame = new JFrame();

		tableFrame.setTitle(tableType);
		tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tableFrame.setResizable(false);

		String[][] main = new String[tableVals.length][columnNames.length];

		for (int i = 0; i < tableVals.length; i++) {
			for (int j = 0; j < columnNames.length; j++) {
				if (j == 0) {
					main[i][j] = (int) tableVals[i][j] + "";
				} else {
					main[i][j] = tableVals[i][j] + "";
				}

			}
		}

		JTable mainTable = new JTable(main, columnNames);
		JScrollPane scrollPane = new JScrollPane(mainTable);
		JPanel tablePanel = new JPanel();

		tablePanel.setSize(new Dimension(480, 640));
		tablePanel.add(scrollPane);

		tableFrame.setContentPane(tablePanel);
		tableFrame.pack();
		tableFrame.setSize(new Dimension(480, 500));
		tableFrame.setVisible(true);
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
