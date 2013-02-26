package game.ui;


import java.util.LinkedList;
import maze_objects.MazeSymbol;
import maze_objects.Maze;

public class GameOutputs {

	public static void printMaze(Maze m) {
		
		char[][] mazePositions = m.getPositions();
		
		for(int i = 0; i < 100; i++)
			System.out.println();
		for (int x = 0; x < m.getRows(); x++) {
			for (int y = 0; y < m.getColumns(); y++) {
				System.out.print(mazePositions[x][y]);
				System.out.print(MazeSymbol.space);
			}
			System.out.print('\n');
		}
	}
	
	public static void printEventQueue(LinkedList<GameEvent> events) {
		while(!events.isEmpty())
			printEvent(events.removeFirst());
	}
	
	public static void printEvent(GameEvent ev) {
		System.out.println(ev.getMessage());
	}
	
	
	
}
