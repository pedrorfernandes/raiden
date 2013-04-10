package game.ui;

import game.objects.MazeSymbol;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class MazePictures {
	
	public BufferedImage sword;
	public BufferedImage wall;
	public BufferedImage exit;
	public BufferedImage empty;
	public BufferedImage space;
	
	public BufferedImage hero;
	public BufferedImage armedHero;
	public BufferedImage deadHero;
	
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
	public BufferedImage deadDragon;
	
	public MazePictures() {
		
		try {
			sword = ImageIO.read(getClass().getResource(MazeSymbol.swordPic));
			wall = ImageIO.read(getClass().getResource(MazeSymbol.wallPic));
			exit = ImageIO.read(getClass().getResource(MazeSymbol.exitPic));
			empty = ImageIO.read(getClass().getResource(MazeSymbol.emptyPic));
			space = ImageIO.read(getClass().getResource(MazeSymbol.spacePic));
			
			hero = ImageIO.read(getClass().getResource(MazeSymbol.heroPic));
			armedHero = ImageIO.read(getClass().getResource(MazeSymbol.armedHeroPic));
			deadHero = ImageIO.read(getClass().getResource(MazeSymbol.deadHeroPic));
			
			dragon = ImageIO.read(getClass().getResource(MazeSymbol.dragonPic));
			sleepingDragon = ImageIO.read(getClass().getResource(MazeSymbol.sleepingDragonPic));
			
			guardedSword = ImageIO.read(getClass().getResource(MazeSymbol.guardedSwordPic));
			sleepingGuardedSword = ImageIO.read(getClass().getResource(MazeSymbol.sleepingGuardedSwordPic));
			
			eagle = ImageIO.read(getClass().getResource(MazeSymbol.eaglePic));
			eagleOnHero = ImageIO.read(getClass().getResource(MazeSymbol.eagleOnHeroPic));
			eagleWithSword = ImageIO.read(getClass().getResource(MazeSymbol.eagleWithSwordPic));
			eagleOnWall = ImageIO.read(getClass().getResource(MazeSymbol.eagleOnWallPic));
			eagleOnDragon = ImageIO.read(getClass().getResource(MazeSymbol.eagleOnDragonPic));
			eagleOnSleepingDragon = ImageIO.read(getClass().getResource(MazeSymbol.eagleOnSleepingDragonPic));
			deadDragon = ImageIO.read(getClass().getResource(MazeSymbol.deadDragonPic));

		} catch (IOException ex) {
			System.out.println("Failed to load images");
			System.exit(0);
		}
		
	}


}
