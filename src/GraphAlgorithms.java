import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Qingyuan Zhang
 * @userid qzhang417
 * @GTID 903497782
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Search cannot have any null input!");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!map.containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in graph!");
        }

        List<Vertex<T>> returnList = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Vertex<T> curr;
        queue.add(start);
        while (!queue.isEmpty()) {
            curr = queue.poll();
            if (!returnList.contains(curr)) {
                returnList.add(curr);
                List<VertexDistance<T>> list2 = map.get(curr);
                if (list2 == null) {
                    throw new IllegalArgumentException("The vertex is not in the graph!");
                }
                for (VertexDistance<T> item : list2) {
                    queue.add(item.getVertex());
                }
            }
        }
        return returnList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Search cannot have any null input!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!map.containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph!");
        }
        List<Vertex<T>> list = new ArrayList<>();
        dfsHelper(start, map, list);
        return list;
    }

    /**
     * Helper method for the recursive dfs method.
     *
     * @param start the current vertex to begin the dfs
     * @param map   the adjacency list
     * @param list  list of vertices
     * @param <T>   the generic type of the data in the vertices
     */
    private static <T> void dfsHelper(Vertex<T> start, Map<Vertex<T>, List<VertexDistance<T>>> map,
                                      List<Vertex<T>> list) {
        list.add(start);
        List<VertexDistance<T>> list2 = map.get(start);
        for (VertexDistance<T> item : list2) {
            if (!list.contains(item.getVertex())) {
                dfsHelper(item.getVertex(), map, list);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!map.containsKey(start)) {
            throw new IllegalArgumentException("Start does not exist in the graph!");
        }
        List<Vertex<T>> list = new ArrayList<>();
        list.add(start);
        Map<Vertex<T>, Integer> returnMap = new HashMap<>(map.size());
        for (Vertex<T> item : map.keySet()) {
            if (item.equals(start)) {
                returnMap.put(item, 0);
            } else {
                returnMap.put(item, Integer.MAX_VALUE);
            }
        }
        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>(map.get(start));
        while (!queue.isEmpty() && list.size() != returnMap.size()) {
            VertexDistance<T> current = queue.poll();
            if (!list.contains(current.getVertex())) {
                list.add(current.getVertex());
                returnMap.put(current.getVertex(), current.getDistance());
                List<VertexDistance<T>> edgeList = map.get(current.getVertex());
                for (VertexDistance<T> item : edgeList) {
                    if (!(current.getVertex().equals(item.getVertex()))) {
                        queue.add(new VertexDistance<T>(item.getVertex(), item.getDistance() + current.getDistance()));
                    }
                }
            } else {
                if (returnMap.get(current.getVertex()) > current.getDistance()) {
                    returnMap.put(current.getVertex(), current.getDistance());
                }
            }
        }
        return returnMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!map.containsKey(start)) {
            throw new IllegalArgumentException("The start does not exist in the graph.");
        }
        List<Vertex<T>> list = new ArrayList<>();
        list.add(start);
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        for (VertexDistance<T> item : map.get(start)) {
            queue.add(new Edge<>(start, item.getVertex(), item.getDistance()));
        }
        Set<Edge<T>> returnSet = new HashSet<>();
        Edge<T> edge;
        while (!queue.isEmpty()) {
            edge = queue.poll();
            if (!list.contains(edge.getV())) {
                returnSet.add(new Edge<>(edge.getU(), edge.getV(), edge.getWeight()));
                returnSet.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));
                list.add(edge.getV());
                for (VertexDistance<T> item : map.get(edge.getV())) {
                    if (!list.contains(item.getVertex())) {
                        queue.add(new Edge<>(edge.getV(), item.getVertex(), item.getDistance()));
                    }
                }
            }
        }
        return returnSet;
    }
}