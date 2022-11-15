package pathfinding;

import java.util.HashMap;
import java.util.PriorityQueue;

import exceptions.NotNeighboringCasesException;
import exceptions.UnreachableCaseException;
import terrain.Carte;
import terrain.Case;

/**
 * A class implementing the ability of finding the closest way to a given tile
 * ({@code Case}).
 */
public abstract class SelfDriving {

    /**
     * Node used by the Dijkstra algorithm to find the closest path.
     */
    private static class Node implements Comparable<Node> {
        private final Case position;
        private int dist;
        private Node previous;

        /**
         * Node constructor
         *
         * @param position The tile ({@code Case}) represented.
         */
        public Node(Case position) {
            this.position = position;
            this.dist = 0;
            this.previous = null;
        }

        /**
         * New constructor for the dijkstra algorithm
         *
         * @param position The tile ({@code Case}) represented.
         * @param dist     The distance from the origin Node.
         */
        public Node(Case position, int dist) {
            this.position = position;
            this.dist = dist;
            this.previous = null;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }

        public Case getPosition() {
            return position;
        }

        public int getDist() {
            return dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(dist, o.getDist());
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Node)
                return this.position == ((Node) o).getPosition();
            else
                return false;
        }
    }

    private HashMap<Case, Node> graph;

    /**
     * Return the speed of the {@code Robot} (or anything {@code SelfDriving}) on a
     * given {@code Case}.
     *
     * @param place The tile ({@code Case}) where we want to get the speed.
     * @return Speed of this on place.
     */
    public abstract int getSpeedOn(Case place);

    /**
     * Return True if there is accessible water on given place. This is not defined
     * in SelfDriving as different {@code Robot}s have different conditions for
     * accessing
     * water.
     * 
     * @param place The tile from which we would like to refill.
     *
     * @return {@code true} if water is accessible.
     */
    public abstract boolean findWater(Case place);

    /**
     * Return the time the {@code Robot} (or anything {@code SelfDriving}) will
     * spend moving to escape
     * given {@code Case}.
     *
     * @param pos The tile ({@code Case}) from which we want to "escape".
     * @return The time needed to move from given tile.
     */
    public abstract int getTimeOn(Case pos);

    /**
     * Return if the given tile ({@code Case}) is accessible for the
     * {@code Selfdriving} instance.
     *
     * @param place The tile we would like to access.
     * @return True if {@code place} is accessible.
     */
    public abstract boolean isAccessible(Case place);

    /**
     * Return {@code Node} referring to given {@code Case}.
     * If none already exist, create one.
     *
     * @param position A tile ({@code Case}) of the map.
     * @return A node referring to given {@code position}.
     */
    private Node getNode(Case position) {
        if (graph.containsKey(position)) {
            return graph.get(position);
        }
        Node node = new Node(position);
        graph.put(position, node);
        return node;
    }

    /**
     * Generate path from Node, following {@code previous} attribute.
     *
     * @param node End of the path.
     * @return {@code Path} toward the tile referred by the {@code node} parameter.
     * @throws NotNeighboringCasesException This should not happen.
     */
    private Path generatePath(Node node) throws NotNeighboringCasesException {
        Carte carte = node.getPosition().getCarte();
        Path path = new Path(carte, this, node.getPosition());
        node = node.previous;
        while (node != null) {
            path.addStep(node.position);
            node = node.previous;
        }
        return path;
    }

    /**
     * Dijkstra algorithm for finding the shortest path toward a tile ({@code Case})
     * that satisfy given condition.
     *
     * @param origin The {@code Case} from which we want to go.
     * @param cond   A function returning either true of false. Returns true on
     *               {@code Case}s considered as destination or target.
     * @return A {@code Path} from {@code origin} toward a {@code Case} satisfying
     *         {@code cond}. The return path is the shortest in terms of travel
     *         time.
     * @throws UnreachableCaseException If there is no accessible {@code Case}
     *                                  satisfying given condition.
     *
     *                                  This code is an interpretation of pseudocode
     *                                  on Wikipedia :
     * @link https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     *
     *       TODO: validate this javadoc with the team
     */
    public Path Dijkstra(Case origin, CaseCompareCond cond) throws UnreachableCaseException {
        Carte carte = origin.getCarte();

        /* Initialization of the graph */
        Node bestEndNode = null;
        PriorityQueue<Node> vertexPriorityQueue = new PriorityQueue<>();
        graph = new HashMap<>();
        for (Case place : carte.getCases()) {
            if (isAccessible(place)) {
                Node n = new Node(place, Integer.MAX_VALUE);
                graph.put(place, n);
                if (!place.equals(origin)) {
                    vertexPriorityQueue.add(n);
                }
            }
        }
        graph.get(origin).setDist(0);
        vertexPriorityQueue.add(graph.get(origin));

        /* Sets personnalized cost of all nodes and finds bestEndNode */
        while (!vertexPriorityQueue.isEmpty()) {
            Node u = vertexPriorityQueue.poll(); /* Maybe it's not the min but max... */
            for (Case position : carte.getVoisins(u.getPosition())) {
                if (isAccessible(position)) {
                    Node v = getNode(position);
                    int alt = u.getDist() + carte.getTailleCases() / this.getSpeedOn(u.getPosition());
                    if (alt >= 0 && alt < v.getDist()) { // We are avoiding integer overflow with first condition.
                        v.setDist(alt);
                        v.setPrevious(u);
                        vertexPriorityQueue.add(v);
                    }
                }
            }
            if (cond.isEnding(u.position)) {
                if (bestEndNode == null || bestEndNode.getDist() > u.getDist()) {
                    bestEndNode = u;
                }
            }
        }
        if (bestEndNode == null)
            throw new UnreachableCaseException("No path exists between " + origin + " and a valid ending case.");
        return generatePath(bestEndNode);
    }

    /*
     * 1 function Dijkstra(Graph, source):
     * 2 dist[source] ← 0 // Initialization
     * 3
     * 4 create vertex priority queue Q
     * 5
     * 6 for each vertex v in Graph.Vertices:
     * 7 if v ≠ source
     * 8 dist[v] ← INFINITY // Unknown distance from source to v
     * 9 prev[v] ← UNDEFINED // Predecessor of v
     * 10
     * 11 Q.add_with_priority(v, dist[v])
     * 12
     * 13
     * 14 while Q is not empty: // The main loop
     * 15 u ← Q.extract_min() // Remove and return best vertex
     * 16 for each neighbor v of u: // Go through all v neighbors of u
     * 17 alt ← dist[u] + Graph.Edges(u, v)
     * 18 if alt < dist[v]:
     * 19 dist[v] ← alt
     * 20 prev[v] ← u
     * 21 Q.decrease_priority(v, alt)
     * 22
     * 23 return prev
     *
     * TODO: delete this
     */

    /**
     * A simple interface to enable Dijkstra to take any condition for a valid
     * ending {@code Case} as a lambda expression.
     */
    public interface CaseCompareCond {
        /**
         * The method a lambda expression will use.
         * Simply use {@code (Case c) -> boolean}.
         * 
         * @param c the Case which Dijkstra needs to know if it is valid as an ending
         * @return if the Case is valid for ending
         */
        public boolean isEnding(Case c);
    }
}
