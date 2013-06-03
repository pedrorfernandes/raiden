package com.raiden.game;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Screen;

public class PauseScreen extends Screen {
	
	private boolean bgPainted = false;
	private Screen previousScreen;

	ScreenButton continueButton;
	ScreenButton quitButton;
	ScreenButton settingsButton;

	public PauseScreen(Game game) {
		super(game);
	}
	
	public PauseScreen(Game game, Screen previousScreen) {
		this(game);
		this.previousScreen = previousScreen;
	}

	@Override
	public void update(float deltaTime) {
		game.getInput().getTouchEvents();
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		if(!bgPainted) {
			g.drawImage(Assets.pauseMenu, 0, 0);
			//g.drawString(continueButton.label, continueButton.labelX, continueButton.labelY, continueButton.paint);
			//g.drawString(quitButton.label, quitButton.labelX, quitButton.labelY, quitButton.paint);
			//g.drawString(settingsButton.label, settingsButton.labelX, settingsButton.labelY, settingsButton.paint);
			bgPainted = true;
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
		bgPainted = false;
		game.getInput().getTouchEvents().clear();
		game.setScreen(previousScreen);
	}

}
