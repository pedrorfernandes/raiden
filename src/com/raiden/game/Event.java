package com.raiden.game;

import java.util.ArrayList;

import com.raiden.animation.Animation;
import com.raiden.animation.Collision;
import com.raiden.animation.Explosion;
import com.raiden.framework.Music;
import com.raiden.framework.Sound;

public enum Event {
	EnemyHit
	{
		@Override
		public Animation getSpecialEffect(int x, int y){
			return new Explosion(x, y, SMALL_EFFECT);
		}
		
		@Override
		public Sound getSound(){
			return getHitSound();
		}
	}, 
	
	HeroHit
	{
		@Override
		public Animation getSpecialEffect(int x, int y){
			return new Explosion(x, y, SMALL_EFFECT);
		}
		
		@Override
		public Sound getSound(){
			return Assets.heroHit;
		}
	},
	
	Explosion
	{
		@Override
		public Animation getSpecialEffect(int x, int y){
			return new Explosion(x, y, BIG_EFFECT);
		}
		
		@Override
		public Sound getSound(){
			return getExplosionSound();
		}
	},
	
	Collision
	{
		@Override
		public Animation getSpecialEffect(int x, int y){
			return new Collision(x, y, NORMAL_EFFECT, collisionSpeedX, collisionSpeedY);
		}
		
		@Override
		public Sound getSound(){
			return Assets.heroCollisionSound;
		}
	},
	
	TurnLeft
	{
		@Override
		public Animation getAnimation(){
			return heroTurningLeftAnimation;
		}
	},
	
	TurnRight
	{
		@Override
		public Animation getAnimation(){
			return heroTurningRightAnimation;
		}
	},
	
	StopTurning
	{
		@Override
		public Animation getAnimation(){
			return heroAnimation;
		}
	},

	Firing
	{
		@Override
		public Music getMusic(){
			return Assets.machinegun;
		}
	},
	
	PowerUp
	{
		@Override
		public Sound getSound(){
			return powerUpType.sound;
		}
	},
	
	GameOver
	{
		@Override
		public Sound getSound(){
			return Assets.heroDown;
		}
		
		@Override
		public Music getMusic(){
			return Assets.gameOverMusic;
		}
	},
	
	ScoreUp
	{
		
	};

	private static final float BIG_EFFECT = 1.5f;
	private static final float NORMAL_EFFECT = 1.0f;
	private static final float SMALL_EFFECT = 0.4f;
	
	private static final int collisionSpeedX = 0;
	private static final int collisionSpeedY = 5;
	
	private static Animation heroAnimation,  heroTurningLeftAnimation, heroTurningRightAnimation;
	
	private static ArrayList<Sound> hitSounds;
	private static int currentHitSound, currentExplosionSound;
	private static ArrayList<Sound> explosionSounds;
		
	public PowerUp.Type powerUpType;
	
	private Event(){
		powerUpType = null;
	}
	
	public void setPowerUpType(PowerUp.Type powerUpType){
		this.powerUpType = powerUpType;
	}
	
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
		return null;
	}
		
	public Animation getAnimation(){
		return null;
	}
	
	public Sound getSound(){
		return null;
	}
	
	public Music getMusic(){
		return null;
	}
		
	private static Sound getExplosionSound(){
		if (currentExplosionSound >= explosionSounds.size() )
			currentExplosionSound = 0;
		Sound explosionSound = explosionSounds.get(currentExplosionSound);
		currentExplosionSound++;
		return explosionSound;
	}
	
	private static Sound getHitSound(){
		if (currentHitSound >= hitSounds.size() )
			currentHitSound = 0;
		Sound explosionSound = hitSounds.get(currentHitSound);
		currentHitSound++;
		return explosionSound;
	}
}
