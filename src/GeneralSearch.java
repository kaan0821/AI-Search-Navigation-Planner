import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class GeneralSearch {
    protected Map map;
    protected Coord start;
    protected Coord goal;
    protected Queue<Node> frontier = new LinkedList<Node>();;
    protected ArrayList<Node> extended = new ArrayList<Node>();
    protected ArrayList<Node> path = new ArrayList<Node>();

    public GeneralSearch(Map map, Coord start, Coord goal) {
        this.map = map;
        this.start = start;
        this. goal = goal;
    }

    //Make path by backtracking the parent of each node
    protected void makePath(Node node) {
        while(node.getParent() != null) {
            path.add(node);
            node = node.getParent();
        }
        path.add(node);
    }

    //Printing the frontier
    protected void printFrontier() {
        System.out.print("[");
        for (int i = 0; i < frontier.size(); i++) {
            if (i == frontier.size()-1) {
                System.out.print(((LinkedList<Node>) frontier).get(i).getState().toString());
            } else {
                System.out.print(((LinkedList<Node>) frontier).get(i).getState().toString()+",");
            }
        }
        System.out.print("]\n");
    }

    //Return the path with nodes
    public ArrayList<Node> getPath() {
        return path;
    }

    //Return the extended list
    public ArrayList<Node> getExtended() {
        return extended;
    }

    //Return the frontier length
    public int getFrontierSize() {
        return frontier.size();
    }

    //Check whether is node is extended
    protected boolean isExtended(Node node) {
        int len = extended.size();
        for (int i = 0; i < len; i++) {
            if (extended.get(i).getState().equals(node.getState()) && extended.get(i).getState().equals(node.getState())) {
                return true;
            }
        }
        return false;
    }

    //For child classes to implement
    protected abstract void expand(Node curr_node, Boolean upward);
}
