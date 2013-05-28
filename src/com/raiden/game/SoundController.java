package com.raiden.game;

import java.util.ArrayList;

import com.raiden.framework.Sound;

public class SoundController implements Observer {
	GameScreen gameScreen;
	
	// sound variables
	private int volume = 100;
	private static final int FIRST_SHOT_FIRED = 8;
	private static ArrayList<Sound> hitSounds;
	private static int currentHitSound = 0;
	private static ArrayList<Sound> explosionSounds;
	private static int currentExplosionSound = 0;
	
	SoundController(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		
		Assets.machinegun.setLooping(true);
		hitSounds = new ArrayList<Sound>();
		hitSounds.add(Assets.hit1); hitSounds.add(Assets.hit2); 
		hitSounds.add(Assets.hit3); hitSounds.add(Assets.hit4); 
		hitSounds.add(Assets.hit5); 

		explosionSounds = new ArrayList<Sound>();
		explosionSounds.add(Assets.explosionSound1); explosionSounds.add(Assets.explosionSound2);
		explosionSounds.add(Assets.explosionSound3); explosionSounds.add(Assets.explosionSound4);
		explosionSounds.add(Assets.explosionSound5); explosionSounds.add(Assets.explosionSound6);
		explosionSounds.add(Assets.explosionSound7); explosionSounds.add(Assets.explosionSound8);
		explosionSounds.add(Assets.explosionSound9); explosionSounds.add(Assets.explosionSound10);
		explosionSounds.add(Assets.explosionSound11);
		
		Assets.machinegun.setVolume(1.0f);
	}
	
	public void update(Collidable c, Event event){
		if (currentHitSound >= hitSounds.size() )
			currentHitSound = 0;
		
		if (currentExplosionSound >= explosionSounds.size() )
			currentExplosionSound = 0;
		
		switch (event) {
		case EnemyHit:
			hitSounds.get(currentHitSound).play(volume);
			currentHitSound++;
			break;
		case HeroHit:
			Assets.heroHit.play(volume);
			break;
		case Explosion:
			explosionSounds.get(currentExplosionSound).play(volume);
			currentExplosionSound++;
			break;
		case Collision:
			Assets.heroCollisionSound.play(volume);
			break;
		case StartFiring:
			Assets.machinegun.play();
			break;
		case StopFiring:
			Assets.machinegun.seekBegin();
			Assets.machinegun.stop();
			break;

		default:
			break;
		}
	}
	
	public void update(int x, int y, Event event){
		this.update(null, event);
	}
}
