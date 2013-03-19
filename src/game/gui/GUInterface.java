package game.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.logic.Game;
import game.ui.GameInterface;
import game.ui.GameOptions;
import game.ui.GameOutput;

public class GUInterface extends GameInterface {

	private void startInterface() {
		final JFrame frame = new JFrame("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.pack();
		frame.setVisible(true);
		
		final MazeDrawer md = new MazeDrawer();
		md.setBackground(new java.awt.Color(255, 255, 255));
		
		Container c = frame.getContentPane();
		c.add(md);


	}


	public void startGame() {
		startInterface();
		/*	
		GameOptions options = new GameOptions();
		game = new Game(options);
		GameOutput.clearScreen();
		GameOutput.printGame(game);
		mainLoop();*/

	}

}
