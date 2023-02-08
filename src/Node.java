
public class Node implements Comparable<Node> {

    private Coord state;
    private Node parent_node;
    private int action;
    private int cost;
    private int depth;

    public Node(Coord state, Node parent_node, int action, int cost, int depth) {
        this.state = state;
        this.parent_node = parent_node;
        this.action = action;
        this.cost = cost;
        this.depth = depth;
    }

    //Get the Coordinates
    public Coord getState() {
        return this.state;
    }

    //Get the parent node
    public Node getParent() {
        return this.parent_node;
    }

    //Get the cost
    public int getCost() {
        return this.cost;
    }

    //Track the action taken to get here from the parent node
    public int getAction() {
        return this.action;
    }

    //Track the action taken to get here from the parent node
    public int getDepth() {
        return this.depth;
    }

    //Compare for priority queue
    @Override
    public int compareTo(Node n2) {
        if (this.getCost() == n2.getCost()) {
            //If f-cost same, compare tie-breaking strategy
            if (this.getAction() == n2.getAction()) {
                //If all same, compare depth
                if (this.getDepth() > n2.getDepth()) {
                    return 1;
                } else if (this.getDepth() < n2.getDepth()) {
                    return -1;
                } else {
                    return 0;
                }
            } else if (this.getAction() > n2.getAction()) {
                return 1;
            } else {
                return -1;
            }
        //Compare cost
        } else if (this.getCost() > n2.getCost()) {
            return 1;
        } else {
            return -1;
        }
    }

}
