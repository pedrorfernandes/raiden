package game.gui;

import game.logic.Game;
import game.ui.GameOutput;
import game.ui.MazePictures;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class MazePanel extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Game game;
	MazePictures pictures;
	
	private LinkedList<Character> keys = new LinkedList<Character>();

	public MazePanel(Game game, MazePictures pictures, Dimension dimension) {
		addKeyListener(this);
		setBackground(Color.WHITE);
		setFocusable( true );
		setPreferredSize(dimension);
		
		this.game = game;
		this.pictures = pictures;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GameOutput.printGame(game, g, pictures);
	}
	
	public char getNextKey() {
		if (keys.isEmpty() ) 
			return '\n';
		else 
			return keys.removeFirst();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
        	keys.add('w');
        }
        if (e.getKeyChar() == 'a') {
        	keys.add('a');
        }
        if (e.getKeyChar() == 's') {
        	keys.add('s');
        }
        if (e.getKeyChar() == 'd') {
        	keys.add('d');
        }
        if (e.getKeyChar() == 'e') {
        	keys.add('e');
        }
        if (e.getKeyChar() == 'z') {
        	keys.add('z');
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        	keys.add(' ');
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}
