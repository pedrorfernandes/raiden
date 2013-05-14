package com.raiden.game;

import java.util.Random;

import android.graphics.Point;

public class Turret {
	private Collidable ship;
	private Collidable target;
	private Point position;
	private float angle;
	private double radians;
	public static Random random = new Random();

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

	public void fire(Bullet[] bullets){
		if (target == null) {
			int length = bullets.length;
			for (int i = 0; i < length; i++) {
				if ( !bullets[i].visible ){
					bullets[i].fire(ship.x + position.x, 
                                        ship.y + position.y, angle);
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
					bullets[i].fire(ship.x + position.x, 
                                        ship.y + position.y, radians);
					return;
				}
			}
		}
	}

}
