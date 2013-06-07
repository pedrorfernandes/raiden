package com.raiden.game;

import java.util.List;

import android.graphics.Paint;
import android.graphics.Typeface;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Screen;
import com.raiden.framework.Input.TouchEvent;

public class SettingsScreen extends Screen {

	private static final String MUSIC_ON = "Music: On";
	private static final String MUSIC_OFF = "Music: Off";
	private static final String SOUND_ON = "Sound: On";
	private static final String SOUND_OFF = "Sound: Off";
	//Button related constants
	private final static int BUTTON_WIDTH = 800;
	private final static int BUTTON_HEIGHT = 160;
	
	private final static int SETTINGS_BUTTONS_FONT_SIZE = 90;

	private final static int SETTINGS_MENU_STR_XDIST = 380;
	private final static int SETTINGS_MENU_STR_YDIST = 96;

	private final static int SETTINGS_MENU_FIRST_BUTTON_X = 0;
	private final static int SETTINGS_MENU_FIRST_BUTTON_Y = 750;

	private final static int SETTINGS_MENU_DIST_BETWEEN_BUTTONS = BUTTON_HEIGHT + 195;

	private boolean bgPainted = false;
	private Screen previousScreen;

	private ScreenButton soundButton;
	private ScreenButton musicButton;

	private SettingsScreen(Game game) {
		super(game);

		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(SETTINGS_BUTTONS_FONT_SIZE);
		p.setAntiAlias(true);
		Typeface face=Typeface.createFromAsset(game.getAssets(), ScreenButton.GAME_FONT);
		p.setTypeface(face);
		p.setColor(ScreenButton.GAME_FONT_COLOR);

		soundButton = new ScreenButton(SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_X, SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_Y,
				BUTTON_WIDTH, BUTTON_HEIGHT, SOUND_ON,
				SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_X + SettingsScreen.SETTINGS_MENU_STR_XDIST,
				SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_Y + SettingsScreen.SETTINGS_MENU_STR_YDIST,
				p);

		musicButton = new ScreenButton(SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_X, SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_Y + SettingsScreen.SETTINGS_MENU_DIST_BETWEEN_BUTTONS,
				BUTTON_WIDTH, BUTTON_HEIGHT, MUSIC_ON,
				SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_X + SettingsScreen.SETTINGS_MENU_STR_XDIST,
				SettingsScreen.SETTINGS_MENU_FIRST_BUTTON_Y + SettingsScreen.SETTINGS_MENU_DIST_BETWEEN_BUTTONS + SettingsScreen.SETTINGS_MENU_STR_YDIST,
				p);
	}

	public SettingsScreen(Game game, Screen previousScreen) {
		this(game);
		this.previousScreen = previousScreen;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		
		if(Assets.soundMuted) {
			soundButton.label = SOUND_OFF;
		}
		else {
			soundButton.label = SOUND_ON;
		}
		
		if(Assets.musicMuted) {
			musicButton.label = MUSIC_OFF;
		}
		else {
			musicButton.label = MUSIC_ON;
		}

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				//TODO Fix dis (button toggling not the very best thing)
				if (soundButton.hitbox.contains(event.x, event.y)) {
					// TOGGLE SOUND
					bgPainted = false;
					if(!Assets.soundMuted) {
						soundButton.label = SOUND_OFF;
						Assets.setSoundVolume(0.0f);
					}
					else {
						soundButton.label = SOUND_ON;
						Assets.setSoundVolume(1.0f);
					}
				}

				if (musicButton.hitbox.contains(event.x, event.y)) {
					//TOGGLE MUSIC
					bgPainted = false;
					if(!Assets.musicMuted) {
						musicButton.label = MUSIC_OFF;
						Assets.setMusicVolume(0.0f);
					}
					else {
						musicButton.label = MUSIC_ON;
						Assets.setMusicVolume(1.0f);
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
