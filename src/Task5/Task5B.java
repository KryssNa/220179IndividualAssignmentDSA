package Task5;

/*
A network consisting of n servers is connected in a tree structure, where the servers are numbered from 0 to n -
1 and there are n - 1 connections between them that only allow for one-way communication. A 2D array a is
used to represent these connections, where a[i] = [ai, bi] represents a directed path from server ai to server bi.
However, due to specific requirements, all traffic from each server must route to server 0. The task is to
reorient some connections to ensure that each server has a path to server 0. The goal is to minimize the number
of edges that need to be changed. It is guaranteed that every server must have a path to server 0 after the
connections are reordered.
Input: n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
Output: 3
Explanation: Change the direction of edges show in red such that each node can reach the node 0.
*/

import java.util.ArrayList;
import java.util.List;
public class Task5B {

    // Function to calculate the minimum number of reorders required
    public static int minReorder(int n, int[][] connections) {
        List<List<Integer>> graph = new ArrayList<>(); // Create a list to represent the graph
        for (int i = 0; i < n; i++) {
            graph.add(i, new ArrayList<>()); // Initialize each node with an empty list
        }
        for (int[] connection : connections) {
            graph.get(connection[0]).add(connection[1]); // Add directed edge from source to destination
            graph.get(connection[1]).add(-connection[0]); // Add reverse directed edge from destination to source
        }
        return dfs(graph, new boolean[n], 0); // Call the DFS function to traverse the graph
    }

    // Depth-First Search (DFS) function to traverse the graph and count reorders
    private static int dfs(List<List<Integer>> graph, boolean[] visited, int node) {
        int count = 0; // Initialize a counter for reorders
        visited[node] = true; // Mark the current node as visited
        for (int next : graph.get(node)) {
            if (!visited[Math.abs(next)]) { // Check if the next node is unvisited
                // Recursively call DFS and update the count based on the direction of the edge
                count += dfs(graph, visited, Math.abs(next)) + (next > 0 ? 1 : 0);
            }
        }
        return count; // Return the total reorders
    }

    public static void main(String[] args) {
        int n = 6; // Total number of nodes
        int[][] connections = {{0,1},{1,3},{2,3},{4,0},{4,5}}; // Array of directed connections
        System.out.println(minReorder(n, connections)); // Print the minimum reorders required
    }
}
