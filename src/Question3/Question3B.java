package Question3;

/*
Implement bellman ford algorithm and priority queue using maximum heap
 */
public class Question3B {

    //implement your code here

    public static void main(String[] args) {
        int[][] edges = {{0, 1, 1}, {0, 2, 5}, {1, 2, 2}, {1, 3, 1}, {1, 4, 3}, {2, 4, 1}, {3, 4, 2}, {3, 5, 1}, {4, 5, 4}};
        int n = 6;
        int start = 0;
        int end = 5;
        System.out.println(bellmanFord(edges, n, start, end));
    }

    public static int bellmanFord(int[][] edges, int n, int start, int end) {
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[start] = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];
                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        return dist[end];
    }
}
