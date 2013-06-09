package com.raiden.game;

import com.raiden.animation.Animation;

/**
 * The animation controller is an observer that decides which animation a certain element has.
 * For example, if the hero is observed and signals a certain event, the associated animation with that event 
 * will be used by this observer to determine the current hero animation.
 */
public class AnimationController implements Observer {
	GameScreen gameScreen;
	Animation currentAnimation;
	
	AnimationController(GameScreen gameScreen, Animation startingAnimation){
		this.gameScreen = gameScreen;
		Event.initializeAnimations(gameScreen);
		currentAnimation = startingAnimation;
	}
	
	public void update(Collidable c, Event event){
		this.update(c.x, c.y, event);
	}
	
	public void update(int x, int y, Event event){
		Animation animation = event.getAnimation();
		if (animation != null)
			currentAnimation = animation;
	}

	/**
	 * @return The current animation for the observed object.
	 */
	public Animation getCurrenAnimation(){
		return currentAnimation;
	}
}