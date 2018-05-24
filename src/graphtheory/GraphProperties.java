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
		
//		//Properties variables
//		boolean isConnected = isConnected();
//		int noOfComponents = countComponents();
//		boolean isCycle = checkCycle();
		
//		for(int i=0; i<eList.size(); i++) {
//			System.out.println(eList.get(i).vertex1.name+"-"+eList.get(i).vertex2.name);
//		}
		
		int[] arr = checkClique(vList, eList);
		int[][] degreeCentralities = getDegreeCentrality(vList, eList);
//		for(int i=0; i<degreeCentralities.length; i++) {
//			System.out.println("Node "+ degreeCentralities[i][0]+" - "+ degreeCentralities[i][1]);
//		}
//		for(int i=0; i<vList.size(); i++) {
//			System.out.println(vList.get(i).name);
//		}
		generateDistanceMatrix(vList);
		displayContainers(vList);
//		for(int i=0; i<distanceMatrix.length; i++) {
//			for(int j=0; j<distanceMatrix.length; j++) {
//				System.out.println("i = "+i+" j="+j +"   dis(ij)="+distanceMatrix[i][j]);
//				System.out.println("paths");
//				for()
//				
//			}
//		}
		getBetweenessCentrality(vList, eList);
		double[] closenessCentralities = getClosenessCentrality(vList, eList);
		
		VertexPair vp = vpList.get(0);
		for(int i=0; i<vp.pathList.get(0).size(); i++) {
			System.out.print(vp.pathList.get(0).get(i).name+"->");
		}
		for(int i=0; i<vpList.size(); i++) {
			System.out.println(vpList.get(i).vertex1.name + "+"+ vpList.get(i).vertex2.name);
		}
		return adjacencyMatrix;
	}
	
	public int[] getBlock() {
		return null;
	}
	
	
	
	public void setNodeColor(Vertex aVertex, int color) {
		switch(color) {
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
		switch(color) {
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
	
	
	
	public double[] getClosenessCentrality(Vector<Vertex> vList, Vector<Edge> eList){
		double[] closenessCentralities = new double[vList.size()];
		for(int i=0; i<vList.size(); i++) {
			int sumOfShortestPaths = 0;
			double closenessCentrality = 0.0;
			for(int j=0; j<vList.size(); j++) {
				sumOfShortestPaths+=distanceMatrix[Integer.parseInt(vList.get(i).name)][j];
			}
			closenessCentrality = (double)(vList.size()-1)/(double)sumOfShortestPaths;
			
			closenessCentralities[i] = closenessCentrality;
		}
		return closenessCentralities;
	}
	
	public double[] getBetweenessCentrality(Vector<Vertex> vList, Vector<Edge> eList){
		double betweennessCentrality[] = new double[vList.size()];
		for(int i=0; i<vList.size(); i++) {
			Vertex currNode = vList.get(i);
//			System.out.println();
//			System.out.println("Current node" + currNode.name);
			double betweenness = 0.0;
			for(int j=0; j<vpList.size(); j++) {
				if(vpList.get(j).vertex1.name.equals(currNode.name) || vpList.get(j).vertex2.name.equals(currNode.name)) {
					continue;
				}
				double numOfShortestPaths = vpList.get(j).getNumberOfShortestPathPassingNode(currNode, distanceMatrix[Integer.parseInt(vpList.get(j).vertex1.name)][Integer.parseInt(vpList.get(j).vertex2.name)]);
//				System.out.println("VertexPair = "+vpList.get(j).vertex1.name+"-"+vpList.get(j).vertex2.name);
//				System.out.print(numOfShortestPaths);
				betweenness+=numOfShortestPaths;
				
//				System.out.println();
			}
//			System.out.println("Betweenness="+betweenness);
//			System.out.println("\n");
			betweennessCentrality[i] = betweenness;
		}
		return betweennessCentrality;
	}
	
	

	public int[][] getDegreeCentrality(Vector<Vertex> vList, Vector<Edge> eList){
		int[][] degreeCentralities = new int[vList.size()][2];
		
		for(int i=0; i<vList.size(); i++) {
			ArrayList<Integer> adjacentNodes = getAdjacentNodes(vList, eList, Integer.parseInt(vList.get(i).name));
			degreeCentralities[i][0] = Integer.parseInt(vList.get(i).name);
			degreeCentralities[i][1] = adjacentNodes.size();
		}
		return degreeCentralities;
	}
	
	public int[] checkClique(Vector<Vertex> vList, Vector<Edge> eList){
		// finds k3 in graph
		int[] clique = new int[3];
		for(int i=0; i<vList.size(); i++) {
			int node1 = Integer.parseInt(vList.get(i).name);
			for(int j=0; j<vList.size(); j++) {
				int node2 = Integer.parseInt(vList.get(j).name);
				for(int k=0; k<vList.size(); k++) {
					int node3 = Integer.parseInt(vList.get(k).name);
					if(node1 != node2 && node2!=node3) {
						if(isEdge(eList, node1, node2) && isEdge(eList, node2, node3) && isEdge(eList, node3, node1)) {
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
	
	
	public ArrayList<ArrayList<Integer>> maximumMatching(Vector<Vertex> vList, Vector<Edge> eList) {
		return null;
	}

	public boolean edgeConnectivity() {
		return false;
	}
	
	public ArrayList<Integer> getAdjacentNodes(Vector<Vertex> vList, Vector<Edge> eList, int node) {
		ArrayList<Integer> adjacentNodes = new ArrayList<Integer>();
		for(int i=0; i<vList.size(); i++) {
			if(i!=node) {
				if(isEdge(eList, node, Integer.parseInt(vList.get(i).name))) {
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

			//System.out.println("Node " + i);
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
	
	
	
	public void showGraphProperties(Graphics g, int x, int y) {
		g.drawString("Graph Properties", x, y-10);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x-10, y, 250, 220);
		g.setColor(Color.black);
		g.drawString("Connected: " /* + ADD FXN HERE*/, x, y + 20);
		g.drawString("Number of Components: " /* + ADD FXN HERE*/, x, y + 40);
		g.drawString("Cycle: " /* + ADD FXN HERE*/, x, y + 60);
		g.drawString("Node Connectivity: " /* + ADD FXN HERE*/, x, y + 80);
		g.drawString("Edge Connectivity: " /* + ADD FXN HERE*/, x, y + 100);
		g.drawString("Has a Clique: " /* + ADD FXN HERE*/, x, y + 120);
		g.drawString("Maximum Matching: " /* + ADD FXN HERE*/, x, y + 140);
		g.drawString("Betweenness: " /* + ADD FXN HERE*/, x, y + 160);
		g.drawString("Closeness: ", x, y + 180);
		g.drawString("Distance: " /* + ADD FXN HERE*/, x, y + 200);

		
	}
	
	
	public void showTable(String tableType, String[] columnNames, int[][] tableVals) {
		
		System.out.println("TEST");
		JFrame tableFrame = new JFrame();
		
		tableFrame.setTitle(tableType);
		tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tableFrame.setResizable(false);
		
		Integer[][] main = new Integer[tableVals.length][columnNames.length];
		
		for(int i = 0; i < tableVals.length; i++) {
			for(int j = 0; j < columnNames.length; j++) {
				main[i][j] = Integer.valueOf(tableVals[i][j]);
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
