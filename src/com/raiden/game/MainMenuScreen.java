package com.raiden.game;

import java.util.List;

import android.graphics.Paint;
import android.graphics.Typeface;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Input.TouchEvent;
import com.raiden.framework.Screen;

public class MainMenuScreen extends Screen {
	
	boolean bgPainted = false;
	ScreenButton playButton;
	ScreenButton settingsButton;
	ScreenButton helpButton;
	
	//Button related constants
	private final static int BUTTON_WIDTH = 800;
	private final static int BUTTON_HEIGHT = 160;
	
	private final static int MENU_BUTTONS_FONT_SIZE = 90;
	
	private final static int MAIN_MENU_STR_YDIST = 117;
	private final static int MAIN_MENU_STR_XDIST = 450;
	
	private final static int MAIN_MENU_DIST_BETWEEN_BUTTONS = BUTTON_HEIGHT + 97;
	
	private final static int MAIN_MENU_FIRST_BUTTON_X = 0;
	private final static int MAIN_MENU_FIRST_BUTTON_Y = 580;

	public MainMenuScreen(Game game) {
		super(game);
		
		game.getMusicController().play(Assets.menuMusic);
		
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(MainMenuScreen.MENU_BUTTONS_FONT_SIZE);
		p.setAntiAlias(true);
		Typeface face=Typeface.createFromAsset(game.getAssets(), ScreenButton.GAME_FONT);
		p.setTypeface(face);
		p.setColor(ScreenButton.GAME_FONT_COLOR);
		
		playButton = new ScreenButton(MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X, MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y,
				MainMenuScreen.BUTTON_WIDTH, MainMenuScreen.BUTTON_HEIGHT, "Play",
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X + MainMenuScreen.MAIN_MENU_STR_XDIST,
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + MainMenuScreen.MAIN_MENU_STR_YDIST,
				p);
		
		settingsButton = new ScreenButton(MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X, MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS,
				MainMenuScreen.BUTTON_WIDTH, MainMenuScreen.BUTTON_HEIGHT, "Settings",
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X + MainMenuScreen.MAIN_MENU_STR_XDIST,
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS + MainMenuScreen.MAIN_MENU_STR_YDIST,
				p);
		
		helpButton = new ScreenButton(MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X, MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + 2*MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS,
				MainMenuScreen.BUTTON_WIDTH, MainMenuScreen.BUTTON_HEIGHT, "Help",
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X + MainMenuScreen.MAIN_MENU_STR_XDIST,
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + 2*MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS + MainMenuScreen.MAIN_MENU_STR_YDIST,
				p);
	}


	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if (playButton.hitbox.contains(event.x, event.y)) {
					//START GAME
					bgPainted = false;
					playButton.setNextScreen(new LevelSelectionScreen(game, this));
					game.setScreen(playButton.nextScreen);               
				}
				
				if (settingsButton.hitbox.contains(event.x, event.y)) {
					//SETTINGS SCREEN
					bgPainted = false;
					settingsButton.setNextScreen(new SettingsScreen(game, this));
					game.setScreen(settingsButton.nextScreen);
				}
				
				if (helpButton.hitbox.contains(event.x, event.y)) {
					//HELP SCREEN
					bgPainted = false;
					helpButton.setNextScreen(new HelpScreen(game, this));
					game.setScreen(helpButton.nextScreen);               
				}


			}
		}
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
		game.getMusicController().resume();

	}


	@Override
	public void dispose() {


	}


	@Override
	public void backButton() {
		game.getMusicController().pause();
		game.moveTaskToBack(true);
	}
	
	@Override
	public void pauseMusic() {
		Assets.menuMusic.pause();
	}

	@Override
	public void resumeMusic() {
		Assets.menuMusic.play();
	}
}