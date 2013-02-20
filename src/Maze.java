import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class Maze {
	private int size_h = 10;
	private int size_v = 10;
	Dragon dragon = new Dragon(); 
	Hero hero = new Hero();
	char wall = 'X';
	char empty = ' ';
	char space = ' ';
	char exit = 'S';
	char weapon = 'E';
	static boolean gameOver = false;
	static String gameOverMessage = "Game Over";
	static int dragonMoves = 5;

	private char[][] positions = new char[size_h][size_v];

	public void spawnDragon(int x, int y){
		dragon.x = x;
		dragon.y = y;
		positions[x][y] = dragon.image;
	}

	public void spawnHero(int x, int y){
		hero.x = x;
		hero.y = y;
		positions[x][y] = hero.image;
	}

	public void startPredefinedMaze(){
		for (int x = 0; x < positions.length; x++) {
			for (int y = 0; y < positions.length; y++) {
				if ( x == 0 || x == size_h-1  || y == 0 || y == size_v-1){
					positions[x][y] = wall;
				} else if(y == 2 || y == 3 || y == 5 || y == 7){
					positions[x][y] = wall;
				} else {
					positions[x][y] = empty;
				}
			}
		}
		spawnDragon(3, 4);
		spawnHero(1, 1);
		positions[1][2] = empty;
		positions[1][3] = empty;
		positions[1][5] = empty;
		positions[1][7] = empty;
		positions[5][2] = empty;
		positions[5][3] = empty;
		positions[5][5] = empty;
		positions[8][5] = empty;
		positions[8][7] = empty;
		positions[5][9] = exit;
		positions[8][1] = weapon;
	}

	public void printMaze(){
		for (int x = 0; x < size_h; x++) {
			for (int y = 0; y < size_v; y++) {
				System.out.print(positions[x][y]);
				System.out.print(space);
			}
			System.out.print('\n');
		}
	}

	public boolean checkValidPosition(int x, int y){
		if(x <= 0 || x >= size_v || y <= 0 || y >= size_h){
			return false;
		}
		if (positions[x][y] == dragon.image ) {
			if(hero.isArmed){
				// dragon was slain
				System.out.println("You have killed the dragon!");
				return true;
			} else {
				gameOverMessage = "You have been killed by the dragon!";
				gameOver = true;
			}
		}
		
		if(positions[x][y] == exit){
			if (hero.isArmed) {
				gameOver = true;
				return true;
			} else 
				return false;
		}
		if(positions[x][y] == weapon){
			hero.pickWeapon();
			return true;
		}
		if (positions[x][y] == empty)
			return true;
		else return false;
	}

	public boolean moveHero(){
		String input = " ";
		try{
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			input = bufferRead.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		switch (input) {
		case "w": 
			if (checkValidPosition(hero.x-1, hero.y)){
				positions[hero.x][hero.y] = empty;
				hero.x -=1;
				positions[hero.x][hero.y] = hero.image;
				return true;
			}
			break;
		case "a":
			if (checkValidPosition(hero.x, hero.y-1)){
				positions[hero.x][hero.y] = empty;
				hero.y -=1;
				positions[hero.x][hero.y] = hero.image;	
				return true;
			}
			break;
		case "s":
			if (checkValidPosition(hero.x+1, hero.y)){
				positions[hero.x][hero.y] = empty;
				hero.x +=1;
				positions[hero.x][hero.y] = hero.image;
				return true;
			}
			break;
		case "d" :
			if (checkValidPosition(hero.x, hero.y+1)){
				positions[hero.x][hero.y] = empty;
				hero.y +=1;
				positions[hero.x][hero.y] = hero.image;
				return true;
			}
			break;
		default:
			break;
		}

		return false;
	}
	
	public boolean checkDragonPosition(int x, int y){
		if(x <= 0 || x >= size_v || y <= 0 || y >= size_h){
			return false;
		}
		if (positions[x][y] == empty)
			return true;
		else return false;
	}
	
	public void moveDragon(){
		Random r = new Random();
		boolean done = false;
		while (!done){
			int move = r.nextInt(dragonMoves);	
			switch (move) {
			case 0:
				done = true;
				break;
			case 1: // w
				if (checkDragonPosition(dragon.x-1, dragon.y)){
					positions[dragon.x][dragon.y] = empty;
					dragon.x -=1;
					positions[dragon.x][dragon.y] = dragon.image;
					done = true;
				}
				break;
			case 2: // a
				if (checkValidPosition(dragon.x, dragon.y-1)){
					positions[dragon.x][dragon.y] = empty;
					dragon.y -=1;
					positions[dragon.x][dragon.y] = dragon.image;	
					done = true;
				}
				break;
			case 3: // s
				if (checkValidPosition(dragon.x+1, dragon.y)){
					positions[dragon.x][dragon.y] = empty;
					dragon.x +=1;
					positions[dragon.x][dragon.y] = dragon.image;
					done = true;
				}
				break;
			case 4: // d
				if (checkValidPosition(dragon.x, dragon.y+1)){
					positions[dragon.x][dragon.y] = empty;
					dragon.y +=1;
					positions[dragon.x][dragon.y] = dragon.image;
					done = true;
				}
				break;
			default:
				break;
			}
		}		

	}

	public static void main(String[] args){
		Maze m1 = new Maze();
		m1.startPredefinedMaze();
		m1.printMaze();
		while (!gameOver) {
			m1.moveHero();
			m1.moveDragon();
			m1.printMaze();
		}
		System.out.println(gameOverMessage);
	}
}

class Hero {
	public int x = 0;
	public int y = 0;
	public char image = 'H';
	public boolean isArmed = false;
	
	public void pickWeapon(){
		isArmed = true;
		image = 'A';
	}
}

class Dragon {
	public int x = 0;
	public int y = 0;
	public char image = 'D';
	public boolean isOnSword = false;
	
	public void movesToSword(){
		image = 'F';
		isOnSword = true;
	}
	
	public void movesFromSword(){
		image = 'D';
		isOnSword = false;
	}
}
