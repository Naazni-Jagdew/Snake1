import za.ac.wits.snake.DevelopmentAgent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class MyAgent extends DevelopmentAgent {
    DateFormat format = new SimpleDateFormat("hh:mm:ss.SSS");
    int timestamp = 0;
    private void printTime(String whereabout){
       // System.out.println("log"+whereabout+": "+timestamp+":"+format.format(new Date()));
        timestamp++;
    }

    public static void main(String[] args) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        int count = 0, co = 0;
        AStar astar = new AStar();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            //printTime("in try");
            String initString = br.readLine();
            String[] intitStore = initString.split(" ");
            int nSnakes = Integer.parseInt(intitStore[0]);
            int gridWidth = Integer.parseInt(intitStore[1]);
            int gridHeight = Integer.parseInt(intitStore[2]);

            String state = null;
            String snakeLength = null;
            String snakeKills = null;
            int[] headXY = null;
            int[] tailXY = null;

            double appleFreshness = 5;

            while (true) {
                //printTime("start while");
                //get apple details
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }


                String[] coordinatesOfApple = line.split(" ");

                int appleX = 0;
                int appleY = 0;
                try{
                    appleX = Integer.parseInt(coordinatesOfApple[0]);
                    appleY = Integer.parseInt(coordinatesOfApple[1]);
                } catch(NumberFormatException e){
                    String [] appleData = coordinatesOfApple[0].split(",");
                    appleX = Integer.parseInt(appleData[0]);
                    appleY = Integer.parseInt(appleData[1]);
                }

                /*obstacles*/
                // read in obstacles and do something with them!
                int nObstacles = 3;
                String[] obsArray = new String[3];
                for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                    String obs = br.readLine();
                    obsArray[obstacle] = obs;
                }

                String[] obstacle1 = obsArray[0].split(" ");
                String[] obstacle2 = obsArray[1].split(" ");
                String[] obstacle3 = obsArray[2].split(" ");
                /*end obstacles*/

                //get snake number
                String data = br.readLine();
                int mySnakeNum = Integer.parseInt(data);
                String[] snakeDetails = null;
                ArrayList<String[]> enemySnakes = new ArrayList<>();

                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    if (i == mySnakeNum) {
                        snakeDetails = snakeLine.split(" ");
                        //state = snakeDetails[0];
                        //snakeLength = snakeDetails[1];
                        //snakeKills = snakeDetails[2];
                        headXY = Stream.of(snakeDetails[3].split(",")).mapToInt(Integer::parseInt).toArray();
                        //tailXY = Stream.of(snakeDetails[snakeDetails.length - 1].split(",")).mapToInt(Integer::parseInt).toArray();
                        //hey! That's me :)
                    } else {
                        enemySnakes.add(snakeLine.split(" "));
                    }
                }
                //finished reading, calculate move:
                int move = -1;

                //printTime("before grid");
                Grid grid = new Grid(gridWidth,gridHeight);
                grid.setObstacles(obstacle1,obstacle2,obstacle3);
                grid.setApple(coordinatesOfApple);
                for(String[] enemySnake : enemySnakes){
                   for(int j = 3; j < enemySnake.length; j++) {
                        if(j+1 < enemySnake.length) {
                            grid.setMySnakeBody(enemySnake[j], enemySnake[j+1]);
                        }
                    }
                }

                for(int j = 3; j < snakeDetails.length; j++) {
                    if(j+1 < snakeDetails.length) {
                        grid.setMySnakeBody(snakeDetails[j], snakeDetails[j+1]);
                    }
                }
                grid.setHead(headXY);
                //printTime("before path");

                ArrayList<Coordinate> path = astar.findPath(grid.getBoard(), headXY[0], headXY[1], appleX, appleY);
                //printTime("after path");

                //todo: for loop for appleFreshness
                grid.setPath(path);
                if(count == 0){
                    count++;
                    //grid.printBoard();
                } else {
                    count++;
                }
                assert headXY != null;
                //direct snake to coordinates
                Coordinate headC = new Coordinate(headXY[0], headXY[1]);
                Coordinate moveC = new Coordinate();
                if(path.size()-2 >= 0){
                    moveC = path.get(path.size()-2);
                } else {
                    moveC = new Coordinate(0,0);
                    //todo: code hamiltonian cycle
                    //System.out.println("log"+"FLAG !");
                    //System.out.println("log"+"*************************************");
                    //path = astar.findPath(grid.getBoard(), headXY[0], headXY[1], appleX, appleY);
                    //moveC = path.get(path.size()-2);
                    //grid.printBoard();
                }

                if(moveC.getX() == headC.getX() && moveC.getY() < headC.getY()) {
                    move = 0;
                }
                if(moveC.getX() == headC.getX() && moveC.getY() > headC.getY()) {
                    move = 1;
                }
                if(moveC.getX() > headC.getX() && moveC.getY() == headC.getY()) {
                    move = 3;
                }
                if(moveC.getX() < headC.getX() && moveC.getY() == headC.getY()) {
                    move = 2;
                }

                /*if(headXY[0] == appleX-1 && headXY[1] == appleY){
                    System.out.println("log"+"Apple: " + new Coordinate(appleX, appleY).toString());
                    System.out.println("log"+"HEAD: " + new Coordinate(headXY[0], headXY[1]).toString());
                    System.out.println("log"+"moveC: " + moveC.toString());
                    System.out.println("log"+"MOVE: " + move);
                    grid.printBoard();
                }*/
                //printTime("end while");
                System.out.println(move);

            }
        } catch (IOException e) {
            //System.err.println(e);
            e.printStackTrace();
        }
    }
}

