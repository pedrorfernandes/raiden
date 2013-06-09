package com.raiden.game;

import com.raiden.framework.Music;

/**
 * The music controller is responsible for managing the current song playing during the game.
 * It is also an observer, so when a certain event occurs, it can pause the current song and play a new one.
 */
public class MusicController implements Observer {
	Music currentMusic;
	
	public void update(Collidable c, Event event){
		Music music = event.getMusic();
		if ( music != null ){
			if (music.isPlaying()){
				music.seekBegin();
				music.stop();
			} else if (music == Assets.machinegun) {
				// machinegun is a special case, it is a side music
				music.play();
			} else {
				this.play(music);
			}
		}

	}
	
	public void update(int x, int y, Event event){
		this.update(null, event);
	}
	
	/**
	 * Plays a new song and stops the currently playing song (if there is one).
	 * @param music The new music to play.
	 */
	public void play(Music music){
		if (currentMusic != null && currentMusic.isPlaying()){
			currentMusic.seekBegin();
			currentMusic.stop();
		}
		currentMusic = music;
		music.play();
	}

	/**
	 * Stops and rewinds the currently playing song.
	 */
	public void stop(){
		if (currentMusic != null && currentMusic.isPlaying()){
			currentMusic.seekBegin();
			currentMusic.stop();
		}
	}
	
	/**
	 * Pauses the current song.
	 */
	public void pause(){
		currentMusic.pause();
	}
	
	/**
	 * Resumes the current song.
	 */
	public void resume(){
		if (currentMusic != null && !currentMusic.isPlaying()){
			currentMusic.play();
		}
	}
}
