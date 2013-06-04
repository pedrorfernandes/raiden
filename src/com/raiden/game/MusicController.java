package com.raiden.game;

import com.raiden.framework.Music;

public class MusicController implements Observer {
	GameScreen gameScreen;
	
	MusicController(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}
	
	public void update(Collidable c, Event event){

		Music music = event.getMusic();
		if ( music != null ){
			if (music.isPlaying()){
				music.seekBegin();
				music.stop();
			} else {
				music.play();
			}
		}

	}
	
	public void update(int x, int y, Event event){
		this.update(null, event);
	}
}
