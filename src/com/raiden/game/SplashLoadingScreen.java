package com.raiden.game;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Graphics.ImageFormat;
import com.raiden.framework.Screen;

public class SplashLoadingScreen extends Screen {
	
	public SplashLoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.splash = g.newImage("splash.png", ImageFormat.RGB565);

        game.setScreen(new LoadingScreen(game));
    }

	@Override
	public void paint(float deltaTime) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}

	@Override
	public void pauseMusic() {
		
	}

	@Override
	public void resumeMusic() {
		
	}

}
