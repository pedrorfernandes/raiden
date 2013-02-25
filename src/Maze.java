import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

public class Maze {
	private static final int SIZE_H = 10;
	private static final int SIZE_V = 10;
	Dragon dragon = new Dragon(); 
	Hero hero = new Hero();
	static char wall = 'X';
	static char empty = ' ';
	static char space = ' ';
	static char exit = 'S';
	static char weapon = 'E';
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

	static public void print(char[][] maze){
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				System.out.print(maze[x][y]);
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
	
	public static char[][] generateMaze(int rows, int cols){
		// this algorithm only works well if the 
		// width and height are odd
		
		if (cols % 2 == 0){
			cols += 1;
		}
		if (rows % 2 == 0){
			rows +=1;
		}
	
		// fill the maze with walls
		char[][] maze = new char [rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				maze[i][j] = wall;
			}
		}
		
		// create a CellStack (LIFO) to hold a list of cell locations
		Stack<Cell> cellStack = new Stack<Cell>();
		//set TotalCells = number of maximum visited cells
		int totalCells = (cols-2) + (rows-3)*((int)((rows-2)/2)+1);
		
		//choose random starting odd cell, call it currentCell
		Cell currentCell = new Cell();
		Random r = new Random();
		currentCell.i = r.nextInt(cols-1)+1; // 1..cols-1, can't be a wall
		currentCell.j = r.nextInt(cols-1)+1;
		if (currentCell.i % 2 == 0){
			currentCell.i -= 1;
		}
		if (currentCell.j % 2 == 0){
			currentCell.j -=1;
		}
		
		//set VisitedCells = 1 
		int visitedCells = 1;
		
		maze[currentCell.i][currentCell.j] = empty;
		Vector<Cell> nearbyCells = new Vector<Cell>();
		
		while( visitedCells < totalCells){
			//System.out.print(visitedCells + " < " + totalCells + "\n");
			//System.out.print("stack size: " + cellStack.size() + "\n");
			
			// find all 4 neighbors of CurrentCell with all walls intact 
			nearbyCells.clear();
			if(currentCell.i-2 > 0 && currentCell.i-2 < rows && maze[currentCell.i-2][currentCell.j] == wall ){
				nearbyCells.add(new Cell(currentCell.i-2, currentCell.j));
			}
			if(currentCell.j-2 > 0 && currentCell.j-2 < cols && maze[currentCell.i][currentCell.j-2] == wall ){
				nearbyCells.add(new Cell(currentCell.i, currentCell.j-2));
			}
			if(currentCell.i+2 > 0 && currentCell.i+2 < rows && maze[currentCell.i+2][currentCell.j] == wall ){
				nearbyCells.add(new Cell(currentCell.i+2, currentCell.j));
			}
			if(currentCell.j+2 > 0 && currentCell.j+2 < cols && maze[currentCell.i][currentCell.j+2] == wall ){
				nearbyCells.add(new Cell(currentCell.i, currentCell.j+2));
			}
			
			//if one or more found 
			if (nearbyCells.size() > 0){
				// choose one at random  
				int selected = r.nextInt(nearbyCells.size());
				Cell next = nearbyCells.elementAt(selected);
				maze[next.i][next.j] = empty;
				// knock down the wall between currentCell and next cell
				if (currentCell.i == next.i){
					if(currentCell.j > next.j){ // left
						maze[currentCell.i][currentCell.j-1] = empty;
					} else { // right
						maze[currentCell.i][currentCell.j+1] = empty;
					}
				} else {
					if (currentCell.i > next.i){ // top
						maze[currentCell.i-1][currentCell.j] = empty;
					} else {
						maze[currentCell.i+1][currentCell.j] = empty;
					}
				}
				
				//push CurrentCell location on the CellStack
				cellStack.push(next);
				// make the new cell CurrentCell 
				currentCell = next;
				visitedCells += 2;
			} else {
				if (cellStack.empty()){
					break;
				}
				currentCell = cellStack.pop();
			}
			
		}
		
		// generate an exit
		
		// generate hero, dragon and sword

		return maze;
	}

	public static void main(String[] args){
		Maze m1 = new Maze();
		m1.positions = generateMaze(30,30);
		print(m1.positions);
		/*
		Maze m1 = new Maze();
		m1.startPredefinedMaze();
		print(m1.positions);
		while (!gameOver) {
			m1.moveHero();
			m1.moveDragon();
			print(m1.positions);
		}
		System.out.println(gameOverMessage);
		*/
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

class Cell {
	public int i;
	public int j;
	
	public Cell(){
		i = 0;
		j = 0;
	}
	
	public Cell(int row, int col){
		i = row;
		j = col;
	}
}