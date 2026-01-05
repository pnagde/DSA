import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Graph {

    public static void main(String[] args) {
        Graph graph = new Graph();
        int[][] image = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        graph.updateMatrix(image);
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                System.out.print(image[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    class Node{
        int i;
        int j;
        int d;

        public Node(int i, int j, int d) {
            this.i = i;
            this.j = j;
            this.d = d;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }
    }

    public int[][] updateMatrix(int[][] mat) {

        int n = mat.length;
        int m = mat[0].length;

        int[][] visited = new int[n][m];

        Queue<Node> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(mat[i][j] == 1) {
                    queue.offer(new Node(i, j, 1));
                    while (!queue.isEmpty()){
                        int size = queue.size();
                        for (int k = 0; k < size; k++) {
                            Node node = queue.poll();
                            addConnectedNodes(queue, mat, node);
                            mat[i][j] = node.d;
                        }
                    }
                }
            }
        }
        return mat;
    }

    private void addConnectedNodes(Queue<Node> queue, int[][] mat, Node node) {
        // for 0
        if((node.i - 1 >= 0 && mat[node.i - 1][node.j] == 0) || (node.i + 1 < mat.length && mat[node.i + 1][node.j] == 0 )
                || (node.j + 1 < mat[0].length && mat[node.i][node.j + 1] == 0) || (node.j - 1 >= 0 && mat[node.i][node.j - 1] == 0)) {
            return;
        }

        // for 1
        if(node.i - 1 >= 0 && mat[node.i - 1][node.j] != 0) {
            queue.offer(new Node(node.i - 1, node.j, node.d + 1));
        }
        if(node.i + 1 < mat.length && mat[node.i + 1][node.j] != 0) {
            queue.offer(new Node(node.i + 1, node.j, node.d + 1));
        }
        if(node.j + 1 < mat[0].length && mat[node.i][node.j + 1] != 0) {
            queue.offer(new Node(node.i, node.j + 1, node.d + 1));
        }
        if(node.j - 1 >= 0 && mat[node.i][node.j - 1] != 0) {
            queue.offer(new Node(node.i, node.j - 1, node.d + 1));
        }
    }


    public boolean canFinish(int numCourses, int[][] prerequisites) {

        List<List<Integer>> adjList = new ArrayList<>();

        for (int i = 0; i < prerequisites.length; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            for (int j = 0; j < prerequisites[i].length; j++) {
                adjList.get(prerequisites[i][j]).add(prerequisites[i][j]);
            }
        }

        for (int i = 0; i < adjList.size(); i++) {
            System.out.print(adjList.get(i) + " : ");
        }

        int[] visited = new int[numCourses + 1];

        for (int i = 0; i < numCourses; i++) {
            if (visited[i] != 1) {
                if (checkCycle(i, adjList, visited) == 1) return true;
            }
        }
        return false;
    }

    private int checkCycle(int i, List<List<Integer>> adjList, int[] visited) {
        Queue<Pair> queue = new LinkedList<>();

        queue.offer(new Pair(i, -1));
        visited[i] = 1;

        while (!queue.isEmpty()) {
            int node = queue.peek().i;
            int parent = queue.peek().j;
            queue.remove();

            for (int adjLis : adjList.get(node)) {
                if(visited[adjLis] != 1){
                    visited[adjLis] = 1;
                    queue.add(new Pair(adjLis, node));
                } else if(parent != adjLis){
                    return 1;
                }
            }
        }
        return -1;
    }

    class Pair {
        int i;
        int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int pixel = image[sr][sc];
        dfsFill(image, sr, sc, color, pixel);
        return image;
    }

    private void dfsFill(int[][] image, int sr, int sc, int color, int start) {
        if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length || image[sr][sc] != start) {
            return;
        }

        image[sr][sc] = color;

        dfsFill(image, sr, sc + 1, color, start);
        dfsFill(image, sr, sc - 1, color, start);
        dfsFill(image, sr - 1, sc, color, start);
        dfsFill(image, sr + 1, sc, color, start);
    }

    public int orangesRotting(int[][] grid) {
        List<List<Integer>> adjList = new ArrayList<>();
        int n = grid.length - 1;
        int count = -1;

        for (int idex = 0; idex <= n; idex++) {
            adjList.add(new ArrayList<>());
            for (int kdex = 0; kdex <= n; kdex++) {
                adjList.get(idex).add(grid[idex][kdex]);
            }
        }

        Queue<Pair> queue = new LinkedList<>();

        int i = 0;
        int j = 0;

        while (adjList.get(i).get(j) != 2) {
            i++;
            j++;
        }

        Pair pair = new Pair(i, j);

        queue.offer(pair);

        while (!queue.isEmpty()) {
            for (int k = 0; k < queue.size(); k++) {
                Pair value = queue.poll();

                adjList.get(value.i).set(value.j, 2);

                if (value.j + 1 <= n && adjList.get(value.i).get(value.j + 1) == 1) {
                    queue.offer(new Pair(value.i, value.j + 1));
                }
                if (value.i + 1 <= n && adjList.get(value.i + 1).get(value.j) == 1) {
                    queue.offer(new Pair(value.i + 1, value.j));
                }
                if (value.i - 1 > 0 && adjList.get(value.i - 1).get(value.j) == 1) {
                    queue.offer(new Pair(value.i - 1, value.j));
                }
                if (value.j - 1 > 0 && adjList.get(value.i).get(value.j - 1) == 1) {
                    queue.offer(new Pair(value.j, value.j - 1));
                }
            }
            count++;
        }
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < n; l++) {
                if (adjList.get(k).get(l) == 1) {
                    return -1;
                }
            }
        }
        return count;
    }

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;

        int count = 0;

        int[] visited = new int[n];

        for (int i = 0; i < n; i++) {
            if (visited[i] != 1) {
                dfsCircle(isConnected, i, visited);
                count++;
            }
        }
        return count;
    }

    public void dfsCircle(int[][] isConnected, int city, int[] visited) {
        visited[city] = 1;
        for (int j = 0; j < isConnected.length; j++) {
            if (isConnected[city][j] == 1 && visited[j] != 1) {
                dfsCircle(isConnected, j, visited);
            }
        }
    }
}
