import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HS {
    DateFormat format = new SimpleDateFormat("hh:mm:ss.SSS");
    private void printTime(String whereabout) {
         System.out.println("log"+whereabout+":"+format.format(new Date()));

    }
    private ArrayList<Coordinate> path = new ArrayList<>();
    private ArrayList<Coordinate> emptyPlaces = new ArrayList<>();
    int a = 0;
    public ArrayList<Coordinate> path(Cell[][] map, Coordinate head) {
        ArrayList<Coordinate> emptyPlaces = new ArrayList<>();
        path = new ArrayList<>();
        path.add(head);

        for(int i = 0; i < map[0].length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j].equals(Cell.EMPTY)){
                	if((i > head.getX() - 15 && i < head.getX() + 15) && (j > head.getY() - 15 && j < head.getY() + 15)) {
                		emptyPlaces.add(new Coordinate(i, j));
                	}
                }
            }
        }
        hamCycleUtil(emptyPlaces);
        return path;
    }

    private boolean hamCycleUtil(ArrayList<Coordinate> emptyPlaces) {
        //a++;
        //System.out.println("log"+"HC: " + a);
        /* base case: If all vertices are included in
           Hamiltonian Cycle */
      /*  if (pos == V)
        {
            // And if there is an edge from the last included
            // vertex to the first vertex
            if (graph[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }*/

        // Try different vertices as a next candidate in
        // Hamiltonian Cycle. We don't try for 0 as we
        // included 0 as starting point in hamCycle()
        for (int i = 0; i < emptyPlaces.size(); i++)
        {
            /* Check if this vertex can be added to Hamiltonian
               Cycle */
            if (isSafe(emptyPlaces.get(i)))
            {
               path.add(emptyPlaces.get(i));

                /* recur to construct rest of the path */
                if (hamCycleUtil(emptyPlaces) == true)
                    return true;

                /* If adding vertex v doesn't lead to a solution,
                   then remove it */
                //path.remove(emptyPlaces.get(i));
            }
        }

        /* If no vertex can be added to Hamiltonian Cycle
           constructed so far, then return false */
        return false;
    }

    private boolean isSafe(Coordinate nextBlock) {
        /* Check if this vertex is an adjacent vertex of
           the previously added vertex. */
        if (!isAdjacent(path.get(path.size() - 1), nextBlock))
            return false;

        /* Check if the vertex has already been included.
           This step can be optimized by creating an array
           of size V */
        for (Coordinate c : path)
            if (c.equals(nextBlock))
                return false;
        
        return true;
    }

    private boolean isAdjacent(Coordinate current, Coordinate nextBlock){
    	if(current.getY() == nextBlock.getY() ) {
        	if(current.getX() + 1 == nextBlock.getX() || current.getX() - 1 == nextBlock.getX()) {
        		return true;
        	}
        }
        if(path.get(path.size() - 1).getX() == nextBlock.getX() ) {
        	if(current.getY() + 1 == nextBlock.getY() || current.getY() - 1 == nextBlock.getY()) {
        		return true;
        	}
        }
        
        return false;
    }

}
