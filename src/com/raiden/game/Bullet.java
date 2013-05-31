package com.raiden.game;

import com.raiden.framework.Image;

public class Bullet extends Collidable {
	private static final int RADIUS = 10;
	private static final int SPEED = 15;
	
	public boolean visible, hit;
	public float angle;
	
	private int moveX;
	private int moveY;
	
	public Type type;
	
	public static enum Type{
		Hero        (15, 1, 10, Assets.heroBullet1),
		Enemy       (10, 1, 10, Assets.enemyBullet1),
		EnemyHeavy  ( 7, 1, 10, Assets.enemyBullet2);
		
		public int speed, damage, radius;
		public Image image;
		
		Type(int speed, int damage, int radius, Image image){
			this.speed = speed;
			this.damage = damage;
			this.radius = radius;
			this.image = image;
		}
	}
	
		
	public Bullet(){
		this.radius = RADIUS;
		this.speed  = SPEED;		
		this.collisionDamage = 1;
		this.visible = false;
		this.setType(Type.Hero); // initializes the enum
	}
	
	public void setType(Type type){
		this.type = type;
		this.speed = type.speed;
		this.collisionDamage = type.damage;
		this.radius = type.radius;
	}
	
	public void fire(int x, int y, float angle, Type type) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.visible = true;
		this.setType(type);
		float radians = (float) Math.toRadians(angle);
		this.moveX = (int) (speed * FastMath.cos(radians));
		this.moveY = (int) (speed * FastMath.sin(-radians));
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
	
	@Override
	public void visit(Ship ship) {
		ship.takeDamage(this);
		this.visible = false;
		this.hit = true;
	}

	@Override
	public void visit(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}
}
