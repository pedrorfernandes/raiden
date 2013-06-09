package com.raiden.game;

import com.raiden.animation.Animation;

/**
 * The effects controller is an observer that listens to events that return animations.
 * This controller will automatically update the game screen of new special effects.
 */
public class EffectsController implements Observer {
	GameScreen gameScreen;
	
	/**
	 * Creates a new effects controller.
	 * @param gameScreen The current game screen.
	 */
	EffectsController(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}
	
	public void update(Collidable c, Event event){
		this.update(c.x, c.y, event);
	}
	
	public void update(int x, int y, Event event){
		Animation specialEffect = event.getSpecialEffect(x, y);
		if (specialEffect != null)
			gameScreen.addSpecialEffect(specialEffect);
	}

}
