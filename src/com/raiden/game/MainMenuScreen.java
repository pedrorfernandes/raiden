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
	public final static int MENU_BUTTONS_FONT_SIZE = 90;
	public final static int SETTINGS_MENU_STR_YDIST = 96;
	public final static int SETTINGS_MENU_STR_XDIST = 380;
	public final static int MAIN_MENU_STR_YDIST = 117;
	public final static int MAIN_MENU_STR_XDIST = 450;
	public final static int BUTTON_WIDTH = 800;
	public final static int BUTTON_HEIGHT = 160;
	public final static int SETTINGS_MENU_DIST_BETWEEN_BUTTONS = BUTTON_HEIGHT + 195;
	public final static int MAIN_MENU_DIST_BETWEEN_BUTTONS = BUTTON_HEIGHT + 97;
	public final static int SETTINGS_MENU_FIRST_BUTTON_Y = 750;
	public final static int SETTINGS_MENU_FIRST_BUTTON_X = 0;
	public final static int MAIN_MENU_FIRST_BUTTON_Y = 580;
	public final static int MAIN_MENU_FIRST_BUTTON_X = 0;

	public MainMenuScreen(Game game) {
		super(game);
		
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(MainMenuScreen.MENU_BUTTONS_FONT_SIZE);
		p.setAntiAlias(true);
		Typeface face=Typeface.createFromAsset(game.getAssets(), ScreenButton.GAME_FONT);
		p.setTypeface(face);
		p.setColor(ScreenButton.GAME_FONT_COLOR);
		//p.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
		
		playButton = new ScreenButton(MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X, MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y,
				MainMenuScreen.BUTTON_WIDTH, MainMenuScreen.BUTTON_HEIGHT, "Play",
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X + MainMenuScreen.MAIN_MENU_STR_XDIST,
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + MainMenuScreen.MAIN_MENU_STR_YDIST,
				p, new GameScreen(game), false);
		
		settingsButton = new ScreenButton(MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X, MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS,
				MainMenuScreen.BUTTON_WIDTH, MainMenuScreen.BUTTON_HEIGHT, "Settings",
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X + MainMenuScreen.MAIN_MENU_STR_XDIST,
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS + MainMenuScreen.MAIN_MENU_STR_YDIST,
				p, new SettingsScreen(game, this), false);
		
		helpButton = new ScreenButton(MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X, MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + 2*MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS,
				MainMenuScreen.BUTTON_WIDTH, MainMenuScreen.BUTTON_HEIGHT, "Help",
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_X + MainMenuScreen.MAIN_MENU_STR_XDIST,
				MainMenuScreen.MAIN_MENU_FIRST_BUTTON_Y + 2*MainMenuScreen.MAIN_MENU_DIST_BETWEEN_BUTTONS + MainMenuScreen.MAIN_MENU_STR_YDIST,
				p, new HelpScreen(game, this), false);
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
					game.setScreen(playButton.nextScreen);               
				}
				
				if (settingsButton.hitbox.contains(event.x, event.y)) {
					//SETTINGS SCREEN
					bgPainted = false;
					game.setScreen(settingsButton.nextScreen);
				}
				
				if (helpButton.hitbox.contains(event.x, event.y)) {
					//HELP SCREEN
					bgPainted = false;
					game.setScreen(helpButton.nextScreen);               
				}


			}
		}
	}


	/*private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}*/


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