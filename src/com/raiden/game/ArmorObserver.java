package com.raiden.game;

import java.util.ArrayList;
import java.util.Random;

import com.raiden.framework.Image;

public class ArmorObserver implements Observer {
	
	private Random random;
	private int maxX, maxY;
	
	public ArrayList<ScreenCrack> screenCracks;
	
	public ArmorObserver(GameScreen gameScreen){
		this.maxX = gameScreen.screenSize.x;
		this.maxY = gameScreen.screenSize.y;
		screenCracks = new ArrayList<ScreenCrack>();
		random = new Random();
	}

	@Override
	public void update(int x, int y, Event event) {
		if (event == Event.HeroHit){
			ScreenCrack screenCrack = new ScreenCrack(random.nextInt(maxX), random.nextInt(maxY));
			screenCracks.add(screenCrack);
		} else if (event == Event.PowerUp && event.powerUpType == PowerUp.Type.Repair){
			screenCracks.clear();
		}
	}

	@Override
	public void update(Collidable c, Event event) {
		this.update(c.x, c.y, event);
	}

}

class ScreenCrack {
	public int x, y;
	public static Image image = Assets.screenCrack;
	
	public ScreenCrack(int x, int y) {
		this.x = x;
		this.y = y;
	}
}