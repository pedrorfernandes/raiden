package com.raiden.game;

public class Bullet {
	private int x, y, speed;
	private boolean visible;
	private float angle;
	private int radius;
	
	private static int minX = 0;
	private static int minY = 0;
	private static int maxX = GameScreen.screenSize.x - 1;
	private static int maxY = GameScreen.screenSize.y - 1;
	
	private int moveX;
	private int moveY;
	
	public Bullet(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = (float)angle;
		this.speed = 15;
		this.radius = 5;
		this.visible = true;
		double radians = Math.toRadians(angle);
		this.moveX = (int)(speed * Math.cos(radians));
		this.moveY = (int)(speed * Math.sin(-radians));
	}
	
	public void update(){
		
		x += moveX;
		y += moveY;

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
	
	public float getAngle() {
		return angle;
	}
}
