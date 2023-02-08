import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BiDirectional {
    private Map map;
    private Coord start;
    private Coord goal;
    //FIFO queue using LinkedList
    private Queue<Node> frontier = new LinkedList<Node>();
    private Queue<Node> frontier2 = new LinkedList<Node>();
    private ArrayList<Node> extended = new ArrayList<Node>();
    private ArrayList<Node> extended2 = new ArrayList<Node>();
    private ArrayList<Node> path = new ArrayList<Node>();

    public BiDirectional(Map map, Coord start, Coord goal) {
        this.map = map;
        this.start = start;
        this.goal = goal;
        //Initiating the start point of both
        Node start_initial = new Node(start, null, 0,0,0);
        Node goal_initial = new Node(goal, null, 0,0,0);
        frontier.add(start_initial);
        frontier2.add(goal_initial);
        extended.add(start_initial);
        extended2.add(goal_initial);

        while (!frontier.isEmpty() || !frontier2.isEmpty()) {
            //Print the frontier
            System.out.println("-----------Frontier1:-----------");
            printFrontier(frontier);
            System.out.println("-----------Frontier2:-----------");
            printFrontier(frontier2);
            //Forward exploring started
            Node current = frontier.poll();
            if (current != null) {
                int curr_row = current.getState().getR();
                int curr_col = current.getState().getC();
                //The forward move, if find a meeting point, stop
                if (isInFrontier(frontier2, current)) {
                    makePath(current, 1);
                    System.out.println("1Met at:  "+current.getState().toString());
                    break;
                } else {
                    //upward triangle
                    if ((curr_row%2 == 0 && curr_col%2 == 0) || (curr_row%2 == 1 && curr_col%2 == 1)){
                        expand(current, true,1);
                    //downwarfd triangle
                    } else {
                        expand(current, false,1);
                    }
                }
            }
            //Backward exploring started
            Node current2 = frontier2.poll();
            if (current2 != null) {
                int curr_row2 = current2.getState().getR();
                int curr_col2 = current2.getState().getC();
                //The backward move, if find a meeting point, stop
                if (isInFrontier(frontier, current2)) {
                    makePath(current2, 2);
                    System.out.println("2Met at:  "+current2.getState().toString());
                    break;
                } else {
                    //upward triangle
                    if ((curr_row2%2 == 0 && curr_col2%2 == 0) || (curr_row2%2 == 1 && curr_col2%2 == 1)){
                        expand(current2, true,2);
                    //downwarfd triangle
                    } else {
                        expand(current2, false,2);
                    }
                }
            }
        }
    }

    //Keep back-tracing from the goal node, each node only has on parent
    public void makePath(Node node, int number) {
        //If called by forward search
        //Always start by tracing the parents of the meeting point from backward search to goal
        if (number == 1) {
            Node temp = new Node(start, null, 0,0,0);
            for (int i = 0; i<frontier2.size(); i++) {
                if (((LinkedList<Node>) frontier2).get(i).getState().equals(node.getState())) {
                    temp = ((LinkedList<Node>) frontier2).get(i);
                }
            }
        //Link up with the tracing of meeting point from forward search to start
            while(temp.getParent() != null) {
                path.add(temp);
                temp = temp.getParent();
            }
            path.add(temp);
            Collections.reverse(path);

            while(node.getParent() != null) {
                path.add(node); 
                node = node.getParent();
            }
            path.add(node); 
        }

        //If called by backward search
        if (number == 2) {
            Node temp2 = node;
            while(node.getParent() != null) {
                path.add(node);
                node = node.getParent();
            }
            path.add(node);
            Collections.reverse(path);
            //Linking up with forward search trace
            Node temp = new Node(start, null, 0,0,0);
            for (int i = 0; i<frontier.size(); i++) {
                if (((LinkedList<Node>) frontier).get(i).getState().equals(temp2.getState())) {
                    temp = ((LinkedList<Node>) frontier).get(i);
                }
            }
            while(temp.getParent() != null) {
                path.add(temp);
                temp = temp.getParent();
            }
            path.add(temp);
        }
    }

    //Printing out the frontier at each stage
    public void printFrontier(Queue<Node> list) {
        System.out.print("[");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size()-1) {
                System.out.print(((LinkedList<Node>) list).get(i).getState().toString());
            } else {
                System.out.print(((LinkedList<Node>) list).get(i).getState().toString()+",");
            }
        }
        System.out.print("]\n");
    }

    //Return the path
    public ArrayList<Node> getPath() {
        return path;
    }

    //Return the extended list size, sum of both
    public int getExtendedSize() {
        return extended.size()+extended2.size();
    }

    //Check if a node is in the opponent's frontier
    public boolean isInFrontier(Queue<Node> list, Node node) {
        for (int i = 0; i < list.size(); i++) {
            if (((LinkedList<Node>) list).get(i).getState().equals(node.getState())) {
                return true;
            }
        }
        return false;
    }

    //Return the frontier size, sum of both
    public int getFrontierSize() {
        return frontier.size()+frontier2.size();
    }

    //Checks whether a node is already extended to, number used to dicern who called it
    public boolean isExtended(Node node, int number) {
        //Called by forward search
        if (number == 1) {
            int len = extended.size();
            for (int i = 0; i < len; i++) {
                if (extended.get(i).getState().equals(node.getState())) {
                    return true;
                }
            }
            return false;
        }
        //Called by backward search
        if (number == 2) {
            int len = extended2.size();
            for (int i = 0; i < len; i++) {
                if (extended2.get(i).getState().equals(node.getState())) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    //Explore the adjacent nodes
    public void expand(Node curr_node, Boolean upward, int number) {
        int[][] m= this.map.getMap();
		int rows= m.length;
		int columns= m[0].length;

        int r = curr_node.getState().getR();
        int c = curr_node.getState().getC();
        //Go rightward
        if (c < (columns-1) && m[r][c+1] != 1) {
            if (number == 1) {
                Node rightNode = new Node(new Coord(r, c+1), curr_node, 1,0,0);
                if (!isExtended(rightNode,1)) {
                    frontier.add(rightNode);
                    extended.add(rightNode);
                }
            }
            if (number == 2) {
                Node rightNode = new Node(new Coord(r, c+1), curr_node, 3,0,0);
                if (!isExtended(rightNode,2)) {
                    frontier2.add(rightNode);
                    extended2.add(rightNode);
                }
            }
        }
        //Go downward
        if (upward) {
            if (r < (rows-1) && m[r+1][c] != 1) {
                if (number == 1) {
                    Node downNode = new Node(new Coord(r+1, c), curr_node, 2,0,0);
                    if (!isExtended(downNode,1)) {
                        frontier.add(downNode);
                        extended.add(downNode);
                    }
                }
                if (number == 2) {
                    Node downNode = new Node(new Coord(r+1, c), curr_node, 4,0,0);
                    if (!isExtended(downNode,2)) {
                        frontier2.add(downNode);
                        extended2.add(downNode);
                    }
                }
            }
        }
        //Go leftward
        if (c > 0 && m[r][c-1] != 1) {
            if (number == 1) {
                Node leftNode = new Node(new Coord(r, c-1), curr_node, 3,0,0);
                if (!isExtended(leftNode,1)) {
                    frontier.add(leftNode);
                    extended.add(leftNode);
                }
            }
            if (number == 2) {
                Node leftNode = new Node(new Coord(r, c-1), curr_node, 1,0,0);
                if (!isExtended(leftNode,2)) {
                    frontier2.add(leftNode);
                    extended2.add(leftNode);
                }
            }
        }
        //Go upward
        if (!upward) {
            if (r > 0 && m[r-1][c] != 1) {
                if (number == 1) {
                    Node upNode = new Node(new Coord(r-1, c), curr_node, 4,0,0);
                    if (!isExtended(upNode,1)) {
                        frontier.add(upNode);
                        extended.add(upNode);
                    }
                }
                if (number == 2) {
                    Node upNode = new Node(new Coord(r-1, c), curr_node, 2,0,0);
                    if (!isExtended(upNode,2)) {
                        frontier2.add(upNode);
                        extended2.add(upNode);
                    }
                }
            }
        }
    }

}
