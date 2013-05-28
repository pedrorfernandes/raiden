package com.raiden.game;

import java.util.ArrayList;

import com.raiden.animation.Animation;
import com.raiden.animation.Collision;
import com.raiden.animation.Explosion;
import com.raiden.framework.Sound;

public enum Event {
	EnemyHit, HeroHit, Explosion, Collision, 
	TurnLeft, TurnRight, StopTurning, 
	StartFiring, StopFiring;
	
	private static final float BIG_EFFECT = 1.5f;
	private static final float NORMAL_EFFECT = 1.0f;
	private static final float SMALL_EFFECT = 0.4f;
	
	private static final int collisionSpeedX = 0;
	private static final int collisionSpeedY = 5;
	
	private static Animation heroAnimation,  heroTurningLeftAnimation, heroTurningRightAnimation;
	
	private static ArrayList<Sound> hitSounds;
	private static int currentHitSound, currentExplosionSound;
	private static ArrayList<Sound> explosionSounds;
	
	public static void initializeSounds(){
		hitSounds = Assets.getHitSounds();
		explosionSounds = Assets.getExplosionSounds();
		currentHitSound = 0;
		currentExplosionSound = 0;
	}
	
	public static void initializeAnimations(GameScreen gameScreen){
		heroAnimation = gameScreen.heroAnimation;
		heroTurningLeftAnimation = gameScreen.heroTurningLeftAnimation;
		heroTurningRightAnimation = gameScreen.heroTurningRightAnimation;
	}

	public Animation getSpecialEffect(int x, int y){
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
		
	public Animation getAnimation(){
		switch (this) {
		case TurnLeft:
			return heroTurningLeftAnimation;
		case TurnRight:
			return heroTurningRightAnimation;
		case StopTurning:
			return heroAnimation;
		default:
			return null;
		}
	}
	
	public Sound getSound(){
		switch (this) {
		case EnemyHit:
			return getHitSound();
		case HeroHit:
			return Assets.heroHit;
		case Explosion:
			return getExplosionSound();
		case Collision:
			return Assets.heroCollisionSound;
		default:
			return null;
		}
	}
	
	private Sound getExplosionSound(){
		if (currentExplosionSound >= explosionSounds.size() )
			currentExplosionSound = 0;
		Sound explosionSound = explosionSounds.get(currentExplosionSound);
		currentExplosionSound++;
		return explosionSound;
	}
	
	private Sound getHitSound(){
		if (currentHitSound >= hitSounds.size() )
			currentHitSound = 0;
		Sound explosionSound = hitSounds.get(currentHitSound);
		currentHitSound++;
		return explosionSound;
	}
}
