package game.gui;

import game.logic.Game;
import game.ui.GameOptions;
import game.ui.GameOutput;
import game.ui.MazePictures;
import general_utilities.MazeInput;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import maze_objects.Dragon;
import maze_objects.Maze;
import maze_objects.Tile;

public class MazeEditorPanel extends JDialog implements ActionListener {

	private MazeObjectToDraw currentObject;
	private Game game;
	private MazePictures pictures;
	private GameOptions options = new GameOptions(false);

	private int maze_rows;
	private int maze_columns;
	private int dragonType;
	private boolean useMultipleDragons;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public MazeEditorPanel(Game game, MazePictures pictures) {
		this.game = game;
		this.pictures = pictures;

		setTitle("Maze Editor");

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnFloor = new JButton("Floor");
		toolBar.add(btnFloor);

		JButton btnWall = new JButton("Wall");
		toolBar.add(btnWall);

		JButton btnExit = new JButton("Exit");
		toolBar.add(btnExit);

		JButton btnDragon = new JButton("Dragon");
		toolBar.add(btnDragon);

		JButton btnSword = new JButton("Sword");
		toolBar.add(btnSword);

		JButton btnHero = new JButton("Hero");
		toolBar.add(btnHero);


		String rows;
		String columns;

		do {
			rows = JOptionPane.showInputDialog(this, "Number of rows? (Min. 6!)");
		}
		while(!MazeInput.isInteger(rows) && rows != null);

		if(rows == null)
			return;

		do {
			columns = JOptionPane.showInputDialog(this, "Number of columns? (Min. 6!)");
		}
		while(!MazeInput.isInteger(columns) && columns != null);

		if(columns == null)
			return;

		if(Integer.parseInt(rows)  < 6 || Integer.parseInt(columns) < 6) {
			JOptionPane.showMessageDialog(this,
					"Invalid row and/or column number detected, using 10 for both!",
					"Invalid input error",
					JOptionPane.ERROR_MESSAGE);

			maze_rows = 10;
			maze_columns = 10;
		}
		else {
			maze_rows = Integer.parseInt(rows);
			maze_columns = Integer.parseInt(columns);
		}

		String[] possibilities = {"Randomly sleeping", "Always awake", "Static"};
		String dragonOption = (String)JOptionPane.showInputDialog(
				this,
				"Dragon type:",
				"Dragon type",
				JOptionPane.QUESTION_MESSAGE,
				null,
				possibilities,
				possibilities[0]);

		if(dragonOption == null)
			return;
		if(dragonOption.equals( "Randomly sleeping" ))
			dragonType = Dragon.SLEEPING;
		else if(dragonOption.equals( "Always awake" ))
			dragonType = Dragon.NORMAL;
		else
			dragonType = Dragon.STATIC;

		int multipleDragonsOption = JOptionPane.showConfirmDialog(
				this,
				"Create a number of dragons proportional to the maze size?",
				"Multiple dragons",
				JOptionPane.YES_NO_OPTION);

		if(multipleDragonsOption == JOptionPane.YES_OPTION)
			useMultipleDragons = true;
		else
			useMultipleDragons = false;

		updateOptions();

		MazePainterPanel mazePainter = new MazePainterPanel(pictures, currentObject, options);
		getContentPane().add(mazePainter, BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();                                   
		setVisible(true);
	}

	private void updateOptions() {
		
		options.rows = maze_rows;
		options.columns = maze_columns;

		options.dragonType = dragonType;

		options.multipleDragons = useMultipleDragons;

		options.randomSpawns = false;
	}

}

class MazePainterPanel extends JPanel implements MouseListener {

	private Game game;
	private MazePictures pictures;
	private MazeObjectToDraw currentObject;
	private GameOptions options;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GameOutput.printGame(game, g, pictures);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed!");
		game.getMaze().getPositions()[e.getY()/32][e.getX()/32] = Tile.exit;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	public MazePainterPanel(MazePictures pictures, MazeObjectToDraw currentObject, GameOptions options) {
		game = new Game(options);
		game.setMaze(new Maze(options.rows, options.columns, true));
		this.pictures = pictures;
		this.currentObject = currentObject;
		this.options = options;

		setFocusable(true);
		addMouseListener(this);

		setPreferredSize(new Dimension(options.rows * 32, options.columns * 32));
	}

}
