package com.raiden.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.raiden.animation.Animation;
import com.raiden.framework.Game;
import com.raiden.framework.Graphics;
import com.raiden.framework.Image;
import com.raiden.framework.Input.TouchEvent;
import com.raiden.framework.Music;
import com.raiden.framework.Screen;

/**
 * The game screen holds all the variables important to the gameplay such as the
 * the hero, enemies, special effects, background, etc.
 * It updates all these elements and draws them into the screen.
 */
public class GameScreen extends Screen {

	private static final String START_MISSION_MESSAGE = "Show them what you're made of!";

	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;
	
	private ArrayList<Music> pausedMusics = new ArrayList<Music>();

	private int score = 0;

	private boolean showPauseButton = true;
	private ScreenButton pauseButton;
	private boolean pauseScreenReady = false;

	// Delay showing the game over screen for a while
	private static final int GAME_OVER_COUNTER = 3500;
	private int gameOverScreenCounter = GAME_OVER_COUNTER;
	private String finalScore = "Final Score: ";
	private boolean highscoreBeaten = false;
	private String newHighscore = "New highscore: ";
	private String highscore = "Level highscore: ";

	private int livesLeft = 1;
	Paint paint;

	public Hero hero;
	private Image heroImage;
	public Animation heroAnimation, heroTurningLeftAnimation, heroTurningRightAnimation;
	private Image bulletImage;
	private Background background;

	public Level level;
	public int levelNumber;

	// touch and input variables
	private Point dragPoint;

	// screen size variables
	public Point screenSize;

	private static SoundController soundController;
	private static AnimationController animationController;
	private static EffectsController effectsController;
	private static ArmorObserver armorObserver;
	private static ScoreObserver scoreObserver;

	private static ArrayList<Animation> specialEffects;
	private static final float NORMAL_SCALE = 1.0f;

	// enemy variables
	private Image enemyImage;
	private static final int MAX_ENEMIES = 20;
	public static Enemy[] enemies = new Enemy[MAX_ENEMIES];

	private static final float ANGLE_DOWN = 270.0f;
	private static final float ANGLE_UP = 90.0f;

	// power up variables
	private static final int MAX_POWER_UPS = 15;
	private static PowerUp[] powerUps = new PowerUp[MAX_POWER_UPS];

	// constants for animation
	private static final int ANIMATION_UPDATE = 1;

	// debug variables
	private static boolean HITBOXES_VISIBLE = false;
	private static Paint hitboxColor;

	// iterating variables
	private static int length;
	private static Bullet bullet;
	private static Enemy enemy;
	private static Image image;
	private static PowerUp powerUp;
	private static Animation specialEffect;

	private final static int PAUSE_BUTTON_SIDE = 75;
	private final static int FIRST_BUTTON_X = 700;
	private final static int PAUSE_BUTTON_Y = 25;
	
	/**
	 * Creates a new game screen.
	 * @param game The game.
	 * @param levelNumber The level to play.
	 */
	public GameScreen(Game game, int levelNumber) {
		super(game);

		screenSize = game.getSize();
		Collidable.setBounds(screenSize);
		Collidable.setGameScreen(this);

		// Initialize game objects here

		pauseButton = new ScreenButton(GameScreen.FIRST_BUTTON_X, GameScreen.PAUSE_BUTTON_Y,
				GameScreen.PAUSE_BUTTON_SIDE, GameScreen.PAUSE_BUTTON_SIDE);

		hero = new Hero();
		hero.setTargets(enemies);
		hero.setPowerUps(powerUps);

		heroAnimation = Assets.getHeroAnimation();
		heroTurningLeftAnimation = Assets.getHeroTurningLeftAnimation();
		heroTurningRightAnimation = Assets.getHeroTurningRightAnimation();

		heroImage = heroAnimation.getImage();
		
		background = new Background(this);

		// observers
		effectsController = new EffectsController(this);
		soundController = new SoundController(this);
		animationController = new AnimationController(this, heroAnimation);
		armorObserver = new ArmorObserver(this);
		scoreObserver = new ScoreObserver(this);

		hero.addObserver(effectsController);
		hero.addObserver(soundController);
		hero.addObserver(animationController);
		hero.addObserver(game.getMusicController());
		hero.addObserver(armorObserver);

		for (int i = 0; i < MAX_ENEMIES; i++)
		{
			enemies[i] = new Enemy(hero);
			enemies[i].addObserver(effectsController);
			enemies[i].addObserver(soundController);
			enemies[i].addObserver(scoreObserver);
		}

		for (int i = 0; i < MAX_POWER_UPS; i++) {
			powerUps[i] = new PowerUp();
			powerUps[i].addObserver(scoreObserver);
		}

		specialEffects = new ArrayList<Animation>();

		this.levelNumber = levelNumber;
		level = game.getLevel(levelNumber);
		level.initialize(this);
		
		game.getMusicController().play(Assets.missionMusic);

		dragPoint = new Point();

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(45);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		hitboxColor = new Paint();
		hitboxColor.setColor(Color.MAGENTA);
	}

	/**
	 * @return The current score of the player.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the current score of the player.
	 * @param score The new score.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return If the high score of the current level has been beaten.
	 */
	public boolean isHighscoreBeaten() {
		return highscoreBeaten;
	}

	/**
	 * Sets high score beaten boolean.
	 * @param highscoreBeaten The new boolean.
	 */
	public void setHighscoreBeaten(boolean highscoreBeaten) {
		this.highscoreBeaten = highscoreBeaten;
	}

	/**
	 * @return The lives left for the hero.
	 */
	public int getLivesLeft() {
		return livesLeft;
	}

	/**
	 * Sets the number of lives left for the hero.
	 * @param livesLeft New number of lives left.
	 */
	public void setLivesLeft(int livesLeft) {
		this.livesLeft = livesLeft;
	}

	/**
	 * Spawns an enemy.
	 * @param x The X coordinate.
	 * @param y The Y coordinate
	 * @param angle The starting angle.
	 * @param type The type of enemy.
	 * @param flightPattern The flight pattern to execute (null if none).
	 * @param PowerUpDrop The type of power up to drop on death (null if none).
	 * @return The spawned enemy (null if none could be spawned).
	 */
	public Enemy spawnEnemy(int x, int y, float angle, Enemy.Type type, FlightPattern flightPattern, PowerUp.Type PowerUpDrop){
		for (int i = 0; i < MAX_ENEMIES; i++)
		{
			enemy = enemies[i];
			if ( !enemy.isInGame() ){
				enemy.spawn(x, y, angle, type, flightPattern, PowerUpDrop);
				return enemy;
			}
		}
		return null;
	}
	
	/**
	 * @return If there are enemies still in game.
	 */
	public boolean enemiesLeft() {
		for(Enemy enemy : enemies) {
			if(enemy.isInGame()) return true;
		}
		return false;
	}

	/**
	 * Spawns a power up.
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @param type The type of power up.
	 * @return The spawned power up (null if none could be spawned).
	 */
	public PowerUp spawnPowerUp(int x, int y, PowerUp.Type type){
		for (int i = 0; i < MAX_POWER_UPS; i++)
		{
			powerUp = powerUps[i];
			if ( !powerUp.isVisible() ){
				powerUp.spawn(x, y, type);
				return powerUp;
			}
		}
		return null;
	}

	/**
	 * Adds a new special effect to the game.
	 * @param specialEffect The new special effect.
	 */
	public void addSpecialEffect(Animation specialEffect){
		specialEffects.add(specialEffect);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {

		if (touchEvents.size() > 0){
			state = GameState.Running;
			hero.notifyObservers(Event.GameStart);
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

		boolean goOnPause = false;

		// All touch input is handled here
		length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {

				if (pauseButton.hitbox.contains(event.x, event.y) && showPauseButton) {
					//Pause menu
					//pause();
					hero.setAutoFire(false);
					goOnPause = true;
				}
				else {
					showPauseButton = false;

					dragPoint.x = event.x;
					dragPoint.y = event.y;
					hero.setAutoFire(true);
				}
			}

			if (event.type == TouchEvent.TOUCH_UP) {
				showPauseButton = true;
				hero.setAutoFire(false);
			}

			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				hero.move(event.x - dragPoint.x, event.y - dragPoint.y);
				dragPoint.x = event.x; dragPoint.y = event.y;
			}

			if (event.type == TouchEvent.TOUCH_HOLD) {
				// nothing for now
			}

		}

		updateElements(deltaTime);

		if(goOnPause)
			pause();

	}

	/**
	 * Updates all the game elements.
	 * @param deltaTime The time passed since the last update.
	 */
	private void updateElements(float deltaTime) {

		// Check game conditions
		
		if(livesLeft == 0) {
			if(gameOverScreenCounter > 0) {
				gameOverScreenCounter -= deltaTime;
			}
			else {
				state = GameState.GameOver;
				if(score > level.getHighscore()) {
					level.updateHighscore(score);
					game.saveHighscores();
					highscoreBeaten = true;
				}
			}
		}
		else if(level.levelOver() && !enemiesLeft()) {

			if(gameOverScreenCounter > 0) {
				if (gameOverScreenCounter == GAME_OVER_COUNTER) {
					game.getMusicController().play(Assets.missionVictory);
				}
				gameOverScreenCounter -= deltaTime;
			}
			else {
				if(score > level.getHighscore()) {
					level.updateHighscore(score);
					game.saveHighscores();
					highscoreBeaten = true;
				}
				
				hero.notifyObservers(Event.Victory);
										
				hero.setAutoFire(false);
				game.setScreen(new LevelOverScreen(game, this));
			}
		}

		// This is where all the game updates happen.

		level.update(deltaTime);
		background.update(deltaTime);

		// update the hero's bullets
		//length = Ship.shotsFired.size();
		for (int i = 0; i < hero.shots.length; i++) {
			bullet = hero.shots[i];
			bullet.update(deltaTime);
		}

		hero.update(deltaTime);

		// update the enemies  
		length = enemies.length;
		for (int i = 0; i < length; i++) {
			enemy = enemies[i];
			enemy.update(deltaTime);
			// update the enemy bullets
			for (int j = 0; j < enemy.shots.length; j++) {
				bullet = enemy.shots[j];
				bullet.update(deltaTime);
			}
		}

		// update power ups
		length = powerUps.length;
		for (int i = 0; i < length; i++) {
			powerUp = powerUps[i];
			powerUp.update(deltaTime);
		}
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		if(pauseScreenReady) {
			pauseButton.setNextScreen(new PauseScreen(game, this));
			game.setScreen(pauseButton.nextScreen);
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 0 && event.x < 800 && event.y > 0 && event.y < 1280) {
					Assets.stopAllMusic();
					
					nullify();

					game.saveHighscores();
					game.loadHighscores();

					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		// First draw the game elements.

		//g.clearScreen(Color.rgb(58, 86, 104));
		background.paint(g);

		// hero drawing
		if (hero.visible){
			heroImage = animationController.getCurrenAnimation().getImage();

			g.drawImage(heroImage,
					hero.x - heroImage.halfWidth, 
					hero.y - heroImage.halfHeight);
			if (HITBOXES_VISIBLE) 
				g.drawCircle(hero.x, hero.y, hero.radius, hitboxColor);
		}

		// hero shots drawing
		length = hero.shots.length;
		for (int i = 0; i < length; i++) {
			Bullet bullet = hero.shots[i];
			if (!bullet.visible) continue;
			bulletImage = bullet.type.image;
			if (bullet.angle == ANGLE_UP){
				g.drawImage(bulletImage,
						bullet.x-bulletImage.halfWidth, 
						bullet.y-bulletImage.halfHeight);
			} else {
				g.drawRotatedImage(bulletImage, 
						bullet.x-bulletImage.halfWidth, 
						bullet.y-bulletImage.halfHeight,
						bulletImage.width,
						bulletImage.height, 
						bullet.angle,
						ANGLE_UP);
			}
			if (HITBOXES_VISIBLE)
				g.drawCircle(bullet.x, bullet.y, bullet.radius, hitboxColor);
		}

		// enemies drawing
		length = enemies.length;
		for (int i = 0; i < length; i++) {
			Enemy enemy = enemies[i];

			// enemy bullets drawing
			for (int j = 0; j < enemy.shots.length; j++) {
				Bullet bullet = enemy.shots[j];
				if (!bullet.visible) continue;
				bulletImage = bullet.type.image;
				if (bullet.angle == ANGLE_UP){
					g.drawImage(bulletImage, 
							bullet.x-bulletImage.halfWidth, 
							bullet.y-bulletImage.halfHeight);
				} else {
					g.drawRotatedImage(bulletImage, 
							bullet.x-bulletImage.halfWidth, 
							bullet.y-bulletImage.halfHeight,
							bulletImage.width,
							bulletImage.height,
							bullet.angle,
							ANGLE_UP);
				}
				if (HITBOXES_VISIBLE)
					g.drawCircle(bullet.x, bullet.y, bullet.radius, hitboxColor);
			}

			// draw the enemy
			if (!enemy.visible) continue;
			enemyImage = enemy.type.image;
			if (enemy.angle == ANGLE_DOWN){
				g.drawImage(enemyImage, 
						enemy.x-enemyImage.halfWidth, 
						enemy.y-enemyImage.halfHeight);
			} else {
				g.drawRotatedImage(enemyImage, 
						enemy.x-enemyImage.halfWidth, 
						enemy.y-enemyImage.halfHeight,
						enemyImage.width,
						enemyImage.height, 
						enemy.angle,
						ANGLE_DOWN);
			}
			if (HITBOXES_VISIBLE)
				g.drawCircle(enemy.x, enemy.y, enemy.radius, hitboxColor);
		}

		// power ups drawing
		length = powerUps.length;
		for (int i = 0; i < length; i++) {
			powerUp = powerUps[i];
			if (!powerUp.visible) continue;
			image = powerUp.type.image;
			g.drawImage(image, powerUp.x - image.halfWidth, 
					powerUp.y - image.halfHeight);
		}

		// special effects drawing
		for (int i = 0; i < specialEffects.size(); i++) {
			specialEffect = specialEffects.get(i);
			if (specialEffect == null) continue;

			// clean up special effects
			if (!specialEffect.active){
				specialEffects.remove(i);
				i--;
			}

			image = specialEffect.getImage();
			if (specialEffect.scale == NORMAL_SCALE){
				g.drawImage(image, specialEffect.x - image.halfWidth, 
						specialEffect.y - image.halfHeight);
			} else {
				g.drawScaledImage(image, 
						specialEffect.x - image.halfWidth, 
						specialEffect.y - image.halfHeight,
						specialEffect.x,
						specialEffect.y,
						specialEffect.scale);
			}
		}

		// hero health drawing
		for (ScreenCrack screenCrack : armorObserver.screenCracks) {
			image = ScreenCrack.image;
			g.drawImage(image, screenCrack.x - image.halfWidth,
					screenCrack.y - image.halfHeight);
		}

		animate(deltaTime);

		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}

	/**
	 * Updates all the animations and special effects on screen.
	 * @param deltaTime The time passed since the last update.
	 */
	private void animate(float deltaTime) {
		heroAnimation.update(ANIMATION_UPDATE);
		heroTurningLeftAnimation.update(ANIMATION_UPDATE);
		heroTurningRightAnimation.update(ANIMATION_UPDATE);
		length = specialEffects.size();
		for (int i = 0; i < length; i++) {
			specialEffect = specialEffects.get(i);
			if (specialEffect.active)
				specialEffect.update(ANIMATION_UPDATE);
		}

	}

	private void nullify() {

		// Set all variables to null.
		paint = null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString(START_MISSION_MESSAGE, 400, 640, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();

		if(showPauseButton) {
			g.drawImage(Assets.pauseButtonImg, pauseButton.x, pauseButton.y);
		}
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		pauseScreenReady = true;
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 801, 1281, Color.BLACK);
		g.drawString("GAME OVER", 400, 340, paint);
		g.drawString(finalScore, 400, 600, paint);
		g.drawString(Integer.toString(score), 400, 660, paint);
		
		if(highscoreBeaten) {
			g.drawString(newHighscore, 400, 820, paint);
		}
		else {
			g.drawString(highscore, 400, 820, paint);
		}
		g.drawString(Integer.toString(level.getHighscore()), 400, 880, paint);

	}

	@Override
	public void pause() {
		if (state == GameState.Running) {
			state = GameState.Paused;
		}
	}

	@Override
	public void resume() {
		if (state == GameState.Paused) {
			state = GameState.Running;
			showPauseButton = true;
			hero.setAutoFire(false);
			pauseScreenReady = false;
		}
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
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