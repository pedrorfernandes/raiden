package com.raiden.game;

import com.raiden.framework.Music;

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
	
	public void play(Music music){
		if (currentMusic != null && currentMusic.isPlaying()){
			currentMusic.seekBegin();
			currentMusic.stop();
		}
		currentMusic = music;
		music.play();
	}

	public void stop(){
		if (currentMusic != null && currentMusic.isPlaying()){
			currentMusic.seekBegin();
			currentMusic.stop();
		}
	}
	
	public void pause(){
		currentMusic.pause();
	}
	
	public void resume(){
		if (currentMusic != null && !currentMusic.isPlaying()){
			currentMusic.play();
		}
	}
}
