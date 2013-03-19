package game.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze_objects.MazeSymbol;

public class MazePictures {
	
	public BufferedImage sword;
	public BufferedImage wall;
	public BufferedImage exit;
	public BufferedImage empty;
	public BufferedImage space;
	
	public BufferedImage hero;
	public BufferedImage armedHero;
	
	public BufferedImage dragon;
	public BufferedImage sleepingDragon;
	
	public BufferedImage guardedSword;
	public BufferedImage sleepingGuardedSword;
	
	public BufferedImage eagle;
	public BufferedImage eagleOnHero;
	public BufferedImage eagleWithSword;
	public BufferedImage eagleOnWall;
	public BufferedImage eagleOnDragon;
	public BufferedImage eagleOnSleepingDragon;
	
	public MazePictures() {
		
		try {
			sword = ImageIO.read(new File(MazeSymbol.swordPic));
			wall = ImageIO.read(new File(MazeSymbol.wallPic));
			exit = ImageIO.read(new File(MazeSymbol.exitPic));
			empty = ImageIO.read(new File(MazeSymbol.emptyPic));
			space = ImageIO.read(new File(MazeSymbol.spacePic));
			
			hero = ImageIO.read(new File(MazeSymbol.heroPic));
			armedHero = ImageIO.read(new File(MazeSymbol.armedHeroPic));
			
			dragon = ImageIO.read(new File(MazeSymbol.dragonPic));
			sleepingDragon = ImageIO.read(new File(MazeSymbol.sleepingDragonPic));
			
			guardedSword = ImageIO.read(new File(MazeSymbol.guardedSwordPic));
			sleepingGuardedSword = ImageIO.read(new File(MazeSymbol.sleepingGuardedSwordPic));
			
			eagle = ImageIO.read(new File(MazeSymbol.eaglePic));
			eagleOnHero = ImageIO.read(new File(MazeSymbol.eagleOnHeroPic));
			eagleWithSword = ImageIO.read(new File(MazeSymbol.eagleWithSwordPic));
			eagleOnWall = ImageIO.read(new File(MazeSymbol.eagleOnWallPic));
			eagleOnDragon = ImageIO.read(new File(MazeSymbol.eagleOnDragonPic));
			eagleOnSleepingDragon = ImageIO.read(new File(MazeSymbol.eagleOnSleepingDragonPic));
		} catch (IOException ex) {
			System.out.println("Failed to load images");
		}
		
	}


}
