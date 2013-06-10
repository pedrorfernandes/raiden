package com.raiden.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Music;
import com.raiden.framework.Screen;
import com.raiden.framework.Input.TouchEvent;

/**
 * The pause screen is used when the game is paused during a mission.
 */
public class PauseScreen extends Screen {
	
	private ArrayList<Music> pausedMusics = new ArrayList<Music>();

	private final static int TITLE_X = 80;
	private final static int TITLE_Y = 120;
	private final static int TITLE_ANGLE = -25;
	
	//Button related constants
	private final static int BUTTON_WIDTH = 800;
	private final static int BUTTON_HEIGHT = 115;

	private final static int PAUSE_MENU_FONT_SIZE = 75;

	private final static int PAUSE_MENU_STR_YDIST = 79;
	private final static int PAUSE_MENU_LEFT_STR_XDIST = 25;
	private final static int PAUSE_MENU_RIGHT_STR_XDIST = 632;

	private final static int PAUSE_MENU_DIST_BETWEEN_BUTTONS = BUTTON_HEIGHT + 120;

	private final static int PAUSE_MENU_FIRST_BUTTON_Y = 495;
	private final static int PAUSE_MENU_FIRST_BUTTON_X = 0;
	
	private final static int SCORE_LABEL_FONT_SIZE = 50;
	private final static int SCORE_FONT_SIZE = 45;
	
	private final static int SCORE_LABEL_X = 200;
	private final static int SCORE_LABEL_Y = 1155;
	
	private final static int SCORE_X = 300;
	private final static int SCORE_Y = 1205;

	private boolean bgPainted = false;
	private GameScreen gameScreen;
	
	private String pauseLabel = Assets.resources.getString(R.string.pause_title);
	private String scoreLabel = Assets.resources.getString(R.string.score_label);

	private ScreenButton continueButton;
	private ScreenButton quitButton;
	
	private String continueButtonLabel = Assets.resources.getString(R.string.continue_button);
	private String quitButtonLabel = Assets.resources.getString(R.string.quit_button);
	
	private Paint scoreLabelPaint;
	private Paint scorePaint;

	public PauseScreen(Game game) {
		super(game);

		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(PauseScreen.PAUSE_MENU_FONT_SIZE);
		p.setAntiAlias(true);
		Typeface face=Typeface.createFromAsset(game.getAssets(), ScreenButton.GAME_FONT);
		p.setTypeface(face);
		p.setColor(ScreenButton.GAME_FONT_COLOR);
		p.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
		
		scoreLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		scoreLabelPaint.setTextSize(PauseScreen.SCORE_LABEL_FONT_SIZE);
		scoreLabelPaint.setAntiAlias(true);
		scoreLabelPaint.setTypeface(face);
		scoreLabelPaint.setColor(ScreenButton.GAME_FONT_COLOR);
		
		scorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		scorePaint.setTextSize(PauseScreen.SCORE_FONT_SIZE);
		scorePaint.setAntiAlias(true);
		scorePaint.setTypeface(face);
		scorePaint.setColor(ScreenButton.GAME_FONT_COLOR);

		continueButton = new ScreenButton(PauseScreen.PAUSE_MENU_FIRST_BUTTON_X, PauseScreen.PAUSE_MENU_FIRST_BUTTON_Y,
				PauseScreen.BUTTON_WIDTH, PauseScreen.BUTTON_HEIGHT, continueButtonLabel,
				PauseScreen.PAUSE_MENU_FIRST_BUTTON_X + PauseScreen.PAUSE_MENU_LEFT_STR_XDIST,
				PauseScreen.PAUSE_MENU_FIRST_BUTTON_Y + PauseScreen.PAUSE_MENU_STR_YDIST,
				p);

		quitButton = new ScreenButton(PauseScreen.PAUSE_MENU_FIRST_BUTTON_X, PauseScreen.PAUSE_MENU_FIRST_BUTTON_Y + PauseScreen.PAUSE_MENU_DIST_BETWEEN_BUTTONS,
				PauseScreen.BUTTON_WIDTH, PauseScreen.BUTTON_HEIGHT, quitButtonLabel,
				PauseScreen.PAUSE_MENU_FIRST_BUTTON_X + PauseScreen.PAUSE_MENU_RIGHT_STR_XDIST,
				PauseScreen.PAUSE_MENU_FIRST_BUTTON_Y + PauseScreen.PAUSE_MENU_DIST_BETWEEN_BUTTONS + PauseScreen.PAUSE_MENU_STR_YDIST,
				p);
	}

	public PauseScreen(Game game, GameScreen gameScreen) {
		this(game);
		this.gameScreen = gameScreen;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if (continueButton.hitbox.contains(event.x, event.y)) {
					//CONTINUE GAME
					bgPainted = false;
					continueButton.setNextScreen(gameScreen);
					game.setScreen(continueButton.nextScreen);               
				}
				
				if (quitButton.hitbox.contains(event.x, event.y)) {
					bgPainted = false;
					Assets.stopAllMusic();
					quitButton.setNextScreen(new MainMenuScreen(game));
					game.setScreen(quitButton.nextScreen);               
				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		if(!bgPainted) {
			g.drawImage(Assets.pauseMenu, 0, 0);
			g.drawRotatedString(pauseLabel, TITLE_X, TITLE_Y, TITLE_ANGLE, continueButton.paint);
			g.drawString(continueButton.label, continueButton.labelX, continueButton.labelY, continueButton.paint);
			g.drawString(quitButton.label, quitButton.labelX, quitButton.labelY, quitButton.paint);
			g.drawString(scoreLabel, SCORE_LABEL_X, SCORE_LABEL_Y, scoreLabelPaint);
			g.drawString(Integer.toString(gameScreen.getScore()), SCORE_X, SCORE_Y, scorePaint);
			bgPainted = true;
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		bgPainted = false;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		bgPainted = false;
		game.getInput().getTouchEvents().clear();
		game.setScreen(gameScreen);
	}
	
	@Override
	public void pauseMusic() {
		List<Music> musics = Assets.getMusics();
		for(Music music : musics) {
			if(music.isPlaying()) {
				music.pause();
				pausedMusics.add(music);
			}
		}
	}

	@Override
	public void resumeMusic() {
		for(Music music : pausedMusics) {
			music.play();
		}
	}

}
