package com.raiden.game;

import android.graphics.Point;

/**
 * A turret belongs to a ship and fires ship.shots.
 */
public class Turret {
	private Ship ship;
	private Collidable target;
	private Point position;
	private float angle;
	private double radians;
	public Bullet.Type bulletType;

	/**
	 * Creates a new turret.
	 * @param ship The ship it belongs to.
	 * @param position The position relative to the ship's center.
	 * @param angle The angle of firing.
	 * @param bulletType The type of ship.shots to fire.
	 */
	public Turret(Ship ship, Point position, float angle, Bullet.Type bulletType) {
		this.ship = ship;
		this.position = position;
		this.angle = angle;
		this.bulletType = bulletType;
	}

	/**
	 * Creates a new turret.
	 * @param ship The ship it belongs to.
	 * @param position The position relative to the ship's center.
	 * @param target The target to fire at.
	 * @param bulletType The type of ship.shots to fire.
	 */
	public Turret(Ship ship, Point position, Collidable target, Bullet.Type bulletType) {
		this.ship = ship;
		this.position = position;
		this.target = target;
		this.bulletType = bulletType;
	}

	/**
	 * Fires a bullet in the firing angle or at a target (if not null).
	 */
	public void fire(){
		if (target == null) {
			int length = ship.shots.length;
			for (int i = 0; i < length; i++) {
				if ( !ship.shots[i].visible ){
					ship.shots[i].fire(ship.x + position.x, 
                                    ship.y + position.y,
                                    angle, bulletType);
					return;
				}
			}
		} else {
			int length = ship.shots.length;
			for (int i = 0; i < length; i++) 
			{
				if ( !ship.shots[i].visible )
				{
					// calculate the angle between two points
					int deltaX = target.x - ship.x;
					int deltaY = ship.y - target.y;
					radians = FastMath.atan2(deltaY, deltaX);
					angle = (float) Math.toDegrees(radians);
					ship.shots[i].fire(ship.x + position.x, 
                                    ship.y + position.y, 
                                    angle, bulletType);
					return;
				}
			}
		}
	}

	/**
	 * Sets a target for firing.
	 * @param target The new target.
	 */
	public void setTarget(Collidable target){
		this.target = target;
	}
	
	/**
	 * Sets the bullet type to fire.
	 * @param bulletType The new bullet type.
	 */
	public void setBulletType(Bullet.Type bulletType){
		this.bulletType = bulletType;
	}
	
	/**
	 * Sets the firing angle.
	 * @param angle The new firing angle.
	 */
	public void setAngle(float angle){
		this.angle = angle;
	}
}
