package game.ui;

import game.logic.Game;

/**
 * The GameInterface is the class responsible for handling the created game and start it.
 * The game can be played in a variety of different outputs through this.
 */
public abstract class GameInterface {
	
	protected Game game;
	
	public abstract void startGame();
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game){
		this.game = game;
	}

}
