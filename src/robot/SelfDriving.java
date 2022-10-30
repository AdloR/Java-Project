package robot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import terrain.Carte;
import terrain.Case;

public abstract class SelfDriving {

    class Node {
        private Case position;
        private int cost;
        private int heuristic;

        public void setCost(int cost) {
            this.cost = cost;
        }

        public void setHeuristic(int heuristic) {
            this.heuristic = heuristic;
        }

        public Node(Case position) {
            this.position = position;
            this.cost = 0;
            this.heuristic = 0;
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

    private Carte carte;
    private HashMap<Case, Node> graph;

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

    private Node getNode(Case position) {
        if (graph.containsKey(position)) {
            return graph.get(position);
        }
        Node node = new Node(position);
        graph.put(position, node);
        return node;
    }

    public int aStar(Carte carte, Case origin, Case destination) {
        graph = new HashMap<Case, Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();
        PriorityQueue<Node> openList = new PriorityQueue<Node>(new NodeComparator());
        openList.add(getNode(origin));
        while (!closedList.isEmpty()) {
            Node u = openList.remove();
            if (u.getPosition().equals(destination)) {
                // TODO : reconstituerChemin(u)
                return 1; // TODO : return length
            }
            for (Case position : carte.getVoisins(origin)) {
                Node v = getNode(position);
                if (
                        (!closedList.contains(v)) ||
                                (openList.contains(v) &&
                                        v.getCost() < u.getCost()
                                )
                ) {
                    v.setCost(u.getCost() + 1);
                    v.setHeuristic(
                            v.cost +
                                    (int) (Math.pow(position.getLigne() - destination.getLigne(), 2) +
                                            Math.pow(position.getColonne() - destination.getColonne(), 2))
                    );
                    openList.add(v);
                }
            }
            closedList.add(u);
        }
        throw

        /*
         * closedList = File()
         * openList = FilePrioritaire(comparateur = compareParHeuristique)
         * openList.ajouter(depart)
         * tant que openList n'est pas vide
         * u = openList.defiler()
         * si u.x == objectif.x et u.y == objectif.y
         * reconstituerChemin(u)
         * terminer le programme
         * pour chaque voisin v de u dans g
         * si non( v existe dans closedList
         * ou v existe dans openList avec un coût inférieur)
         * v.cout = u.cout +1
         * v.heuristique = v.cout + distance([v.x, v.y], [objectif.x, objectif.y])
         * openList.ajouter(v)
         * closedList.ajouter(u)
         * terminer le programme (avec erreur)
         */

        return 0;
    }

}
