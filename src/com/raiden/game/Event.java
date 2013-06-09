package com.raiden.game;

import java.util.ArrayList;

import com.raiden.animation.Animation;
import com.raiden.animation.Collision;
import com.raiden.animation.Explosion;
import com.raiden.framework.Music;
import com.raiden.framework.Sound;

/**
 * An event can represent various occurrences in game.
 * It is a means of communication between observable objects and observers.
 * Each event may be associated with a sound, music, animation, special effect, etc.
 */
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
	
	GameStart
	{
		@Override
		public Sound getSound(){
			return Assets.missionStartSound;
		}
	},
	
	Victory
	{
		@Override
		public Sound getSound(){
			return Assets.missionVictorySound;
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
	
	/**
	 * Creates a new event.
	 */
	private Event(){
		powerUpType = null;
	}
	
	/**
	 * Sets the current power up type used when an event occurred.
	 * @param powerUpType The new power up type used.
	 */
	public void setPowerUpType(PowerUp.Type powerUpType){
		this.powerUpType = powerUpType;
	}
	
	/**
	 * Initializes the needed sounds for events.
	 */
	public static void initializeSounds(){
		hitSounds = Assets.getHitSounds();
		explosionSounds = Assets.getExplosionSounds();
		currentHitSound = 0;
		currentExplosionSound = 0;
	}
	
	/**
	 * Initializes the needed animations for events.
	 * @param gameScreen The current game screen.
	 */
	public static void initializeAnimations(GameScreen gameScreen){
		heroAnimation = gameScreen.heroAnimation;
		heroTurningLeftAnimation = gameScreen.heroTurningLeftAnimation;
		heroTurningRightAnimation = gameScreen.heroTurningRightAnimation;
	}

	/**
	 * @param x The X position of the special effect.
	 * @param y The Y position of the special effect.
	 * @return A special effect. If none is associated with the event, null.
	 */
	public Animation getSpecialEffect(int x, int y){
		return null;
	}
	
	/**
	 * @return An animation. If none is associated with the event, null.
	 */
	public Animation getAnimation(){
		return null;
	}
	
	/**
	 * @return A sound. If none is associated with the event, null.
	 */
	public Sound getSound(){
		return null;
	}
	
	/**
	 * @return A music. If none is associated with the event, null.
	 */
	public Music getMusic(){
		return null;
	}
		
	/**
	 * After returning an explosion sound, the next one 
	 * in the explosion sounds array will be returned.
	 * @return The current explosion sound.
	 */
	private static Sound getExplosionSound(){
		if (currentExplosionSound >= explosionSounds.size() )
			currentExplosionSound = 0;
		Sound explosionSound = explosionSounds.get(currentExplosionSound);
		currentExplosionSound++;
		return explosionSound;
	}
	
	/**
	 * After returning a hit sound, the next one in the
	 * hit sounds array will be returned.
	 * @return The current hit sound.
	 */
	private static Sound getHitSound(){
		if (currentHitSound >= hitSounds.size() )
			currentHitSound = 0;
		Sound explosionSound = hitSounds.get(currentHitSound);
		currentHitSound++;
		return explosionSound;
	}
}
