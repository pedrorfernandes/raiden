package com.raiden.game;

public class Bullet {
	private int x, y, speed;
	private boolean visible;
	private double angle;
	private int radius;
	
	private static int minX = 0;
	private static int minY = 0;
	private static int maxX = GameScreen.screenSize.x - 1;
	private static int maxY = GameScreen.screenSize.y - 1;

	public Bullet(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = Math.toRadians(angle);
		this.speed = 10;
		this.radius = 5;
		this.visible = true;
	}
	
	public void update(){
		
		x += (int)(speed * Math.cos(angle));
		y += (int)(speed * Math.sin(-angle));

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;

		if (visible){
			//checkCollision();
		}
		
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
