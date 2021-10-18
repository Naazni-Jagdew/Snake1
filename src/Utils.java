import java.util.ArrayList;

public class Utils {
	

	public boolean isGreedy(ArrayList<Coordinate> greedyCheck) {
		for(int i = 2; i < greedyCheck.size(); i++) {
			if(isNextTo(greedyCheck.get(0), greedyCheck.get(1)) && isNextTo(greedyCheck.get(0), greedyCheck.get(i))) {
				System.out.println("log"+"Apple: " + greedyCheck.get(0));
				System.out.println("log"+"MySnake: " + greedyCheck.get(1));
				System.out.println("log"+"Other: " + greedyCheck.get(i));
				return true;
			}
		}
		return false;
	}
	private boolean isNextTo(Coordinate apple, Coordinate head) {
		if((head.getX() == apple.getX()+1 || head.getX() == apple.getX()-1) && head.getY() == apple.getY()) {
			return true;
		}
		if((head.getY() == apple.getY()+1 || head.getY() == apple.getY()-1) && head.getX() == apple.getX()) {
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Coordinate> futureObstacle(Coordinate head, ArrayList<Coordinate> snakeHeads) {
		ArrayList<Coordinate> futureObstacles = new ArrayList<Coordinate>();
		for(int i = 2; i < snakeHeads.size(); i++) {
			//if x is the same
			if(head.getX() == snakeHeads.get(i).getX()) {
				if(head.getY() + 2 == snakeHeads.get(i).getY())
					futureObstacles.add(new Coordinate(head.getX(), head.getY()+1));
				if(head.getY() - 2 == snakeHeads.get(i).getY())
					futureObstacles.add(new Coordinate(head.getX(), head.getY()-1));
			}
			//if y is the same
			if(head.getY() == snakeHeads.get(i).getY()) {
				if(head.getX() + 2 == snakeHeads.get(i).getX()) 
					futureObstacles.add(new Coordinate(head.getX()+1, head.getY()));
				if(head.getX() - 2 == snakeHeads.get(i).getX())
					futureObstacles.add(new Coordinate(head.getX()-1, head.getY()));
			}
			//if x is less than
			if(head.getX() - 1 == snakeHeads.get(i).getX()) {
				if(head.getY() - 1 == snakeHeads.get(i).getY()) {
					futureObstacles.add(new Coordinate(head.getX()-1, head.getY()));
					futureObstacles.add(new Coordinate(head.getX(), head.getY()-1));
				}
				if(head.getY() + 1 == snakeHeads.get(i).getY()) {
					futureObstacles.add(new Coordinate(head.getX()-1, head.getY()));
					futureObstacles.add(new Coordinate(head.getX(), head.getY()+1));
				}
			}
			//if x is greater than
			if(head.getX() + 1 == snakeHeads.get(i).getX()) {
				if(head.getY() - 1 == snakeHeads.get(i).getY()) {
					futureObstacles.add(new Coordinate(head.getX()+1, head.getY()));
					futureObstacles.add(new Coordinate(head.getX(), head.getY()-1));
				}
				if(head.getY() + 1 == snakeHeads.get(i).getY()) {
					futureObstacles.add(new Coordinate(head.getX()+1, head.getY()));
					futureObstacles.add(new Coordinate(head.getX(), head.getY()+1));
				}
			}
		}
		return futureObstacles;
	}
	
	public boolean mustPreserve(Coordinate head, ArrayList<Coordinate> snakeHeads) {
		for(int i = 2; i < snakeHeads.size(); i++) {
			//if x is the same
			if(head.getX() == snakeHeads.get(i).getX()) {
				if(head.getY() + 3 == snakeHeads.get(i).getY() || head.getY() - 3 == snakeHeads.get(i).getY()) {
					return true;
				}
			}
			//if y is the same
			if(head.getY() == snakeHeads.get(i).getY()) {
				if(head.getX() + 3 == snakeHeads.get(i).getX() || head.getX() - 3 == snakeHeads.get(i).getX()) {
					return true;
				}
			}
			//if x is less than
			if(head.getX() - 2 == snakeHeads.get(i).getX()) {
				if(head.getY() - 2 == snakeHeads.get(i).getY() || head.getY() + 2 == snakeHeads.get(i).getY()) {
					return true;
				}
			}
			//if x is greater than
			if(head.getX() + 2 == snakeHeads.get(i).getX()) {
				if(head.getY() - 2 == snakeHeads.get(i).getY() || head.getY() + 2 == snakeHeads.get(i).getY()) {
					return true;
				}
			}
		}
		
		return false;
	}

}
