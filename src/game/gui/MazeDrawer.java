package game.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import game.ui.GameOutput;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import maze_objects.Maze;
import maze_objects.MazeSymbol;

public class MazeDrawer extends JPanel {

	private Maze maze;

	public MazeDrawer(Maze maze) {
		this.maze = maze;
		setPreferredSize(new Dimension(30,30));
		setBackground(new Color(255, 255, 255));
	}

}
