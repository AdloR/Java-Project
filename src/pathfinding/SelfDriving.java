package pathfinding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import exceptions.NotNeighboringCasesException;
import exceptions.UnreachableCaseException;
import robot.Robot;
import terrain.Carte;
import terrain.Case;

/**
 * A class implementing the ability of finding the closest way to a given {@code Case}.
 * TODO : replace dist by dist.
 */
public abstract class SelfDriving {

    /**
     * Node used by the Dijkstra algorithm to find the closest path.
     */
    class Node implements Comparable<Node> {
        private Case position;
        private int cost;
        private int dist;
        private Node previous;

        public Node(Case position) {
            this.position = position;
            this.cost = 0;
            this.dist = 0;
            this.previous = null;
        }

        /**
         * New constructor for the dijkstra algorithm
         *
         * @param position
         * @param dist
         */
        public Node(Case position, int dist) {
            this.position = position;
            this.dist = dist;
            this.previous = null;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }

        public Case getPosition() {
            return position;
        }

        public int getCost() {
            return cost;
        }

        public int getDist() {
            return dist;
        }

        @Override
        public int compareTo(Node o) {
            return -Integer.compare(dist, o.getDist());
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
     * Return the speed of the {@code Robot} (or anything {@code SelfDriving}) on a given {@code Case}.
     *
     * @param place The Case where we want to get the speed.
     * @return Speed of this on place.
     */
    public abstract int getSpeedOn(Case place);

    /**
     * Return True if there is accessible water on given place. This is not defined
     * in SelfDriving as different {@code Robot}s have different conditions for accessing
     * water.
     * 
     * @param place
     *
     * @return {@code true} if water is accessible.
     */
    protected abstract boolean findWater(Case place);

    public abstract int getTimeOn(Case pos);

    public abstract boolean isAccessible(Case place);

    private Node getNode(Case position) {
        if (graph.containsKey(position)) {
            return graph.get(position);
        }
        Node node = new Node(position);
        graph.put(position, node);
        return node;
    }

    /**
     * Generate path from Node.
     *
     * @param carte Carte of the simulation.
     * @param node  End of the path.
     * @return Path
     * @throws NotNeighboringCasesException
     */
    private Path generatePath(Carte carte, Node node) throws NotNeighboringCasesException {
        Path path = new Path(carte, this, node.getPosition());
        node = node.previous;
        while (node != null) {
            path.addStep(node.position);
            node = node.previous;
        }
        return path;
    }

    public Path Dijkstra(Carte carte, Case origin, CaseCompareCond cond) throws UnreachableCaseException {
        /* Initialization of the graph */
        Node bestEndNode = null;
        PriorityQueue<Node> vertexPriorityQueue = new PriorityQueue<Node>();
        graph = new HashMap<Case, Node>();
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

        /* Sets personnalized cost of all nodes and finds bestEndNode */
        while (!vertexPriorityQueue.isEmpty()) {
            Node u = vertexPriorityQueue.poll(); /* Maybe it's not the min but max... */
            for (Case position : carte.getVoisins(u.getPosition())) {
                if (isAccessible(position)) {
                    Node v = getNode(position);
                    int alt = u.getDist() + carte.getTailleCases() / this.getSpeedOn(u.getPosition());
                    if (alt < v.getDist()) {
                        v.setDist(alt);
                        v.setPrevious(u);
                    }
                }
            }
            if (cond.isEnding(u.position)) {
                if (bestEndNode == null || bestEndNode.getDist() > u.getDist()) {
                    bestEndNode = u;
                }
            }
        }
        return generatePath(carte, bestEndNode);
    }

    /**
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
