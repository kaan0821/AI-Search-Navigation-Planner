import java.util.Stack;

public class DFS extends GeneralSearch {
    private Stack<Node> frontier = new Stack<>();

    public DFS(Map map, Coord start, Coord goal) {
        super(map, start, goal);
        Node initial = new Node(start, null, 0,0,0);
        frontier.push(initial);
        extended.add(initial);
    
        while (!frontier.empty()) {
            
            //Print the frontier
            printFrontier();
    
            //Exploring started
            Node current = frontier.pop();
            int curr_row = current.getState().getR();
            int curr_col = current.getState().getC();
            //If found goal, start constructing the path by back-tracing
            if (current.getState().getC() == goal.getC() && current.getState().getR() == goal.getR()) {
                makePath(current);
                break;
            } else {
                //upward triangle
                if ((curr_row%2 == 0 && curr_col%2 == 0) || (curr_row%2 == 1 && curr_col%2 == 1)){
                    expand(current, true);
                //downward triangle
                } else {
                    expand(current, false);
                }
            }
        }
    }
    
    @Override
    //Printing out the frontier at each stage
    public void printFrontier() {
        System.out.print("[");
        for (int i = 0; i < frontier.size(); i++) {
            if (i == frontier.size()-1) {
                System.out.print(frontier.get(i).getState().toString());
            } else {
                System.out.print(frontier.get(i).getState().toString()+",");
            }
        }
        System.out.print("]\n");
    }

    @Override
    //Return the frontier size
    public int getFrontierSize() {
        return frontier.size();
    }

    @Override
    protected void expand(Node curr_node, Boolean upward) {
        int[][] m= this.map.getMap();
		int rows= m.length;
		int columns= m[0].length;
        int r = curr_node.getState().getR();
        int c = curr_node.getState().getC();

        //Go upward
        if (!upward) {
            if (r > 0 && m[r-1][c] != 1) {
                Node upNode = new Node(new Coord(r-1, c), curr_node, 4,0,0);
                if (!isExtended(upNode)) {
                    frontier.push(upNode);
                    extended.add(upNode);
                }
            }
        }
        //Go leftward
        if (c > 0 && m[r][c-1] != 1) {
            Node leftNode = new Node(new Coord(r, c-1), curr_node, 3,0,0);
            if (!isExtended(leftNode)) {
                frontier.push(leftNode);
                extended.add(leftNode);
            }
        }
        //Go downward
        if (upward) {
            if (r < (rows-1) && m[r+1][c] != 1) {
                Node downNode = new Node(new Coord(r+1, c), curr_node, 2,0,0);
                if (!isExtended(downNode)) {
                    frontier.push(downNode);
                    extended.add(downNode);
                }
            }
        }
        //Go rightward
        if (c < (columns-1) && m[r][c+1] != 1) {
            Node rightNode = new Node(new Coord(r, c+1), curr_node, 1,0,0);
            if (!isExtended(rightNode)) {
                frontier.push(rightNode);
                extended.add(rightNode);
            }
        }
    }
    
}