import java.util.ArrayList;

public class PathFinder {
	private ArrayList<Coordinate> emptyPlaces = new ArrayList<>();
	private AStar astar = null;
	
	public PathFinder(AStar astar) {
		this.astar = astar;
	}
	
	private void path(Cell[][] map, Coordinate head) {      
        for(int i = 0; i < map[0].length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j].equals(Cell.EMPTY)){
                	if((i > head.getX() - 10 && i < head.getX() + 10) && (j > head.getY() - 10 && j < head.getY() + 10)) {
                		emptyPlaces.add(new Coordinate(i, j));
                	}
                }
            }
        }
    }
	
	public ArrayList<Coordinate> getAStarPath(Cell[][] map, Coordinate head) {
		path(map, head);
		ArrayList<Coordinate> heuristics = new ArrayList<Coordinate>();
		
		Coordinate topRight = head;
        Coordinate topLeft = head;
        Coordinate bottomRight = head;
        Coordinate bottomLeft = head;
        
        
        
        for(Coordinate c : emptyPlaces) {
        	if(c.getX() > head.getX() && c.getY() < head.getY()) {
        		if(c.getX() >= topRight.getX() && c.getY() <= topRight.getY()) {
        			topRight = c;
        		}
        	}
        	if(c.getX() < head.getX() && c.getY() < head.getY()) {
        		if(c.getX() <= topLeft.getX() && c.getY() <= topLeft.getY()) {
        			topLeft = c;
        		}
        	}
        	if(c.getX() > head.getX() && c.getY() > head.getY()) {
        		if(c.getX() >= bottomRight.getX() && c.getY() >= bottomRight.getY()) {
        			bottomRight = c;
        		}
        	}
        	if(c.getX() < head.getX() && c.getY() > head.getY()) {
        		if(c.getX() <= bottomLeft.getX() && c.getY() >= bottomLeft.getY()) {
        			bottomLeft = c;
        		}
        	}
        }
        heuristics.add(bottomRight);
        heuristics.add(topLeft);
        heuristics.add(bottomLeft);
        heuristics.add(topRight);
        int heuristic = -1;
        Coordinate highestHeuristic = new Coordinate();
        for(Coordinate c : heuristics) {
        	int temp = astar.heuristic(head.getX(), head.getY(), c.getX(), c.getY());
        	
        	if(temp > heuristic) {
        		heuristic = temp;
        		highestHeuristic = c;
        	}
        	
        }
		//System.out.println("log"+highestHeuristic);
        return astar.findPath(map, head.getX(), head.getY(), highestHeuristic.getX(), highestHeuristic.getY());
	}
	
}
