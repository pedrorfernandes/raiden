package game.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import game.logic.Game;
import game.ui.GameInterface;
import game.ui.GameOptions;
import game.ui.GameOutput;
import game.ui.MazePictures;
import general_utilities.MazeInput;
import general_utilities.WaitTime;

public class GUInterface extends GameInterface {
	
	public static final int SPRITESIZE = 32;
	//private static final int OFFSET = 22; // the title of the window
	private final MazePictures mazePictures = new MazePictures();
	private final JFrame frame = new JFrame("Maze");
	private MazePanel mazePanel;
	private InfoPanel infoPanel;

	private void startInterface() {
		
		Dimension mazePanelDimension = new Dimension(game.getMaze().getColumns() * GUInterface.SPRITESIZE,
				game.getMaze().getRows() * GUInterface.SPRITESIZE);
		
		Dimension infoPanelDimension = new Dimension(game.getMaze().getColumns() * GUInterface.SPRITESIZE,
				100);

		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		
		mazePanel = new MazePanel(game, mazePictures, mazePanelDimension);
		infoPanel = new InfoPanel(infoPanelDimension);
		c.add(mazePanel);
		c.add(infoPanel, BorderLayout.PAGE_START);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.pack();
		frame.setVisible(true);
	}


	public void startGame() {
		GameOptions options = new GameOptions();
		game = new Game(options);
		startInterface();
		//GameOutput.printGame(game, mazePanel.getGraphics(), mazePictures);
		mainLoop();
	}
	
	private void mainLoop() {

		boolean goOn = true;
		char input = ' ';

		while(goOn){

			input = mazePanel.getNextKey();
			while( input == '\n'){
				WaitTime.wait(50);
				//GameOutput.printGame(game, mazePanel.getGraphics(), mazePictures);
				input = mazePanel.getNextKey();
			}

			goOn = game.heroTurn(input);

			//GameOutput.printGame(game, mazePanel.getGraphics(), mazePictures);
			mazePanel.repaint();
			WaitTime.wait(125);

			goOn = game.dragonTurn(goOn);
			//GameOutput.printGame(game, mazePanel.getGraphics(), mazePictures);
			mazePanel.repaint();
			WaitTime.wait(125);

			goOn = game.checkState(goOn);

			/*GameOutput.clearScreen();
			GameOutput.printEventQueue(game.getEvents() );*/
			//GameOutput.printGame(game, mazePanel.getGraphics(), mazePictures);
			mazePanel.repaint();
			GameOutput.printEventQueue(game.getEvents(), infoPanel);
			WaitTime.wait(125);

		}
	}

}
