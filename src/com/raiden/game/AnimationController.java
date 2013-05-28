package com.raiden.game;

import com.raiden.animation.Animation;

public class AnimationController implements Observer {
	GameScreen gameScreen;
	Animation currentAnimation;
	
	AnimationController(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		Event.initializeAnimations(gameScreen);
		currentAnimation = gameScreen.heroAnimation;
	}
	
	public void update(Collidable c, Event event){
		this.update(c.x, c.y, event);
	}
	
	public void update(int x, int y, Event event){
		Animation animation = event.getAnimation();
		if (animation != null)
			currentAnimation = animation;
	}

	public Animation getCurrenAnimation(){
		return currentAnimation;
	}
}