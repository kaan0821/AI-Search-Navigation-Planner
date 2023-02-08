import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar extends GeneralSearch {

    //Priority Queue
    private Queue<Node> frontier = new PriorityQueue<Node>();

    public AStar(Map map, Coord start, Coord goal) {
        super(map, start, goal);
        //Creating the initial node
        int sc = start.getC();
        int sr = start.getR();
        int rg = goal.getR();
        int cg = goal.getC();
        int start_dist = computeDistance(sr, sc, getDir(sr, sc), rg, cg, getDir(rg, cg));
        Node initial = new Node(start, null, 0, start_dist, 0);
        frontier.add(initial);
        extended.add(initial);
    
        //This indicates the depth being explored
        while (!frontier.isEmpty()) {
            //Print the frontier
            printFrontier();
    
            //Exploring started, poll the node with lowest cost
            Node current = ((PriorityQueue<Node>)frontier).poll();
            int curr_row = current.getState().getR();
            int curr_col = current.getState().getC();
            //If found goal, start constructing the path by back-tracing
            if (current.getState().getC() == goal.getC() && current.getState().getR() == goal.getR()) {
                makePath(current);
                break;
            } else {
                //upward triangle
                int curr_dir = getDir(curr_row, curr_col);
                if (curr_dir == 0){
                    expand(current, true);
                //downwarfd triangle
                } else {
                    expand(current, false);
                }
            }
        }
    }
    
    //Computes the Manhattan Distance on triangular grids
    public int computeDistance(int row1, int col1, int dir1, int row2, int col2, int dir2) {
        int deltA = Math.abs(-row2+row1);
        int deltB = Math.abs((row1+col1-dir1)-(row2+col2-dir2))/2;
        int deltC = Math.abs((row1+col1-dir1)/2-row1+dir1 - (row2+col2-dir2)/2+row2-dir2);
        return deltA + deltB + deltC;
    }

    //Check if a node is in the frontier
    public boolean isInFrontier(Node node) {
        Queue<Node> temp = new PriorityQueue<Node>();
        int flag = 0;
        while (!frontier.isEmpty()) {
            if (frontier.peek().getState().equals(node.getState())) {
                flag = 1;
                temp.add(frontier.poll());
            } else {
                temp.add(frontier.poll());
            }
        }
        frontier = temp;
        if (flag == 1) {
            return true;
        } else {
            return false;
        }
    }

    //Compute the dir using coordinates
    public int getDir(int row, int col) {
        if ((row%2 == 0 && col%2 == 0) || (row%2 == 1 && col%2 == 1)){
            return 0;
        } else {
            return 1;
        }
    }

    //Replace an old node with lower priority
    public void handleIfInFrontier(Node node) {
        Queue<Node> temp = new PriorityQueue<Node>();
        while (!frontier.isEmpty()) {
            if (frontier.peek().getState().equals(node.getState())) {
                if (frontier.peek().getCost() > node.getCost()) {
                    frontier.poll();
                    temp.add(node);
                } else {
                    temp.add(frontier.poll());
                }
            } else {
                temp.add(frontier.poll());
            }
        }
        frontier = temp;
    }

    //Printing out the frontier at each stage
    public void printFrontier() {
        System.out.print("[");
        //No way to directly print a priority queue, so change it to sorted array
        Node[] nodes = frontier.toArray(new Node[frontier.size()]);
        ArrayList<String> temp = new ArrayList<String>();
        Arrays.sort(nodes);
        for (Node e : nodes) {
            temp.add(e.getState().toString() + ":"+ Integer.toString(e.getCost())+".0");
            temp.add(",");
        }
        //Strip away the comma at the end to align with the test output
        temp.remove(temp.size()-1);
        for (String each : temp) {
            System.out.print(each);
        }
        System.out.print("]\n");

    }

    //Return the frontier size
    @Override
    public int getFrontierSize() {
        return frontier.size();
    }

    @Override
    protected void expand(Node curr_node, Boolean upward) {
        int[][] m= this.map.getMap();
		int rows= m.length;
		int columns= m[0].length;
        //current info
        int r = curr_node.getState().getR();
        int c = curr_node.getState().getC();
        int adjacent = 0;
        //goal info
        int rg = goal.getR();
        int cg = goal.getC();
        int dirg = getDir(rg, cg);
        int depth = curr_node.getDepth()+1;

        //This indicates the dir for adjacent triangles
        if (upward) {
            adjacent = 1;
        } else {
            adjacent = 0;
        }

        int fcost = 0;
        //Go rightward
        if (c < (columns-1) && m[r][c+1] != 1) {
            fcost = computeDistance(r, c+1, adjacent, rg, cg, dirg);
            Node rightNode = new Node(new Coord(r, c+1), curr_node, 1,fcost+depth,depth);
            if (!isExtended(rightNode)) {
                frontier.add(rightNode);
                extended.add(rightNode);
            } else if (isInFrontier(rightNode)) {
                handleIfInFrontier(rightNode);
            } else {
            }
        }
        //Go downward
        if (upward) {
            if (r < (rows-1) && m[r+1][c] != 1) {
                fcost = computeDistance(r+1, c, adjacent, rg, cg, dirg);
                Node downNode = new Node(new Coord(r+1, c), curr_node, 2,fcost+depth,depth);
                if (!isExtended(downNode)) {
                    frontier.add(downNode);
                    extended.add(downNode);
                } else if (isInFrontier(downNode)) {
                    handleIfInFrontier(downNode);
                } else {
                }
            }
        }
        //Go leftward
        if (c > 0 && m[r][c-1] != 1) {
            fcost = computeDistance(r, c-1, adjacent, rg, cg, dirg);
            Node leftNode = new Node(new Coord(r, c-1), curr_node, 3,fcost+depth,depth);
            if (!isExtended(leftNode)) {
                frontier.add(leftNode);
                extended.add(leftNode);
            } else if (isInFrontier(leftNode)) {
                handleIfInFrontier(leftNode);
            } else {
            }
        }
        //Go upward
        if (!upward) {
            if (r > 0 && m[r-1][c] != 1) {
                fcost = computeDistance(r-1, c, adjacent, rg, cg, dirg);
                Node upNode = new Node(new Coord(r-1, c), curr_node, 4,fcost+depth,depth);
                if (!isExtended(upNode)) {
                    frontier.add(upNode);
                    extended.add(upNode);
                } else if (isInFrontier(upNode)) {
                    handleIfInFrontier(upNode);
                } else {
                }
            }
        }
    }
    
}