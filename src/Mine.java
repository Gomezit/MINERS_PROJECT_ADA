
import java.util.ArrayList;
import java.util.LinkedList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author andres
 */
public class Mine {

    int[][] matrix, adjacency;
    int max_miners;
    int type;
    int time;
    int value;
    int speed;
    int entry;
    ArrayList<Miner> miners;
    String id;

    public Mine(int[][] matrix, int max_miners, int type, int time, int value, int speed, String id) {
        this.matrix = matrix;
        this.max_miners = max_miners;
        this.type = type;
        this.time = time;
        this.value = value;
        this.speed = speed;
        this.id = id;
    }

    // Crear la matriz de adyacencia a partir de la matriz obtenida del JSON
    public void createAdjacency() {
        int size = matrix.length * matrix[0].length;
        adjacency = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacency[i][j] = 0;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int pos = i * matrix.length + j;

                if (matrix[i][j] == 0) {
                    continue;
                }

                // Derecha
                if (j + 1 < matrix.length) {
                    if (matrix[i][j + 1] > 0) {
                        int right = i * matrix.length + j + 1;
                        adjacency[pos][right] = 1;
                        adjacency[right][pos] = 1;
                    }
                }

                // Izquierda
                if (j - 1 >= 0) {
                    if (matrix[i][j - 1] > 0) {
                        int left = i * matrix.length + j - 1;
                        adjacency[pos][left] = 1;
                        adjacency[left][pos] = 1;
                    }
                }

                // Abajo
                if (i + 1 < matrix[0].length) {
                    if (matrix[i + 1][j] > 0) {
                        int up = (i + 1) * matrix.length + j;
                        adjacency[pos][up] = 1;
                        adjacency[up][pos] = 1;
                    }
                }

                // Arriba
                if (i - 1 >= 0) {
                    if (matrix[i - 1][j] > 0) {
                        int down = (i - 1) * matrix.length + j;
                        adjacency[pos][down] = 1;
                        adjacency[down][pos] = 1;
                    }
                }
            }
        }
    }

    // Algoritmo Floyd Warshall
    public int[][] floydWarshall() {
        int size = adjacency.length;
        int[][] floydWarshall = new int[size][size];
        int[][] paths = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                floydWarshall[i][j] = adjacency[i][j];
                if(adjacency[i][j] == 0) 
                    floydWarshall[i][j] = 99999;
                paths[i][j] = j;
            }
        }
        for (int i = 0; i < size; i++) {
            floydWarshall[i][i] = 0;
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (floydWarshall[i][j] > floydWarshall[i][k] + floydWarshall[k][j]) {
                        floydWarshall[i][j] = floydWarshall[i][k] + floydWarshall[k][j];
                        paths[i][j] = k;
                    }
                }
            }
        }
        return paths;
    }
    
    public LinkedList<Integer> getPathTo(int index) {
        LinkedList<Integer> path = new LinkedList<>();
        int[] paths = floydWarshall()[entry];
        int dest = index;
        while(true) {
            path.add(dest);
            if(adjacents(entry, dest))
                break;
            dest = paths[dest];
        }
        return path;
    }
    
    public boolean adjacents(int i, int j) {
        return i + 1 == j || i - 1 == j || i + matrix[0].length == j || i - matrix[0].length == j;
    }

    @Override
    public String toString() {
        return id;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getAdjacency() {
        return adjacency;
    }

    public void setAdjacency(int[][] adjacency) {
        this.adjacency = adjacency;
    }

    public int getMax_miners() {
        return max_miners;
    }

    public void setMax_miners(int max_miners) {
        this.max_miners = max_miners;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int inX) {
        this.entry = inX;
    }

    public ArrayList<Miner> getMiners() {
        return miners;
    }

    public void setMiners(ArrayList<Miner> miners) {
        this.miners = miners;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
}
