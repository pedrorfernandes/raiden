package com.raiden.game;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Screen;

public class HelpScreen extends Screen {
	
	private Screen previousScreen;
	private boolean bgPainted = false;

	public HelpScreen(Game game) {
		super(game);
	}
	
	public HelpScreen(Game game, Screen previousScreen) {
		this(game);
		this.previousScreen = previousScreen;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		if(!bgPainted) {
			g.drawImage(Assets.helpMenu, 0, 0);
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
		game.setScreen(previousScreen);
	}

}
