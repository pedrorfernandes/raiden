package com.raiden.game;

import java.util.ArrayList;

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

	public void fire(ArrayList<Bullet> bullets){
		if (target == null) {
			int length = bullets.size();
			for (int i = 0; i < length; i++) {
				if ( !bullets.get(i).visible ){
					bullets.get(i).fire(ship.x + position.x, 
                                        ship.y + position.y, angle);
					return;
				}
			}
		} else {
			int length = bullets.size();
			for (int i = 0; i < length; i++) {
				if ( !bullets.get(i).visible ){
					// calculate the angle between two points
					int deltaX = target.x - ship.x;
					int deltaY = ship.y - target.y;
					radians = FastMath.atan2(deltaY, deltaX);
					bullets.get(i).fire(ship.x + position.x, 
                                        ship.y + position.y, radians);
					return;
				}
			}
		}
	}

}
