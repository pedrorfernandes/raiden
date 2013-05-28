package com.raiden.game;

import com.raiden.animation.Animation;

public class EffectsController implements Observer {
	GameScreen gameScreen;
	
	EffectsController(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}
	
	public void update(Collidable c, Event event){
		this.update(c.x, c.y, event);
	}
	
	public void update(int x, int y, Event event){
		Animation animation = event.getAnimation(x, y);
		if (animation != null)
			gameScreen.addSpecialEffect(animation);
	}

}
