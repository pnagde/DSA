import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Graph {

    static class Node {
        int dist;
        int node;

        public Node(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }


    public int minimumEffortPath(int[][] heights) {

        int n = heights.length;
        int m = heights[0].length;

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        int[][] dist = new int[n][m];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        queue.add(new int[]{0, 0, 0});

        int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();

                int value = curr[0];
                int r = curr[1];
                int c = curr[2];

                if (r == n - 1 && c == m - 1)
                    return value;

                for (int[] dir : dirs) {
                    int row = r + dir[0];
                    int col = c + dir[1];
                    if (row < n && col < m && col >= 0 && row >= 0) {
                        int newEffort = Math.max(Math.abs(heights[r][c] - heights[row][col]), value);
                        if (newEffort < dist[row][col]) {
                            dist[row][col] = newEffort;
                            queue.add(new int[]{newEffort, row, col});
                        }
                    }
                }
            }

        }

        return 0;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<int[]>> adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            adjList.get(flights[i][0]).add(new int[]{flights[i][1], flights[i][2]});
        }


        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        int[] bestStops = new int[n];
        Arrays.fill(bestStops, Integer.MAX_VALUE);

        queue.offer(new int[]{0, src, 0});
        bestStops[src] = 0;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int totalCost = curr[0];
            int city = curr[1];
            int stopUsed = curr[2];

            if (city == dst) {
                return totalCost;
            }
            if (stopUsed > k) continue;

            for (int[] node : adjList.get(city)) {
                int from = node[0];
                int price = node[1];
                if (stopUsed + 1 <= bestStops[from]) {
                    bestStops[from] = stopUsed + 1;
                    queue.offer(new int[]{price + totalCost, from, stopUsed + 1});
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        int n = 4, k = 2;
        int[][] time = {{2, 1, 1}, {2, 3, 1}, {3, 4, 1}};
        System.out.println(graph.networkDelayTime(time, n, k));

    }

    public int networkDelayTime(int[][] times, int n, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(value -> value[0]));

        List<List<int[]>> adjList = new ArrayList<>();

        for (int i = 0; i < n + 1; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < times.length; i++) {
            int u = times[i][0];
            int v = times[i][1];
            int w = times[i][2];
            adjList.get(u).add(new int[]{v, w});
        }

        queue.offer(new int[]{ 0, k});

        int[] dist = new int[n + 1];

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[k] = 0;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int time = curr[0];
            int node = curr[1];

            if(time > dist[node]) continue;

            for (int[] neighbor : adjList.get(node)) {
                int v = neighbor[0];
                int t = neighbor[1];
                if(time + t < dist[v]) {
                    dist[v] = t + time;
                    queue.offer(new int[]{dist[v], v});
                }
            }
        }

        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            ans = Math.max(ans, dist[i]);
        }

        return ans;
    }

    public String findOrder(String[] words) {

        int n = words.length;

        int[] indegree = new int[26];
        Queue<String> queue = new LinkedList<>();


        // create adjList
        HashMap<Character, Character> adjList = new HashMap<>();

        // for(int i = 0; i< n; i++){
        //     adjList.put(,new ArrayList<>());
        // }

        int j = 1;
        int i = 0;

        while (j < n) {
            String s1 = words[i];
            String s2 = words[j];

            int k = 0;
            int p = 0;

            while (k < s1.length() && p < s2.length()) {
                if (s1.charAt(k) != s2.charAt(p)) {
                    break;
                }
                k++;
                p++;
            }
            adjList.putIfAbsent(s1.charAt(k), s2.charAt(p));
            indegree[s2.charAt(p)]++;
            i++;
            j++;

        }

        for (int o = 0; o < indegree.length; o++) {
            System.out.print(indegree[o] + " ");
        }

        System.out.println(adjList);

        return "";


    }
//    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
//        List<List<Node>> adjList = new LinkedList<>();
//
//        for(int i = 0; i < wordList.size() + 1; i++) {
//            adjList.add(new ArrayList<>());
//        }
//        wordList.add(0, beginWord);
//
//        String begin = beginWord;
//
//        for (int i = 1; i < adjList.size(); i++) {
//            int count = 0;
//            for(int j = 0; j < begin.length(); j++) {
//                if(wordList.get(i).charAt(j) == begin.charAt(j)) {
//                    count++;
//                }
//            }
//            if(count == begin.length() - 1) {
//                adjList.get(i).add(new Node(wordList.get(i)));
//            }
//            begin = wordList.get(i);
//        }
//
//        for(List<Node> node: adjList){
//            for (Node val : node) {
//                System.out.print(val.word + " -> ");
//            }
//            System.out.println();
//        }
//
//        return 0;
//    }
//
//    public int[][] updateMatrix(int[][] mat) {
//
//        int n = mat.length;
//        int m = mat[0].length;
//
//        Queue<Node> queue = new LinkedList<>();
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                if(mat[i][j] == 0){
//                    queue.offer(new Node(i, j));
//                } else{
//                    mat[i][j] = -1;
//                }
//            }
//        }
//
//        int[][] dirs = {{1,0},{0,1},{0,-1},{-1,0}};
//
//        while (!queue.isEmpty()){
//             Node curr = queue.poll();
//             for (int[] index : dirs){
//                 int nr = curr.i + index[0];
//                 int nc = curr.j + index[1];
//                 if (nr >= 0 && nr < n && nc >= 0 && nc < m && mat[nr][nc] == -1) {
//                     mat[nr][nc] = mat[curr.i][curr.j] + 1;
//                     queue.offer(new Node(nr, nc));
//                 }
//             }
//        }
//        return mat;
//    }
//
//    private void addConnectedNodes(Queue<Node> queue, int[][] mat, Node node) {
////        // for 0
////        if((node.i - 1 >= 0 && mat[node.i - 1][node.j] == 1) || (node.i + 1 < mat.length && mat[node.i + 1][node.j] == 1 )
////                || (node.j + 1 < mat[0].length && mat[node.i][node.j + 1] == 1) || (node.j - 1 >= 0 && mat[node.i][node.j - 1] == 1)) {
////
////        }
//
////        // for 1
//        if(node.i - 1 >= 0 && mat[node.i - 1][node.j] == 1) {
//            queue.offer(new Node(node.i - 1, node.j, node.d + 1));
//        }
//        if(node.i + 1 < mat.length && mat[node.i + 1][node.j] == 1) {
//            queue.offer(new Node(node.i + 1, node.j, node.d + 1));
//        }
//        if(node.j + 1 < mat[0].length && mat[node.i][node.j + 1] == 1) {
//            queue.offer(new Node(node.i, node.j + 1, node.d + 1));
//        }
//        if(node.j - 1 >= 0 && mat[node.i][node.j - 1] == 1) {
//            queue.offer(new Node(node.i, node.j - 1, node.d + 1));
//        }
//    }
//
//
//    public boolean canFinish(int numCourses, int[][] prerequisites) {
//
//        List<List<Integer>> adjList = new ArrayList<>();
//
//        for (int i = 0; i < prerequisites.length; i++) {
//            adjList.add(new ArrayList<>());
//        }
//        for (int i = 0; i < prerequisites.length; i++) {
//            for (int j = 0; j < prerequisites[i].length; j++) {
//                adjList.get(prerequisites[i][j]).add(prerequisites[i][j]);
//            }
//        }
//
//        for (int i = 0; i < adjList.size(); i++) {
//            System.out.print(adjList.get(i) + " : ");
//        }
//
//        int[] visited = new int[numCourses + 1];
//
//        for (int i = 0; i < numCourses; i++) {
//            if (visited[i] != 1) {
//                if (checkCycle(i, adjList, visited) == 1) return true;
//            }
//        }
//        return false;
//    }
//
//    private int checkCycle(int i, List<List<Integer>> adjList, int[] visited) {
//        Queue<Pair> queue = new LinkedList<>();
//
//        queue.offer(new Pair(i, -1));
//        visited[i] = 1;
//
//        while (!queue.isEmpty()) {
//            int node = queue.peek().i;
//            int parent = queue.peek().j;
//            queue.remove();
//
//            for (int adjLis : adjList.get(node)) {
//                if(visited[adjLis] != 1){
//                    visited[adjLis] = 1;
//                    queue.add(new Pair(adjLis, node));
//                } else if(parent != adjLis){
//                    return 1;
//                }
//            }
//        }
//        return -1;
//    }
//
//    class Pair {
//        int i;
//        int j;
//
//        public Pair(int i, int j) {
//            this.i = i;
//            this.j = j;
//        }
//
//        public int getJ() {
//            return j;
//        }
//
//        public void setJ(int j) {
//            this.j = j;
//        }
//
//        public int getI() {
//            return i;
//        }
//
//        public void setI(int i) {
//            this.i = i;
//        }
//    }
//
//    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
//        int pixel = image[sr][sc];
//        dfsFill(image, sr, sc, color, pixel);
//        return image;
//    }
//
//    private void dfsFill(int[][] image, int sr, int sc, int color, int start) {
//        if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length || image[sr][sc] != start) {
//            return;
//        }
//
//        image[sr][sc] = color;
//
//        dfsFill(image, sr, sc + 1, color, start);
//        dfsFill(image, sr, sc - 1, color, start);
//        dfsFill(image, sr - 1, sc, color, start);
//        dfsFill(image, sr + 1, sc, color, start);
//    }
//
//    public int orangesRotting(int[][] grid) {
//        List<List<Integer>> adjList = new ArrayList<>();
//        int n = grid.length - 1;
//        int count = -1;
//
//        for (int idex = 0; idex <= n; idex++) {
//            adjList.add(new ArrayList<>());
//            for (int kdex = 0; kdex <= n; kdex++) {
//                adjList.get(idex).add(grid[idex][kdex]);
//            }
//        }
//
//        Queue<Pair> queue = new LinkedList<>();
//
//        int i = 0;
//        int j = 0;
//
//        while (adjList.get(i).get(j) != 2) {
//            i++;
//            j++;
//        }
//
//        Pair pair = new Pair(i, j);
//
//        queue.offer(pair);
//
//        while (!queue.isEmpty()) {
//            for (int k = 0; k < queue.size(); k++) {
//                Pair value = queue.poll();
//
//                adjList.get(value.i).set(value.j, 2);
//
//                if (value.j + 1 <= n && adjList.get(value.i).get(value.j + 1) == 1) {
//                    queue.offer(new Pair(value.i, value.j + 1));
//                }
//                if (value.i + 1 <= n && adjList.get(value.i + 1).get(value.j) == 1) {
//                    queue.offer(new Pair(value.i + 1, value.j));
//                }
//                if (value.i - 1 > 0 && adjList.get(value.i - 1).get(value.j) == 1) {
//                    queue.offer(new Pair(value.i - 1, value.j));
//                }
//                if (value.j - 1 > 0 && adjList.get(value.i).get(value.j - 1) == 1) {
//                    queue.offer(new Pair(value.j, value.j - 1));
//                }
//            }
//            count++;
//        }
//        for (int k = 0; k < n; k++) {
//            for (int l = 0; l < n; l++) {
//                if (adjList.get(k).get(l) == 1) {
//                    return -1;
//                }
//            }
//        }
//        return count;
//    }
//
//    public int findCircleNum(int[][] isConnected) {
//        int n = isConnected.length;
//
//        int count = 0;
//
//        int[] visited = new int[n];
//
//        for (int i = 0; i < n; i++) {
//            if (visited[i] != 1) {
//                dfsCircle(isConnected, i, visited);
//                count++;
//            }
//        }
//        return count;
//    }
//
//    public void dfsCircle(int[][] isConnected, int city, int[] visited) {
//        visited[city] = 1;
//        for (int j = 0; j < isConnected.length; j++) {
//            if (isConnected[city][j] == 1 && visited[j] != 1) {
//                dfsCircle(isConnected, j, visited);
//            }
//        }
//    }
}
