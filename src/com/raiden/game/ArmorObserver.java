package com.raiden.game;

import java.util.ArrayList;
import java.util.Random;

import com.raiden.framework.Image;

/**
 * The armor observer watches the hero's armor so that it can show visual clues for damage.
 *  (in this case, being hit is represented by a random screen crack)
 */
public class ArmorObserver implements Observer {
	
	private GameScreen gameScreen;
	private Random random;
	private int maxX, maxY;
	private int armor;
	
	/**
	 * The screen cracks array that will be represented by the game screen.
	 */
	public ArrayList<ScreenCrack> screenCracks;
	
	public ArmorObserver(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		this.armor = gameScreen.hero.armor;
		this.maxX = gameScreen.screenSize.x;
		this.maxY = gameScreen.screenSize.y;
		screenCracks = new ArrayList<ScreenCrack>();
		random = new Random();
	}

	@Override
	public void update(int x, int y, Event event) {
		if (event == Event.HeroHit){
			int armorLost = armor - gameScreen.hero.armor;
			armor = gameScreen.hero.armor;
			while (armorLost > 0){
				ScreenCrack screenCrack = new ScreenCrack(random.nextInt(maxX), random.nextInt(maxY));
				screenCracks.add(screenCrack);
				armorLost--;
			}
		} else if (event == Event.PowerUp && event.powerUpType == PowerUp.Type.Repair){
			screenCracks.clear();
		} else if (event == Event.GameOver){
			gameScreen.setLivesLeft(gameScreen.getLivesLeft() - 1);
		}	
	}

	@Override
	public void update(Collidable c, Event event) {
		this.update(c.x, c.y, event);
	}

}

/**
 * Simple screen crack with position and associated image.
 */
class ScreenCrack {
	public int x, y;
	public static Image image = Assets.screenCrack;
	
	public ScreenCrack(int x, int y) {
		this.x = x;
		this.y = y;
	}
}