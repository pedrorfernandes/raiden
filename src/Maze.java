import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class Maze {
	private static final int SIZE_H = 10;
	private static final int SIZE_V = 10;
	Dragon dragon = new Dragon(); 
	Hero hero = new Hero();
	char wall = 'X';
	char empty = ' ';
	char space = ' ';
	char exit = 'S';
	char weapon = 'E';
	static boolean gameOver = false;
	static String gameOverMessage = "Game Over";
	private static final int DRAGONCHOICES = 5;

	private char[][] positions = new char[SIZE_H][SIZE_V];

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
				if ( x == 0 || x == SIZE_H-1  || y == 0 || y == SIZE_V-1){
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
		positions[2][4] = weapon;
		positions[4][4] = weapon;

	}

	public void printMaze(){
		for (int x = 0; x < SIZE_H; x++) {
			for (int y = 0; y < SIZE_V; y++) {
				System.out.print(positions[x][y]);
				System.out.print(space);
			}
			System.out.print('\n');
		}
	}

	public boolean checkPosition(int x, int y){
		if(x <= 0 || x >= SIZE_V || y <= 0 || y >= SIZE_H){
			return false;
		}
		if (positions[x][y] == dragon.image ) {
			if(hero.isArmed){
				// dragon was slain
				dragon.die();
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
			if (checkPosition(hero.x-1, hero.y)){
				positions[hero.x][hero.y] = empty;
				hero.x -=1;
				positions[hero.x][hero.y] = hero.image;
				return true;
			}
			break;
		case "a":
			if (checkPosition(hero.x, hero.y-1)){
				positions[hero.x][hero.y] = empty;
				hero.y -=1;
				positions[hero.x][hero.y] = hero.image;	
				return true;
			}
			break;
		case "s":
			if (checkPosition(hero.x+1, hero.y)){
				positions[hero.x][hero.y] = empty;
				hero.x +=1;
				positions[hero.x][hero.y] = hero.image;
				return true;
			}
			break;
		case "d" :
			if (checkPosition(hero.x, hero.y+1)){
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
		if(x <= 0 || x >= SIZE_V || y <= 0 || y >= SIZE_H){
			return false;
		} 
		if (positions[x][y] == weapon ) {
			dragon.toggleInSword();
			return true;
		}
		if (positions[x][y] == empty && dragon.isOnSword){
			dragon.toggleInSword();
			return true;
		}
		if (positions[x][y] == empty ){
			return true;
		}
		else return false;
	}
	
	public void moveDragon(){
		if ( ! dragon.isAlive ){
			return;
		}
		Random r = new Random();
		boolean done = false;
		char block;
		if (dragon.isOnSword){
			block = weapon;
		} else {
			block = empty;
		}
		while (!done){
			int move = r.nextInt(DRAGONCHOICES);	
			switch (move) {
			case 0:
				done = true;
				break;
			case 1: // w
				if (checkDragonPosition(dragon.x-1, dragon.y)){
 					positions[dragon.x][dragon.y] = block;
					dragon.x -=1;
					positions[dragon.x][dragon.y] = dragon.image;
					done = true;
				}
				break;
			case 2: // a
				if (checkDragonPosition(dragon.x, dragon.y-1)){
					positions[dragon.x][dragon.y] = block;
					dragon.y -=1;
					positions[dragon.x][dragon.y] = dragon.image;	
					done = true;
				}
				break;
			case 3: // s
				if (checkDragonPosition(dragon.x+1, dragon.y)){
					positions[dragon.x][dragon.y] = block;
					dragon.x +=1;
					positions[dragon.x][dragon.y] = dragon.image;
					done = true;
				}
				break;
			case 4: // d
				if (checkDragonPosition(dragon.x, dragon.y+1)){
					positions[dragon.x][dragon.y] = block;
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
	
	public char[][] generateMaze(int width, int height){
		char[][] maze = new char [width][height];
		/*
create a CellStack (LIFO) to hold a list of cell locations  
set TotalCells = number of cells in grid  
choose a cell at random and call it CurrentCell  
set VisitedCells = 1  

while VisitedCells < TotalCells 
	find all neighbors of CurrentCell with all walls intact   
	if one or more found 
		choose one at random  
		knock down the wall between it and CurrentCell  
		push CurrentCell location on the CellStack  
		make the new cell CurrentCell  
		add 1 to VisitedCells
	else 
		pop the most recent cell entry off the CellStack  
		make it CurrentCell
	endIf
endWhile  
		*/
		
		
		
		return maze;
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
	public boolean isAlive = true;
	public boolean isOnSword = false;
	
	public void toggleInSword(){
		if (isOnSword){
			image = 'D';
			isOnSword = false;
		} else {
			image = 'F';
			isOnSword = true;
		}
	}
	
	public void die(){
		isAlive = false;
	}
}