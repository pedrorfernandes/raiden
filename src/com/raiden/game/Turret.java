package com.raiden.game;

import android.graphics.Point;

public class Turret {
	Collidable ship;
	Collidable target;
	Point position;
	float angle;
	
	public Turret(Collidable ship, Point position, float angle) {
		this.ship = ship;
		this.position = position;
		this.angle = angle;
	}
	
	public Turret(Collidable ship, Point position, Collidable target) {
		this.ship = ship;
		this.position = position;
		this.target = target;
		this.angle = 270.0f;
	}
	
	public Bullet fire(){
		if (target == null) {
			return new Bullet(ship.x + position.x, 
                              ship.y + position.y, angle);
		} else {
			return new Bullet(ship.x + position.x, 
                    ship.y + position.y, angle);
		}
	}
	
	
}
