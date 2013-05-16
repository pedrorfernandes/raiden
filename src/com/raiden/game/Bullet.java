package com.raiden.game;

public class Bullet extends Collidable {
	private static final int RADIUS = 10;
	private static final int SPEED = 15;
	
	public boolean visible, hit;
	public float angle;
	
	private int moveX;
	private int moveY;
		
	public Bullet(){
		this.radius = (int) (RADIUS * scaleX);
		this.speed  = (int) Math.ceil(SPEED * scaleY);		
		this.collisionDamage = 1;
		this.visible = false;
	}
	
	public void fire(int x, int y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.visible = true;
		float radians = (float) Math.toRadians(angle);
		this.moveX = (int) (speed * FastMath.cos(radians));
		this.moveY = (int) (speed * FastMath.sin(-radians));
		this.hit = false;
	}
	
	public void fire(int x, int y, double radians) {
		this.x = x;
		this.y = y;
		this.angle = (float) Math.toDegrees(radians);
		this.visible = true;
		this.moveX = (int) (speed * FastMath.cos((float)radians));
		this.moveY = (int) (speed * FastMath.sin((float)-radians));
		this.hit = false;
	}
	
	public void update(float deltaTime){
		
		if (!visible) return;
		
		x += moveX;
		y += moveY;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;
	}
	
	public boolean isVisible(){
		return visible;
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
		ship.takeDamage(collisionDamage);
		this.visible = false;
		this.hit = true;
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Enemy enemy) {
		enemy.takeDamage(collisionDamage);
		this.visible = false;
		this.hit = true;
	}
}
