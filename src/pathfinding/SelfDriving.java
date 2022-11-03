package pathfinding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import exceptions.NotNeighboringCasesException;
import exceptions.UnreachableCaseException;
import terrain.Carte;
import terrain.Case;

/**
 * A class implementing the ability of finding the closest way to a given Case.
 */
public abstract class SelfDriving {

    /**
     * Node used by the A* algorithm to find the closest path.
     */
    class Node implements Comparable<Node> {
        private Case position;
        private int cost;
        private int heuristic;
        private Node previous;

        public Node(Case position) {
            this.position = position;
            this.cost = 0;
            this.heuristic = 0;
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

        public void setHeuristic(int heuristic) {
            this.heuristic = heuristic;
        }

        public Case getPosition() {
            return position;
        }

        public int getCost() {
            return cost;
        }

        public int getHeuristic() {
            return heuristic;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(heuristic, o.getHeuristic());
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
     * Return the speed of the robot (or anything SelfDriving) on a given Case.
     * 
     * @param place The Case where we want to get the speed.
     * @return Speed of this on place.
     */
    public abstract int getSpeedOn(Case place);

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
     * Implementation of A* algorithm for pathfinding.
     * 
     * @param carte       Carte of the simulation.
     * @param origin      Case from where we go.
     * @param destination Case where we want to go.
     * @return Path to follow from origin to destination.
     * @throws UnreachableCaseException
     * @throws NotNeighboringCasesException
     */
    public Path aStar(Carte carte, Case origin, Case destination)
            throws UnreachableCaseException, NotNeighboringCasesException {
        graph = new HashMap<Case, Node>();
        HashSet<Node> closedList = new HashSet<>();
        PriorityQueue<Node> openList = new PriorityQueue<Node>();
        openList.add(getNode(origin));
        while (!openList.isEmpty()) {
            Node u = openList.poll();
            if (u.getPosition().equals(destination)) {
                Path path = generatePath(carte, u);
                return path;
            }
            for (Case position : carte.getVoisins(u.getPosition())) {
                if (isAccessible(position)) {
                    Node v = getNode(position);
                    if (!(closedList.contains(v) || (openList.contains(v) && v.getCost() < u.getCost()))) {
                        v.setCost(u.getCost() + carte.getTailleCases() / (this.getSpeedOn(u.getPosition())));
                        v.setHeuristic(
                                v.cost +
                                        (int) (Math.pow(position.getLigne() - destination.getLigne(), 2) +
                                                Math.pow(position.getColonne() - destination.getColonne(), 2)));
                        openList.add(v);
                        v.setPrevious(u);
                    }
                }
            }
            closedList.add(u);
        }
        throw new UnreachableCaseException("Target unreachable");
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
     * 23 return dist, prev
     *
     */
}
