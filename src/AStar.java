import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AStar {

    DateFormat format = new SimpleDateFormat("ss.SSS");
    int timestamp = 0;
    ArrayList<Position> opened = new ArrayList<>();
    ArrayList<Position> closed = new ArrayList<>();

    private void printTime(String whereabout){
        System.out.println("log"+whereabout+": "+timestamp+":"+format.format(new Date()));
        timestamp++;
    }

    public enum Status {
        OPEN,CLOSED,UNKNOWN;
    }

    public int heuristic(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1-x2) + Math.abs(y1-y2)) * 10;
    }

    public Position[][] grid;

    public ArrayList<Coordinate> findPath(Cell[][] cellMap, int headX, int headY, int appleX, int appleY){
        opened.clear();
        closed.clear();
        //printTime("In A*");
        int w = cellMap.length;
        int h = cellMap[0].length;
        int hX=headX,hY=headY,aX=appleX,aY=appleY;

        grid = new Position[w][h];
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                grid[i][j] = new Position(i,j,0,0,0);
            }
        }
        grid[hX][hY].f = 0;
        grid[hX][hY].g = 0;
        grid[hX][hY].h = 0;
        opened.add(grid[hX][hY]); // add the start

        boolean addedEnd = false;
        //printTime("ASTAR: before while");
        while(!opened.isEmpty() && !addedEnd) {
            int lowestFInd = 0;
            for(int i = 0; i < opened.size(); i++) {
                if(opened.get(i).f < opened.get(lowestFInd).f) {
                    lowestFInd = i;
                }
            }
            int x = opened.get(lowestFInd).x;
            int y = opened.get(lowestFInd).y;
            Position p = grid[x][y];
            opened.remove(lowestFInd);
            closed.add(p);
            if(cellMap[x][y] != Cell.APPLE){
                grid[x][y].stat = Status.CLOSED;
            }
            if(x == aX && y == aY) {
                addedEnd = true;
                break;
            }

            ArrayList<Position> potential = new ArrayList<Position>();
            potential.add(new Position(x+1,y  ,10));
            potential.add(new Position(x  ,y+1,10));
            potential.add(new Position(x  ,y-1,10));
            potential.add(new Position(x-1,y  ,10));
            //printTime("ASTAR: before potential");
            int ppx = 0;
            int ppy = 0;
            for(Position potentialPosition : potential) {
                if(potentialPosition.x >= 0
                        && potentialPosition.x < w
                        && potentialPosition.y >= 0
                        && potentialPosition.y < h
                ) {
                    if(cellMap[potentialPosition.x][potentialPosition.y] != Cell.OBSTACLE
                            && grid[potentialPosition.x][potentialPosition.y].stat == Status.UNKNOWN)
                    {
                        ppx = potentialPosition.x;
                        ppy = potentialPosition.y;
                        grid[ppx][ppy].g += potentialPosition.g;
                        grid[ppx][ppy].h = heuristic(ppx,ppy,aX,aY);
                        grid[ppx][ppy].f = grid[ppx][ppy].g + grid[ppx][ppy].h;
                        grid[ppx][ppy].parentX = x;
                        grid[ppx][ppy].parentY = y;
                        grid[ppx][ppy].stat = Status.OPEN;
                        opened.add(grid[ppx][ppy]);
                    }
                }
            }
            //printTime("ASTAR: after potential");
        }
        if(addedEnd)
        {
            ArrayList<Coordinate> path = new ArrayList<Coordinate>();
            path.add(new Coordinate(appleX, appleY));
            int curX = aX;
            int curY = aY;
            int count = 0;
            while(curX != hX || curY != hY) {
                int nextCurX = grid[curX][curY].parentX;
                int nextCurY = grid[curX][curY].parentY;
                curX = nextCurX;
                curY = nextCurY;
                if(count == 0){
                    count++;
                    //System.out.println("log"+"PATH:::::: X: " + curX + " Y: " + curY);
                }
                path.add(new Coordinate(curX,curY));
            }
            return path;
        }
        //printTime("ASTAR: before end");
        return new ArrayList<Coordinate>();
    }
}
