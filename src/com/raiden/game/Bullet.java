package com.raiden.game;

public class Bullet extends Collidable {
	private int speed;
	public boolean visible, hit;
	public float angle;
	
	private static int minX = 0;
	private static int minY = 0;
	private static int maxX = GameScreen.screenSize.x - 1;
	private static int maxY = GameScreen.screenSize.y - 1;
	
	private int moveX;
	private int moveY;
	
	private int damage;
	
	public Bullet(){
		this.radius = (int) (10 * GameScreen.scaleX);
		this.speed  = (int) Math.ceil(15 * GameScreen.scaleY);		
		this.damage = 1;
		this.visible = false;
	}
	
	public void fire(int x, int y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.visible = true;
		double radians = Math.toRadians(angle);
		this.moveX = (int) (speed * Math.cos(radians));
		this.moveY = (int) (speed * Math.sin(-radians));
		this.hit = false;
	}
	
	public void fire(int x, int y, double radians) {
		this.x = x;
		this.y = y;
		this.angle = (float)Math.toDegrees(radians);
		this.visible = true;
		this.moveX = (int)(speed * Math.cos(radians));
		this.moveY = (int)(speed * Math.sin(-radians));
		this.hit = false;
	}
	
	public void update(){
		
		if (!visible) return;
		
		x += moveX;
		y += moveY;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;
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
	
	public void accept(Collidable other) {
		other.visit(this);
	}
	
	public boolean checkHit(){
		if (this.hit){
			this.hit = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void visit(Ship ship) {
		this.visible = false;
		this.hit = true;
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Enemy enemy) {
		enemy.health -= damage;
		this.visible = false;
		this.hit = true;
	}
}
