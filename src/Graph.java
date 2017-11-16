
import com.google.gson.Gson;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author andres
 */
public class Graph {

    private final int vertices;
    private final int[][] adjacency_matrix;
    private ManageFile json;
    static int V;

    public Graph(int v) {
        vertices = v;
        adjacency_matrix = new int[vertices + 1][vertices + 1];
        V = vertices;

    }

    public Graph(int vertices, int[][] adjacency_matrix) {
        this.vertices = vertices;
        this.adjacency_matrix = adjacency_matrix;
    }

    public int getVertices() {
        return this.vertices;
    }

    public void makeEdge(int to, int from, int edge) {
        try {
            adjacency_matrix[to][from] = edge;
        } catch (ArrayIndexOutOfBoundsException index) {
            System.out.println("The vertices does not exists" + index);
        }
    }

    public int getEdge(int to, int from) {
        try {
            return adjacency_matrix[to][from];
        } catch (ArrayIndexOutOfBoundsException index) {
            System.out.println("The vertices does not exists" + index);
        }
        return -1;
    }

    public int[][] getAdjacency_matrix() {
        return adjacency_matrix;
    }

    public Graph createGraphFromJsonFile(String filename) {

        //READ JSON FILE
        String readJson = json.read(filename);

        //JSON TO GRAPH OBJECT WITH GSON
        Gson gson = new Gson();
        Graph g = gson.fromJson(readJson, Graph.class);

        return g;
    }

    private int minDistance(int dist[], Boolean sptSet[]) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++) {
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }

        return min_index;
    }

    // A utility function to print the constructed distance array
    void printSolution(int dist[], int n) {
        for (int i = 0; i < V; i++) {
            System.out.println("Vertex   Distance from Source");
            System.out.println(dist[i]);
        }
    }

    // Funtion that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    public void dijkstra(int graph[][], int src) {
        int dist[] = new int[V]; // The output array. dist[i] will hold
        // the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++) // Update dist[v] only if is not in sptSet, there is an
            // edge from u to v, and total weight of path from src to
            // v through u is smaller than current value of dist[v]
            {
                if (!sptSet[v] && graph[u][v] != 0
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        // print the constructed distance array
        printSolution(dist, V);
    }

}
