package com.raiden.game;

import com.raiden.framework.Image;

/**
 * A bullet is fired by turrets that belong to a ship.
 * It is usually a fast, small and high powered collidable.
 */
public class Bullet extends Collidable {
	private static final int RADIUS = 10;
	private static final int SPEED = 15;
	
	public boolean visible, hit;
	public float angle;
	
	private int moveX;
	private int moveY;
	
	public Type type;
	
	/**
	 * The various types of bullets available.
	 */
	public static enum Type{
		Hero        (15, 1, 10, Assets.heroBullet1),
		HeroHeavy   (10, 2, 20, Assets.heroBullet2),
		Enemy       (10, 1, 10, Assets.enemyBullet1),
		EnemyHeavy  ( 7, 2, 20, Assets.enemyBullet2);
		
		public int speed, damage, radius;
		public Image image;
		
		/**
		 * Constructor for a type of bullet.
		 * @param speed The bullet's speed.
		 * @param damage The bullet's damage on impact.
		 * @param radius The bullet's radius size.
		 * @param image The bullet's representation.
		 */
		Type(int speed, int damage, int radius, Image image){
			this.speed = speed;
			this.damage = damage;
			this.radius = radius;
			this.image = image;
		}
	}
	
	/**
	 * Constructor for a bullet. It only initializes simple parameters.
	 * To make a bullet appear in game, check bullet.fire()
	 */
	public Bullet(){
		this.radius = RADIUS;
		this.speed  = SPEED;		
		this.collisionDamage = 1;
		this.visible = false;
		this.setType(Type.Hero); // initializes the enum
	}
	
	/**
	 * Sets the current bullet to a new type.
	 * @param type The new type of bullet.
	 */
	public void setType(Type type){
		this.type = type;
		this.speed = type.speed;
		this.collisionDamage = type.damage;
		this.radius = type.radius;
	}
	
	/**
	 * Makes a bullet appear in game.
	 * @param x The starting X position.
	 * @param y The starting Y position.
	 * @param angle The angle that defines the bullet's direction.
	 * @param type The type of the bullet fired.
	 */
	public void fire(int x, int y, float angle, Type type) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.visible = true;
		this.setType(type);
		float radians = (float) Math.toRadians(angle);
		this.moveX = Math.round(speed * FastMath.cos( radians));
		this.moveY = Math.round(speed * FastMath.sin(-radians));
		this.hit = false;
	}

	/**
	 * Updates the bullet position unless it is out of the game.
	 * @param deltaTime The time passed since the last update.
	 */
	public void update(float deltaTime){
		
		if (!visible) return;
		
		x += moveX;
		y += moveY;

		if (x < minX || y < minY || x > maxX || y > maxY)
			visible = false;
	}
	
	/**
	 * @return If the bullet is visible.
	 */
	public boolean isVisible(){
		return visible;
	}
	
	/**
	 * @return The direction (angle) of the bullet.
	 */
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
		// nothing happens
		return;
	}
	
	@Override
	public void visit(PowerUp powerUp) {
		// nothing happens
		return;
	}
}
