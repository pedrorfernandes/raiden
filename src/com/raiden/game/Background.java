package com.raiden.game;

import java.util.ArrayList;
import java.util.Random;


import android.graphics.Color;

import com.raiden.framework.Graphics;
import com.raiden.framework.Image;

public class Background {
	public static Image backgroundImage = Assets.background;
	public static Image cloudImage = Assets.cloud;
	
	private Random random;
	private static final int NUMBER_OF_CLOUDS = 6;
	private static final int CLOUD_BOUNDS = 200;
	
	private ArrayList<Cloud> clouds;
	
	int x, yTop, yBottom, speed, cloudSpeed;
	private GameScreen gameScreen;
	
	public Background(GameScreen gameScreen){
		x = 0;
		yTop = -backgroundImage.height;
		yBottom = 0;
		speed = 1;
		cloudSpeed = speed*2;
		this.gameScreen = gameScreen;
		this.random = new Random();
		this.clouds = new ArrayList<Cloud>();
		for (int i = 0; i < NUMBER_OF_CLOUDS; i++) {
			clouds.add(new Cloud(random.nextInt(gameScreen.screenSize.x), random.nextInt(gameScreen.screenSize.y)));
		}
	}
	
	public void update(float deltaTime){
		yTop += speed;
		yBottom += speed;
		
		if (yBottom  > backgroundImage.height ){
			yTop = -backgroundImage.height;
			yBottom = 0;
		}
		
		for (Cloud cloud : clouds) {
			cloud.y += cloudSpeed;
			if (cloud.y > gameScreen.screenSize.y + CLOUD_BOUNDS){
				cloud.x = random.nextInt(gameScreen.screenSize.x);
				cloud.y = -CLOUD_BOUNDS;
			}
		}
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, x, yTop);
		g.drawImage(backgroundImage, x, yBottom);
		
		for (Cloud cloud : clouds) {
			g.drawImage(cloudImage, cloud.x - cloudImage.halfWidth, cloud.y - cloudImage.halfHeight);
		}
	}
	
	class Cloud {
		int x, y;
		
		public Cloud(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}