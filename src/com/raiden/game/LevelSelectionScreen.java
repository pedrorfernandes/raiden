package com.raiden.game;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Screen;
import com.raiden.framework.Input.TouchEvent;

public class LevelSelectionScreen extends Screen {

	private final static int TITLE_X = 370;
	private final static int TITLE_Y = 210;
	private final static int TITLE_ANGLE = -27;

	private final static int SELECT_BUTTON_X = 0;
	private final static int SELECT_BUTTON_Y = 780;
	private final static int SELECT_BUTTON_WIDTH = 640;
	private final static int SELECT_BUTTON_HEIGHT = 140;

	private final static int LEVEL_LABEL_X_DIST = 400;
	private final static int LEVEL_LAVEL_Y_DIST = 85;

	private final static int NEXT_BUTTON_X = 640;
	private final static int NEXT_BUTTON_Y = 780;
	private final static int NEXT_BUTTON_WIDTH = 160;
	private final static int NEXT_BUTTON_HEIGHT = 140;

	private final static int HIGHSCORE_LABEL_X = 20;
	private final static int HIGHSCORE_LABEL_Y = 1027;

	private final static int RESET_X = 700;
	private final static int RESET_Y = 967;
	private final static int RESET_SIDE = 75;

	private final static int POPUP_X = 85;
	private final static int POPUP_Y = 432;
	private final static int POPUP_WIDTH = 630;
	private final static int POPUP_HEIGHT = 375;
	private final static int POPUP_STR_X = POPUP_X + POPUP_WIDTH/2;
	private final static int POPUP_STR_Y = POPUP_Y + POPUP_HEIGHT/3;

	private final static int POPUP_CANCEL_X = 85;
	private final static int POPUP_CANCEL_Y = POPUP_Y + 255;
	private final static int POPUP_CONFIRM_X = 590;
	private final static int POPUP_CONFIRM_Y = POPUP_Y + 255;
	private final static int POPUP_BUTTONS_SIDE = 125;

	private Screen previousScreen;

	private boolean onPopup;

	//Level related buttons
	private ScreenButton selectButton;
	private ScreenButton nextButton;
	private ScreenButton resetButton;

	private String levelSelectionTitle = "Levels";
	private Paint levelSelectionPaint;

	private String levelLabel = "Level ";
	private Paint levelLabelPaint;
	private int currentLevelDisplayed;

	private String highscoreLabel = "Highscore: ";
	private Paint highscoreLabelPaint;
	private Paint highscorePaint;

	//Pop up elements
	private ScreenButton selectYes;
	private ScreenButton selectNo;
	private String notificationText1 = "Reset this level's";
	private String notificationText2 = "score?";
	private Paint notificationPaint;

	private LevelSelectionScreen(Game game) {
		super(game);
		game.loadHighscores();
		currentLevelDisplayed = 1;

		Typeface face=Typeface.createFromAsset(game.getAssets(), ScreenButton.GAME_FONT);

		levelSelectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		levelSelectionPaint.setTextAlign(Paint.Align.CENTER);
		levelSelectionPaint.setTextSize(90);
		levelSelectionPaint.setAntiAlias(true);
		levelSelectionPaint.setTypeface(face);
		levelSelectionPaint.setColor(ScreenButton.GAME_FONT_COLOR);
		levelSelectionPaint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);

		levelLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		levelLabelPaint.setTextSize(65);
		levelLabelPaint.setAntiAlias(true);
		levelLabelPaint.setTypeface(face);
		levelLabelPaint.setColor(ScreenButton.GAME_FONT_COLOR);

		highscoreLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		highscoreLabelPaint.setTextSize(65);
		highscoreLabelPaint.setAntiAlias(true);
		highscoreLabelPaint.setTypeface(face);
		highscoreLabelPaint.setColor(ScreenButton.GAME_FONT_COLOR);

		highscorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		highscorePaint.setTextSize(50);
		highscorePaint.setAntiAlias(true);
		highscorePaint.setTypeface(face);
		highscorePaint.setColor(Color.WHITE);

		notificationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		notificationPaint.setTextAlign(Paint.Align.CENTER);
		notificationPaint.setTextSize(65);
		notificationPaint.setAntiAlias(true);
		notificationPaint.setTypeface(face);
		notificationPaint.setColor(ScreenButton.GAME_FONT_COLOR);

		selectButton = new ScreenButton(SELECT_BUTTON_X, SELECT_BUTTON_Y,
				SELECT_BUTTON_WIDTH, SELECT_BUTTON_HEIGHT, levelLabel,
				SELECT_BUTTON_X + LEVEL_LABEL_X_DIST, SELECT_BUTTON_Y + LEVEL_LAVEL_Y_DIST,
				levelLabelPaint);

		nextButton = new ScreenButton(NEXT_BUTTON_X, NEXT_BUTTON_Y, NEXT_BUTTON_WIDTH, NEXT_BUTTON_HEIGHT);

		resetButton = new ScreenButton(RESET_X, RESET_Y, RESET_SIDE, RESET_SIDE);

		selectNo = new ScreenButton(POPUP_CANCEL_X, POPUP_CANCEL_Y, POPUP_BUTTONS_SIDE, POPUP_BUTTONS_SIDE);
		selectYes = new ScreenButton(POPUP_CONFIRM_X, POPUP_CONFIRM_Y, POPUP_BUTTONS_SIDE, POPUP_BUTTONS_SIDE);
	}

	public LevelSelectionScreen(Game game, Screen previousScreen) {
		this(game);
		this.previousScreen = previousScreen;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				if(onPopup) {

					if (selectNo.hitbox.contains(event.x, event.y)) {
						onPopup = false;     
					}

					if (selectYes.hitbox.contains(event.x, event.y)) {
						game.getLevel(currentLevelDisplayed).resetHighscore();
						game.saveHighscores();
						game.loadHighscores();
						onPopup = false;
					}

				}
				else {
					if (selectButton.hitbox.contains(event.x, event.y)) {
						Assets.stopAllMusic();
						selectButton.setNextScreen(new GameScreen(game, currentLevelDisplayed));
						game.setScreen(selectButton.nextScreen);               
					}

					if (nextButton.hitbox.contains(event.x, event.y)) {
						int nextLevel = currentLevelDisplayed + 1;
						if(game.getLevel(nextLevel) != null) {
							currentLevelDisplayed = nextLevel;
						}
						else {
							currentLevelDisplayed = 1;
						}          
					}

					if (resetButton.hitbox.contains(event.x, event.y)) {
						onPopup = true;     
					}
				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		g.clearScreen(Color.BLACK);

		g.drawImage(Assets.levelSelectionMenu, 0, 0);
		g.drawImage(Assets.refreshButtonImg, resetButton.x, resetButton.y);

		g.drawRotatedString(levelSelectionTitle, TITLE_X, TITLE_Y, TITLE_ANGLE, levelSelectionPaint);

		g.drawString(selectButton.label, selectButton.labelX, selectButton.labelY, selectButton.paint);
		g.drawString(Integer.toString(currentLevelDisplayed),
				selectButton.labelX + (int) selectButton.paint.measureText(selectButton.label) + 5,
				selectButton.labelY, selectButton.paint);

		g.drawString(highscoreLabel, HIGHSCORE_LABEL_X, HIGHSCORE_LABEL_Y, highscoreLabelPaint);
		g.drawString(Integer.toString(game.getLevel(currentLevelDisplayed).getHighscore()),
				HIGHSCORE_LABEL_X + (int) selectButton.paint.measureText(highscoreLabel) + 5,
				HIGHSCORE_LABEL_Y, highscorePaint);

		if(onPopup) {
			g.drawARGB(155, 0, 0, 0);
			g.drawImage(Assets.popupImg, POPUP_X, POPUP_Y);
			g.drawString(notificationText1, POPUP_STR_X, POPUP_STR_Y, notificationPaint);
			g.drawString(notificationText2, POPUP_STR_X, POPUP_STR_Y + 70, notificationPaint);
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
		if(onPopup) {
			onPopup = false;
		}
		else {
			game.getInput().getTouchEvents().clear();
			game.setScreen(previousScreen);
		}
	}

}
