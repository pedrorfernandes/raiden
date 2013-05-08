package com.raiden.game;

import android.graphics.Point;

public class Turret {
	Collidable ship;
	Collidable target;
	Point position;
	float angle;
	double radians;
	
	public Turret(Collidable ship, Point position, float angle) {
		this.ship = ship;
		this.position = position;
		this.angle = angle;
	}
	
	public Turret(Collidable ship, Point position, Collidable target) {
		this.ship = ship;
		this.position = position;
		this.target = target;
	}
	
	public Bullet fire(){
		if (target == null) {
			return new Bullet(ship.x + position.x, 
                              ship.y + position.y, angle);
		} else {
			// calculate the angle between two points
			int deltaX = target.x - ship.x;
			int deltaY = ship.y - target.y;
			radians = Math.atan2(deltaY, deltaX);
			
			return new Bullet(ship.x + position.x, 
                    ship.y + position.y, radians);
		}
	}
	
	
}
