package com.raiden.game;

import com.raiden.framework.Sound;

public class SoundController implements Observer {
	GameScreen gameScreen;
	
	// sound variables
	private float volume;
	
	SoundController(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		Event.initializeSounds();
		volume = Assets.volume;
	}
	
	public void update(Collidable c, Event event){
		volume = Assets.volume;
		Sound sound = event.getSound();
		if ( sound != null )
			sound.play(volume);
	}
	
	public void update(int x, int y, Event event){
		this.update(null, event);
	}
}
