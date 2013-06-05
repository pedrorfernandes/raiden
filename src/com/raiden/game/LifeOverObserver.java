package com.raiden.game;

import com.raiden.game.GameScreen.GameState;

public class LifeOverObserver implements Observer {
	
	private GameScreen gameScreen;

	public LifeOverObserver(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}

	@Override
	public void update(int x, int y, Event event) {
		return;
	}

	@Override
	public void update(Collidable c, Event event) {
		if (event == Event.GameOver){
			gameScreen.livesLeft--;
		}	
	}

}
