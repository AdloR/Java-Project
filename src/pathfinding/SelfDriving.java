package pathfinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import exceptions.NotNeighboringCasesException;
import exceptions.UnreachableCaseException;
import terrain.Carte;
import terrain.Case;

public abstract class SelfDriving {

    class Node {
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
    }

    class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node arg0, Node arg1) {
            if (arg0.getHeuristic() < arg1.getHeuristic()) {
                return 1;
            } else if (arg0.getHeuristic() == arg1.getHeuristic()) {
                return 0;
            }
            return -1;
        }
    }

    private HashMap<Case, Node> graph;

    public abstract int getSpeedOn(Case place);

    private Node getNode(Case position) {
        if (graph.containsKey(position)) {
            return graph.get(position);
        }
        Node node = new Node(position);
        graph.put(position, node);
        return node;
    }

    public Path aStar(Carte carte, Case origin, Case destination)
            throws UnreachableCaseException, NotNeighboringCasesException {
        graph = new HashMap<Case, Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();
        PriorityQueue<Node> openList = new PriorityQueue<Node>(new NodeComparator());
        openList.add(getNode(origin));
        while (!closedList.isEmpty()) {
            Node u = openList.remove();
            if (u.getPosition().equals(destination)) {
                Path path = generatePath(carte, u);
                return path;
            }
            for (Case position : carte.getVoisins(origin)) {
                Node v = getNode(position);
                if ((!closedList.contains(v)) ||
                        (openList.contains(v) &&
                                v.getCost() < u.getCost())) {
                    v.setCost(u.getCost() + 1);
                    v.setHeuristic(
                            v.cost +
                                    (int) (Math.pow(position.getLigne() - destination.getLigne(), 2) +
                                            Math.pow(position.getColonne() - destination.getColonne(), 2)));
                    openList.add(v);
                    v.setPrevious(u);
                }
            }
            closedList.add(u);
        }
        throw new UnreachableCaseException("Target unreachable");
    }

    private Path generatePath(Carte carte, Node node) throws NotNeighboringCasesException {
        Path path = new Path(carte, this, node.getPosition());
        node = node.previous;
        while (node != null) {
            path.addStep(node.position);
            node = node.previous;
        }
        return path;
    }
}
