package com.raiden.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;

import com.raiden.animation.Animation;
import com.raiden.framework.Image;
import com.raiden.framework.Music;
import com.raiden.framework.Sound;

/**
 * The assets hold every important resource needed for the game, such as images, sounds and musics.
 */
public class Assets {

	private static final int HERO_ANIMATION_DURATION = 10;

	public static Image splash, menu, refreshButtonImg, popupImg, helpMenu,
	settingsMenu, levelSelectionMenu, pauseButtonImg, pauseMenu, levelOverMenu;

	public static float volume = 1.0f;

	public static Sound explosionSound1, explosionSound2, explosionSound3,
	explosionSound4, explosionSound5, explosionSound6,
	explosionSound7, explosionSound8, explosionSound9,
	explosionSound10, explosionSound11,
	hit1, hit2, hit3, hit4, hit5,
	heroHit, heroCollisionSound,
	powerUpSound1, powerUpSound2, powerUpSound3, powerUpSound4,
	heroDown,
	missionStartSound, missionVictorySound;

	public static Image hero1, hero2, 
	heroLeft1, heroLeft2, 
	heroRight1, heroRight2,
	heroBullet1, heroBullet2,
	enemyBullet1, enemyBullet2,
	enemy1, enemy2, tankBoss, techBoss,
	explosion1, explosion2, explosion3,
	explosion4, explosion5, explosion6,
	heroCollision1, heroCollision2, heroCollision3,
	powerUp1, powerUp2, powerUp3, powerUp4,
	screenCrack,
	background, cloud;

	public static boolean musicMuted;
	public static boolean soundMuted;

	public static Music machinegun, gameOverMusic,
	menuMusic, missionMusic, missionVictory;
	
	public static List<Music> musics;
	
	public static Resources resources;

	/**
	 * Sets the volume for all the sounds.
	 * @param newVolume The volume to set.
	 */
	public static void setSoundVolume(float newVolume){

		if(newVolume == 0) {
			soundMuted = true;
		}
		else {
			soundMuted = false;
		}

		Assets.volume = newVolume;

		machinegun.setVolume(newVolume);
	}

	/**
	 * Sets the volume for all the musics.
	 * @param newVolume The volume to set.
	 */
	public static void setMusicVolume(float newVolume) {

		if(newVolume == 0) {
			musicMuted = true;
		}
		else {
			musicMuted = false;
		}

		for (Music music : musics) {
			if(music != machinegun) {
				music.setVolume(newVolume);
			}
		}
	}

	/**
	 * @return All the game musics.
	 */
	public static List<Music> getMusics(){
		return musics;
	}

	/**
	 * Stops all the game's musics.
	 */
	public static void stopAllMusic() {
		for (Music music : musics) {
			music.stop();
		}
	}
	
	/**
	 * Initializes the music list containing all the game's musics.
	 */
	public static void initializeMusicList(){
		musics = Arrays.asList(machinegun, gameOverMusic, menuMusic, missionMusic, missionVictory);
	}

	/**
	 * @return The animation for the hero turning left.
	 */
	public static Animation getHeroTurningLeftAnimation(){
		Animation heroTurningLeftAnimation = new Animation();
		heroTurningLeftAnimation.addFrame(heroLeft1, HERO_ANIMATION_DURATION);
		heroTurningLeftAnimation.addFrame(heroLeft2, HERO_ANIMATION_DURATION);
		return heroTurningLeftAnimation;
	}

	/**
	 * @return The animation for the hero turning right.
	 */
	public static Animation getHeroTurningRightAnimation(){
		Animation heroTurningRightAnimation = new Animation();
		heroTurningRightAnimation.addFrame(heroRight1, HERO_ANIMATION_DURATION);
		heroTurningRightAnimation.addFrame(heroRight2, HERO_ANIMATION_DURATION);
		return heroTurningRightAnimation;
	}

	/**
	 * @return The animation for the hero (not turning).
	 */
	public static Animation getHeroAnimation(){
		Animation heroAnimation = new Animation();
		heroAnimation.addFrame(hero1, HERO_ANIMATION_DURATION);
		heroAnimation.addFrame(hero2, HERO_ANIMATION_DURATION);
		return heroAnimation;
	}

	/**
	 * @return All the explosion sounds.
	 */
	public static ArrayList<Sound> getExplosionSounds(){
		ArrayList<Sound> explosionSounds = new ArrayList<Sound>();
		explosionSounds.add(explosionSound1); explosionSounds.add(explosionSound2);
		explosionSounds.add(explosionSound3); explosionSounds.add(explosionSound4);
		explosionSounds.add(explosionSound5); explosionSounds.add(explosionSound6);
		explosionSounds.add(explosionSound7); explosionSounds.add(explosionSound8);
		explosionSounds.add(explosionSound9); explosionSounds.add(explosionSound10);
		explosionSounds.add(explosionSound11);
		return explosionSounds;
	}
	
	/**
	 * @return All the hit sounds.
	 */
	public static ArrayList<Sound> getHitSounds(){
		ArrayList<Sound> hitSounds = new ArrayList<Sound>();
		hitSounds.add(hit1); hitSounds.add(hit2); 
		hitSounds.add(hit3); hitSounds.add(hit4); 
		hitSounds.add(hit5); 
		return hitSounds;
	}

	/**
	 * @return All the explosion images.
	 */
	public static ArrayList<Image> getExplosionImages(){
		ArrayList<Image> explosionImages = new ArrayList<Image>();
		explosionImages.add(explosion1); explosionImages.add(explosion2); 
		explosionImages.add(explosion3); explosionImages.add(explosion4); 
		explosionImages.add(explosion5); explosionImages.add(explosion6);
		return explosionImages;
	}

	/**
	 * @return All the collision images.
	 */
	public static ArrayList<Image> getCollisionImages(){
		ArrayList<Image> collisionImages = new ArrayList<Image>();
		collisionImages.add(heroCollision1); collisionImages.add(heroCollision2);
		collisionImages.add(heroCollision3);
		return collisionImages;
	}
}