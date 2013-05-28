package com.raiden.game;

import com.raiden.animation.Animation;
import com.raiden.animation.Collision;
import com.raiden.animation.Explosion;

public enum Event {
	EnemyHit, HeroHit, Explosion, Collision, 
	TurnLeft, TurnRight, StartFiring, StopFiring;
	
	private static final float BIG_EFFECT = 1.5f;
	private static final float NORMAL_EFFECT = 1.0f;
	private static final float SMALL_EFFECT = 0.4f;
	
	private static final int collisionSpeedX = 0;
	private static final int collisionSpeedY = 5;
	
	public Animation getAnimation(int x, int y){
		switch (this) {
		case EnemyHit: case HeroHit:
			return new Explosion(x, y, SMALL_EFFECT);
		case Explosion:
			return new Explosion(x, y, BIG_EFFECT);
		case Collision:
			return new Collision(x, y, NORMAL_EFFECT, collisionSpeedX, collisionSpeedY);

		default:
			return null;
		}
	}
}
