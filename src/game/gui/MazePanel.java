package game.gui;

import game.ui.GameEvent;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class MazePanel extends JPanel implements KeyListener {
	
	private LinkedList<Character> keys = new LinkedList<Character>();

	public MazePanel() {
		addKeyListener(this);
		setBackground(Color.WHITE);
	}
	
	public char getNextKey() {
		return keys.removeFirst();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys.add(e.toString().charAt(0));
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
