package game.gui;

import java.awt.Dimension;

import game.ui.GameOutput;

import javax.swing.JPanel;

import maze_objects.Maze;
import maze_objects.MazeSymbol;

public class MazeDrawer extends JPanel {

	private Maze maze;

	public static void printMaze(Maze m) {

		char[][] mazePositions = GameOutput.getMazeSymbols(m);

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

	public MazeDrawer() {
		setPreferredSize(new Dimension(30,30));
	}

}
