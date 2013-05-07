package com.raiden.game;

public class Enemy extends Collidable {
	private int x, y, speed;
	private boolean visible, outOfRange;
	public boolean alive;
	private float angle;
	private double radians;
	
	private static int minX = 0;
	private static int minY = 0;
	private static int maxX = GameScreen.screenSize.x - 1;
	private static int maxY = GameScreen.screenSize.y - 1;
	
	private static final int BOUNDS = 100;
	private static int outMinX = - BOUNDS;
	private static int outMinY = - BOUNDS;
	private static int outMaxX = GameScreen.screenSize.x + BOUNDS;
	private static int outMaxY = GameScreen.screenSize.y + BOUNDS;
	
	private int moveX;
	private int moveY;
	
	public int health;
	
	public Enemy(int x, int y, double angle) {
		
		this.hitX = x;
		this.hitY = y;
		this.radius = 70;
		
		this.x = x;
		this.y = y;
		this.angle = (float)angle;
		this.speed = 7;
		this.radius = 5;
		this.visible = true;
		this.outOfRange = false;
		this.radians = Math.toRadians(angle);
		this.moveX = (int)(speed * Math.cos(radians));
		this.moveY = (int)(speed * Math.sin(-radians));
		
		this.health = 40;
		this.alive = true;
	}
	
	public void update(){
		
		if (health < 0) alive = false;
		
		// out of range enemies must be destroyed
		if (outOfRange || !alive) return;
		
		x += moveX;
		y += moveY;
		hitX = x;
		hitY = y;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;

		if ( !visible ){
			// if the enemy is out of bounds, it must be removed from the memory
			if (x < outMinX || y < outMinY || x > outMaxX || y > outMaxY)
				outOfRange = true;
		}
		
		for (Bullet bullet : Ship.shotsFired) {
			this.checkCollision(bullet);
		}
		
	}
	
	public void turn(float degrees){
		// this will turn the ship
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public boolean isOutOfRange(){
		return outOfRange;
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
	
	public void accept(Collidable other) {
		other.visit(this);
	}
	
	@Override
	public void visit(Ship ship) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Enemy enemy) {
		// TODO Auto-generated method stub
		
	}
}
