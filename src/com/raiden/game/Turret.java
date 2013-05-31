package com.raiden.game;

import android.graphics.Point;

public class Turret {
	private Ship ship;
	private Collidable target;
	private Point position;
	private float angle;
	private double radians;
	public Bullet.Type bulletType;

	public Turret(Ship ship, Point position, float angle, Bullet.Type bulletType) {
		this.ship = ship;
		this.position = position;
		this.angle = angle;
		this.bulletType = bulletType;
	}

	public Turret(Ship ship, Point position, Collidable target, Bullet.Type bulletType) {
		this.ship = ship;
		this.position = position;
		this.target = target;
		this.bulletType = bulletType;
	}

	public void fire(Bullet[] bullets){
		if (target == null) {
			int length = bullets.length;
			for (int i = 0; i < length; i++) {
				if ( !bullets[i].visible ){
					bullets[i].fire(ship.x + position.x, 
                                    ship.y + position.y,
                                    angle, bulletType);
					return;
				}
			}
		} else {
			int length = bullets.length;
			for (int i = 0; i < length; i++) {
				if ( !bullets[i].visible ){
					// calculate the angle between two points
					int deltaX = target.x - ship.x;
					int deltaY = ship.y - target.y;
					radians = FastMath.atan2(deltaY, deltaX);
					angle = (float) Math.toDegrees(radians);
					bullets[i].fire(ship.x + position.x, 
                                    ship.y + position.y, 
                                    angle, bulletType);
					return;
				}
			}
		}
	}

	public void setTarget(Collidable target){
		this.target = target;
	}
	
	public void setBulletType(Bullet.Type bulletType){
		this.bulletType = bulletType;
	}
}
