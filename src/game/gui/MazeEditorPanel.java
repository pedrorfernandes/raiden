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
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import maze_objects.Dragon;
import maze_objects.Maze;
import maze_objects.Movable;
import maze_objects.Sword;
import maze_objects.Tile;

public class MazeEditorPanel extends JDialog implements ActionListener {

	private MazeObjectToDraw currentObject = new MazeObjectToDraw();
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

	public MazeEditorPanel(final Game game, MazePictures pictures) {
		this.game = game;
		this.pictures = pictures;

		setTitle("Maze Editor");

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnFloor = new JButton("Floor");
		toolBar.add(btnFloor);

		btnFloor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentObject.set(Tile.empty);
			}
		});

		JButton btnWall = new JButton("Wall");
		toolBar.add(btnWall);

		btnWall.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentObject.set(Tile.wall);
			}
		});

		JButton btnExit = new JButton("Exit");
		toolBar.add(btnExit);

		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentObject.set(Tile.exit);
			}
		});

		JButton btnDragon = new JButton("Dragon");
		toolBar.add(btnDragon);

		btnDragon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentObject.set(new Dragon(0, 0, options.dragonType));
			}
		});

		JButton btnSword = new JButton("Sword");
		toolBar.add(btnSword);

		btnSword.addActionListener(new SetSword());

		JButton btnHero = new JButton("Hero");
		toolBar.add(btnHero);

		btnHero.addActionListener(new SetHero());

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
		this.game = new Game(options);
		this.game.setMaze(new Maze(options.rows, options.columns, true));
		this.game.getHero().print = false;
		this.game.getSword().print = false;

		MazePainterPanel mazePainter = new MazePainterPanel(pictures, currentObject, this.game, this.options);
		getContentPane().add(mazePainter, BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();                                   
		setVisible(true);
	}

	private void updateOptions() {

		options.randomMaze = true;
		options.rows = maze_rows;
		options.columns = maze_columns;

		options.dragonType = dragonType;

		options.multipleDragons = useMultipleDragons;

		options.randomSpawns = false;
	}

	class SetHero implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			currentObject.set(game.getHero());
		}

	}

	class SetSword implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			currentObject.set(game.getSword());
		}

	}

}

class MazePainterPanel extends JPanel implements MouseListener {

	private Game game;
	private MazePictures pictures;
	private MazeObjectToDraw currentObject;
	private ArrayList<Movable> movableObjects = new ArrayList<Movable>();

	public MazePainterPanel(MazePictures pictures, MazeObjectToDraw currentObject, Game game, GameOptions options) {

		this.game = game;
		this.pictures = pictures;
		this.currentObject = currentObject;

		setFocusable(true);
		addMouseListener(this);

		setPreferredSize(new Dimension(options.rows * 32, options.columns * 32));
	}

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
		int printRow = e.getY() / 32;
		int printColumn = e.getX() / 32;

		System.out.println("mouse pressed!");
		if(currentObject.isMovable) {
			deleteObjectOn(printRow, printColumn);
			currentObject.movable.setRow(printRow);
			currentObject.movable.setColumn(printColumn);
			currentObject.movable.print = true;
			movableObjects.add(currentObject.movable);

			if(currentObject.movable instanceof Dragon) {
				game.addDragon((Dragon) currentObject.movable);
			}
		}
		else
			game.getMaze().getPositions()[printRow][printColumn] = currentObject.tile;

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	private void deleteObjectOn(int row, int column) {

		Iterator<Movable> iter = movableObjects.iterator(); 
		
		if(movableObjects != null)
			while(iter.hasNext()) {
				Movable movable = iter.next();

				if(movable.getRow() == row && movable.getColumn() == column) {

					if(movable instanceof Dragon) {
						Iterator<Dragon> dragonIter = game.getDragons().iterator(); 
						
						while(dragonIter.hasNext()) {
							Dragon dragon = dragonIter.next();
							
							if(dragon.getRow() == row && dragon.getColumn() == column)
								dragonIter.remove();
						}
					}

					movable.setRow(0);
					movable.setColumn(0);
					movable.print = false;
					iter.remove();
				}
			}

	}

}
