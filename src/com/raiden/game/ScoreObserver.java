package com.raiden.game;

/**
 * The score observer updates the game's current score
 * when a scoring event occurs.
 */
public class ScoreObserver implements Observer {

	private GameScreen gameScreen;

	public ScoreObserver(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}

	@Override
	public void update(int x, int y, Event event) {
		return;
	}

	@Override
	public void update(Collidable c, Event event) {
		if (event == Event.ScoreUp){
			gameScreen.setScore(gameScreen.getScore() + c.score);
		}	
	}

}
