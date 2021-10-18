import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    private Cell [][] board;

    public Grid(int x, int y) {
        int[][] grid = new int[x][y];
        board = new Cell[x][x];

        for(Cell[] row : board){
            Arrays.fill(row, Cell.EMPTY);
        }
    }

    public void setHead(int[] headXY){
        board[headXY[0]][headXY[1]] = Cell.HEAD;
    }

    public void setMySnakeBody(String pos1, String pos2){
        ArrayList<String> list = new ArrayList<>();

        int maxX, maxY;
        int minX, minY;

        String[] pos1temp = pos1.split(",");
        int posx1 = Integer.parseInt(pos1temp[0]);
        int posy1 = Integer.parseInt(pos1temp[1]);

        String[] pos2temp = pos2.split(",");
        int posx2 = Integer.parseInt(pos2temp[0]);
        int posy2 = Integer.parseInt(pos2temp[1]);

        if(posx1 >= posx2){
            maxX = posx1;
            minX = posx2;
        }
        else {
            maxX = posx2;
            minX = posx1;
        }

        if (posy1 >= posy2) {
            maxY = posy1;
            minY = posy2;
        }
        else {
            maxY = posy2;
            minY = posy1;
        }

        for (int i = minX; i <= maxX ; i++){
            for (int j = minY; j <= maxY ; j++) {
                String temp = String.valueOf(i)+","+String.valueOf(j);
                list.add(temp);

            }
        }

        for(String obst : list){
            setObstacles(new String[]{obst});
        }

    }
    public void setTail(int[] tailXY){
        board[tailXY[0]][tailXY[1]] = Cell.TAIL;
    }

    public void setObstacles(String[] ... obstacle){
        for(String[] ob1: obstacle){
            for (String ob1_1: ob1){
                int x, y;
                x = Integer.parseInt(ob1_1.split(",")[0]);
                y = Integer.parseInt(ob1_1.split(",")[1]);

                board[x][y] = Cell.OBSTACLE;
            }
        }
    }

    public void setApple(String[] Apple){
        int x = Integer.parseInt(Apple[0]);
        int y = Integer.parseInt(Apple[1]);

        board[x][y] = Cell.APPLE;
    }
    public void printApple(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j] == Cell.APPLE){
                    System.out.println("log"+"GRID::::X: " + i + " Y: " + j);
                }
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public void setPath(ArrayList<Coordinate> path){
        for(Coordinate coordinate : path){
            this.board[coordinate.getX()][coordinate.getY()] = Cell.PATH;
        }
    }


    public void printBoard(){
        for (int i = 0; i < 50; i++) {             //prints out matrix
            for (int j = 0; j < 50; j++) {
                if(board[j][i].equals(Cell.EMPTY))
                    System.err.print(0+ " ");
                if(board[j][i].equals(Cell.APPLE))
                    System.err.print(8+ " ");
                if(board[j][i].equals(Cell.OBSTACLE))
                    System.err.print(1+ " ");
                if(board[j][i].equals(Cell.HEAD))
                    System.err.print("H"+ " ");
                if(board[j][i].equals(Cell.PATH))
                    System.err.print("P"+ " ");
            }
            System.err.println();
        }

    }
}
