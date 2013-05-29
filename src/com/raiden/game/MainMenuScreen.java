package com.raiden.game;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Input.TouchEvent;
import com.raiden.framework.Screen;

public class MainMenuScreen extends Screen {
	
	boolean bgPainted = false;
	ScreenButton playButton;
	ScreenButton settingsButton;
	ScreenButton helpButton;

	public MainMenuScreen(Game game) {
		super(game);
		
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(ScreenButton.MENU_BUTTONS_FONT_SIZE);
		p.setAntiAlias(true);
		Typeface face=Typeface.createFromAsset(game.getAssets(), ScreenButton.GAME_FONT);
		p.setTypeface(face);
		p.setColor(ScreenButton.GAME_FONT_COLOR);
		//p.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
		
		playButton = new ScreenButton(ScreenButton.MAIN_MENU_PLAY_X, ScreenButton.MAIN_MENU_PLAY_Y,
				ScreenButton.BUTTON_WIDTH, ScreenButton.BUTTON_HEIGHT, "Play",
				ScreenButton.MAIN_MENU_PLAY_X + ScreenButton.MAIN_MENU_STR_XDIST,
				ScreenButton.MAIN_MENU_PLAY_Y + ScreenButton.MAIN_MENU_STR_YDIST,
				p, new GameScreen(game));
		
		settingsButton = new ScreenButton(ScreenButton.MAIN_MENU_PLAY_X, ScreenButton.MAIN_MENU_PLAY_Y + ScreenButton.DIST_BETWEEN_BUTTONS,
				ScreenButton.BUTTON_WIDTH, ScreenButton.BUTTON_HEIGHT, "Settings",
				ScreenButton.MAIN_MENU_PLAY_X + ScreenButton.MAIN_MENU_STR_XDIST,
				ScreenButton.MAIN_MENU_PLAY_Y + ScreenButton.DIST_BETWEEN_BUTTONS + ScreenButton.MAIN_MENU_STR_YDIST,
				p, new GameScreen(game));
		
		helpButton = new ScreenButton(ScreenButton.MAIN_MENU_PLAY_X, ScreenButton.MAIN_MENU_PLAY_Y + 2*ScreenButton.DIST_BETWEEN_BUTTONS,
				ScreenButton.BUTTON_WIDTH, ScreenButton.BUTTON_HEIGHT, "Help",
				ScreenButton.MAIN_MENU_PLAY_X + ScreenButton.MAIN_MENU_STR_XDIST,
				ScreenButton.MAIN_MENU_PLAY_Y + 2*ScreenButton.DIST_BETWEEN_BUTTONS + ScreenButton.MAIN_MENU_STR_YDIST,
				p, new HelpScreen(game, this));
	}


	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if (playButton.hitbox.contains(event.x, event.y)) {
					//START GAME
					bgPainted = false;
					game.setScreen(new GameScreen(game));               
				}
				
				if (helpButton.hitbox.contains(event.x, event.y)) {
					//HELP SCREEN
					bgPainted = false;
					game.setScreen(helpButton.nextScreen);               
				}


			}
		}
	}


	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}


	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		if(!bgPainted) {
			g.drawImage(Assets.menu, 0, 0);
			g.drawString(playButton.label, playButton.labelX, playButton.labelY, playButton.paint);
			g.drawString(settingsButton.label, settingsButton.labelX, settingsButton.labelY, settingsButton.paint);
			g.drawString(helpButton.label, helpButton.labelX, helpButton.labelY, helpButton.paint);
			bgPainted = true;
		}
		
		//g.drawRect(playButton.hitbox.left, playButton.hitbox.top, playButton.hitbox.right - playButton.x, playButton.hitbox.bottom - playButton.y, Color.BLACK);
		//g.drawRect(helpButton.hitbox.left, helpButton.hitbox.top, helpButton.hitbox.right - helpButton.x, helpButton.hitbox.bottom - helpButton.y, Color.BLACK);
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
		game.moveTaskToBack(true);
	}
}