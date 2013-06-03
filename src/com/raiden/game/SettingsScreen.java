package com.raiden.game;

import java.util.List;

import android.graphics.Paint;
import android.graphics.Typeface;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Screen;
import com.raiden.framework.Input.TouchEvent;

public class SettingsScreen extends Screen {

	private boolean bgPainted = false;
	private Screen previousScreen;

	private ScreenButton soundButton;
	private ScreenButton musicButton;

	public SettingsScreen(Game game) {
		super(game);

		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(ScreenButton.MENU_BUTTONS_FONT_SIZE);
		p.setAntiAlias(true);
		Typeface face=Typeface.createFromAsset(game.getAssets(), ScreenButton.GAME_FONT);
		p.setTypeface(face);
		p.setColor(ScreenButton.GAME_FONT_COLOR);
		//p.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);

		soundButton = new ScreenButton(ScreenButton.SETTINGS_MENU_FIRST_BUTTON_X, ScreenButton.SETTINGS_MENU_FIRST_BUTTON_Y,
				ScreenButton.BUTTON_WIDTH, ScreenButton.BUTTON_HEIGHT, "Sound: On",
				ScreenButton.SETTINGS_MENU_FIRST_BUTTON_X + ScreenButton.SETTINGS_MENU_STR_XDIST,
				ScreenButton.SETTINGS_MENU_FIRST_BUTTON_Y + ScreenButton.SETTINGS_MENU_STR_YDIST,
				p, new GameScreen(game), true);

		musicButton = new ScreenButton(ScreenButton.SETTINGS_MENU_FIRST_BUTTON_X, ScreenButton.SETTINGS_MENU_FIRST_BUTTON_Y + ScreenButton.SETTINGS_MENU_DIST_BETWEEN_BUTTONS,
				ScreenButton.BUTTON_WIDTH, ScreenButton.BUTTON_HEIGHT, "Music: On",
				ScreenButton.SETTINGS_MENU_FIRST_BUTTON_X + ScreenButton.SETTINGS_MENU_STR_XDIST,
				ScreenButton.SETTINGS_MENU_FIRST_BUTTON_Y + ScreenButton.SETTINGS_MENU_DIST_BETWEEN_BUTTONS + ScreenButton.SETTINGS_MENU_STR_YDIST,
				p, new GameScreen(game), true);
	}
	
	public SettingsScreen(Game game, Screen previousScreen) {
		this(game);
		this.previousScreen = previousScreen;
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				if (soundButton.hitbox.contains(event.x, event.y)) {
					// TOGGLE SOUND
					bgPainted = false;
					if(soundButton.toggled) {
						soundButton.toggled = false;
						soundButton.label = "Sound: Off";
						//TODO Toggle sound
					}
					else {
						soundButton.toggled = true;
						soundButton.label = "Sound: On";
						//TODO Toggle sound
					}
				}

				if (musicButton.hitbox.contains(event.x, event.y)) {
					//TOGGLE MUSIC
					bgPainted = false;
					if(musicButton.toggled) {
						musicButton.toggled = false;
						musicButton.label = "Music: Off";
						//TODO Toggle music
					}
					else {
						musicButton.toggled = true;
						musicButton.label = "Music: On";
						//TODO Toggle music
					}
				}


			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		if(!bgPainted) {
			g.drawImage(Assets.settingsMenu, 0, 0);
			g.drawString(soundButton.label, soundButton.labelX, soundButton.labelY, soundButton.paint);
			g.drawString(musicButton.label, musicButton.labelX, musicButton.labelY, musicButton.paint);
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
