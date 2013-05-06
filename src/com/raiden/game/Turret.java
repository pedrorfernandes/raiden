package com.raiden.game;

import android.graphics.Point;

public class Turret {
	Ship ship;
	Point position;
	double angle;
	
	public Turret(Ship ship, Point position, double angle) {
		this.ship = ship;
		this.position = position;
		this.angle = angle;
	}
	
	public Bullet fire(){
		return new Bullet(ship.centerX + position.x, 
                          ship.centerY + position.y, angle);
	}
}
