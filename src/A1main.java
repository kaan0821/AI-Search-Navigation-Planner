import java.util.ArrayList;

public class A1main {

	public static void main(String[] args) {

		Conf conf = Conf.valueOf(args[1]);

		//run your search algorithm 
		runSearch(args[0],conf.getMap(),conf.getS(),conf.getG());

	}

    //Main snippet initiating different search algorithms
	private static void runSearch(String algo, Map map, Coord start, Coord goal) {
		switch(algo) {
            case "BFS": //run BFS
                BFS bfs = new BFS(map, start, goal);
                ArrayList<Node> bfs_solution = bfs.getPath();
                //If found a path
                if (bfs_solution.size()!=0){
                    //Print Coord sequence
                    for (int i= bfs_solution.size()-1; i>=0; i--) {
                        System.out.print(bfs_solution.get(i).getState().toString());
                    }
                    System.out.println();
                    //Print actions taken
                    for (int i= bfs_solution.size()-1; i>=0; i--) {
                        printAction(bfs_solution,i);
                    }
                    System.out.println();
                    //Print path length
                    System.out.println(bfs_solution.size()-1 + ".0");
                    //Print nodes visited
                    System.out.println(bfs.getExtended().size() - bfs.getFrontierSize());
                //If failed
                } else {
                    System.out.println();
                    System.out.println("fail");
                    System.out.println(bfs.getExtended().size() - bfs.getFrontierSize());
                }
                break;

            case "DFS": //run DFS
                DFS dfs = new DFS(map, start, goal);
                ArrayList<Node> dfs_solution = dfs.getPath();
                //If found a path
                if (dfs_solution.size()!=0){
                    //Print Coord sequence
                    for (int i= dfs_solution.size()-1; i>=0; i--) {
                        System.out.print(dfs_solution.get(i).getState().toString());
                    }
                    System.out.println();
                    //Print actions taken
                    for (int i= dfs_solution.size()-1; i>=0; i--) {
                        printAction(dfs_solution,i);
                    }
                    System.out.println();
                    //Print path length
                    System.out.println(dfs_solution.size()-1 + ".0");
                    //Print nodes visited
                    System.out.println(dfs.getExtended().size() - dfs.getFrontierSize());
                //If failed
                } else {
                    System.out.println();
                    System.out.println("fail");
                    System.out.println(dfs.getExtended().size() - dfs.getFrontierSize());
                }
                break;

            case "BestF": //run BestF
                BestF bestF = new BestF(map, start, goal);
                ArrayList<Node> bestF_solution = bestF.getPath();
                //If found a path
                if (bestF_solution.size()!=0){
                    //Print Coord sequence
                    for (int i= bestF_solution.size()-1; i>=0; i--) {
                        System.out.print(bestF_solution.get(i).getState().toString());
                    }
                    //Print actions taken
                    System.out.println();
                    for (int i= bestF_solution.size()-1; i>=0; i--) {
                        printAction(bestF_solution,i);
                    }
                    System.out.println();
                    //Print path length
                    System.out.println(bestF_solution.size()-1 + ".0");
                    //Print nodes visited
                    System.out.println(bestF.getExtended().size() - bestF.getFrontierSize());
                //If failed
                } else {
                    System.out.println();
                    System.out.println("fail");
                    System.out.println(bestF.getExtended().size() - bestF.getFrontierSize());
                }
                break;

            case "AStar": //run AStar
                AStar astar = new AStar(map, start, goal);
                ArrayList<Node> astar_solution = astar.getPath();
                //If found a path
                if (astar_solution.size()!=0){
                    //Print Coord sequence
                    for (int i= astar_solution.size()-1; i>=0; i--) {
                        System.out.print(astar_solution.get(i).getState().toString());
                    }
                    //Print actions taken
                    System.out.println();
                    for (int i= astar_solution.size()-1; i>=0; i--) {
                        printAction(astar_solution,i);
                    }
                    System.out.println();
                    //Print path length
                    System.out.println(astar_solution.size()-1 + ".0");
                    //Print nodes visited
                    System.out.println(astar.getExtended().size() - astar.getFrontierSize());
                //If failed
                } else {
                    System.out.println();
                    System.out.println("fail");
                    System.out.println(astar.getExtended().size() - astar.getFrontierSize());
                }
                break;

            case "BiDirectional": //run BiDirectional
                BiDirectional bidir = new BiDirectional(map, start, goal);
                ArrayList<Node> bidir_solution = bidir.getPath();
                //If found a path
                if (bidir_solution.size()!=0){
                    //Print Coord sequence
                    for (int i= bidir_solution.size()-1; i>=0; i--) {
                        if (i>0 && !bidir_solution.get(i).getState().equals(bidir_solution.get(i-1).getState())) {
                            System.out.print(bidir_solution.get(i).getState().toString());
                        } else if (i==0){
                            System.out.print(bidir_solution.get(i).getState().toString());
                        } else{
                        }
                    }
                    //Print actions taken
                    System.out.println();
                    for (int i= bidir_solution.size()-1; i>=0; i--) {
                        printAction(bidir_solution,i);
                    }
                    System.out.println();
                    //Print path length
                    System.out.println(bidir_solution.size()-2 + ".0");
                    //Print nodes visited
                    System.out.println(bidir.getExtendedSize() - bidir.getFrontierSize());
                //If failed
                } else {
                    System.out.println();
                    System.out.println("fail");
                    System.out.println(bidir.getExtendedSize() - bidir.getFrontierSize());
                }
                break;
        }
	}

    //Print out all the actions taken in order
    private static void printAction(ArrayList<Node> solution, int count) {
        int act = solution.get(count).getAction();
        if (act == 1) {
            System.out.print("Right");
        } else if (act == 2) {
            System.out.print("Down");
        } else if (act == 3) {
            System.out.print("Left");
        } else if (act == 4) {
            System.out.print("Up");
        } else {
        }
        if (count!= solution.size()-1 && count != 0) {
            System.out.print(" ");
        }       
    }

	private static boolean isCoord(Coord coord, int r, int c) {
		//check if coordinates are the same as current (r,c)
		if(coord.getR()==r && coord.getC()==c) {
			return true;
		}
		return false;
	}

    private static void printMap(Map m, Coord init, Coord goal) {

		int[][] map=m.getMap();

		System.out.println();
		int rows=map.length;
		int columns=map[0].length;

		//top row
		System.out.print("  ");
		for(int c=0;c<columns;c++) {
			System.out.print(" "+c);
		}
		System.out.println();
		System.out.print("  ");
		for(int c=0;c<columns;c++) {
			System.out.print(" -");
		}
		System.out.println();

		//print rows 
		for(int r=0;r<rows;r++) {
			boolean right;
			System.out.print(r+"|");
			if(r%2==0) { //even row, starts right [=starts left & flip right]
				right=false;
			}else { //odd row, starts left [=starts right & flip left]
				right=true;
			}
			for(int c=0;c<columns;c++) {
				System.out.print(flip(right));
				if(isCoord(init,r,c)) {
					System.out.print("S");
				}else {
					if(isCoord(goal,r,c)) {
						System.out.print("G");
					}else {
						if(map[r][c]==0){
							System.out.print(".");
						}else{
							System.out.print(map[r][c]);
						}
					}
				}
				right=!right;
			}
			System.out.println(flip(right));
		}
		System.out.println();


	}

    public static String flip(boolean right) {
        //prints triangle edges
		if(right) {
			return "\\"; //right return left
		}else {
			return "/"; //left return right
		}

	}

}