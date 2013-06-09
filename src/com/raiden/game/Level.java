package com.raiden.game;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A level is a composition of timed enemy spawns.
 */
public class Level {
	private ArrayList<Spawn> spawns;
	static private ArrayList<FlightPattern> flightPatterns;
	private int currentTime, currentSpawn;
	private GameScreen gameScreen;
	
	private int highScore;
	private String levelContents;
	private boolean initialized;
	
	// iterating variables
	private static Spawn spawn;
	
	static {
		flightPatterns = new ArrayList<FlightPattern>();
		flightPatterns.add(null); // flightPattern 0 means the spawn has no pattern
	}

	/**
	 * Creates a new level.
	 * @param levelContents The JSON file with the level contents.
	 * @param currentHighScore The current high score for this level.
	 */
	public Level(String levelContents, int currentHighScore) {
		this.levelContents = levelContents;
		this.highScore = currentHighScore;
		this.initialized = false;
	}
	
	/**
	 * Initializes a level and reads the contents of the level into memory.
	 * @param gameScreen The current game screen.
	 */
	public void initialize(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		currentTime = 0; currentSpawn = 0;
		if (!this.initialized){
			spawns = new ArrayList<Spawn>();
			loadSpawns(levelContents);
		}
	}

	/**
	 * Adds a flight pattern to the flight patterns list.
	 * @param flightPattern The new flight pattern.
	 */
	public static void addFlightPattern(FlightPattern flightPattern){
		flightPatterns.add(flightPattern);
	}
	
	/**
	 * Sets a new list of flight patterns.
	 * @param flightPatterns The new list of flight patterns.
	 */
	public static void setFlightPatterns(ArrayList<FlightPattern> flightPatterns){
		Level.flightPatterns = flightPatterns;
	}
	
	/**
	 * Adds a spawn to the level.
	 * @param time Time when the enemy will spawn (1000 -> 1 second).
	 * @param x The starting X coordinate.
	 * @param y The starting Y coordinate.
	 * @param angle The starting angle.
	 * @param enemyType The enemy type to spawn.
	 * @param flightPatternNumber The flight pattern of the enemy (null if none).
	 * @param powerUpDrop The power up that the enemy will drop on death (null if none).
	 */
	public void addSpawn(int time, int x, int y, float angle, Enemy.Type enemyType, int flightPatternNumber, PowerUp.Type powerUpDrop){
		Spawn spawn = new Spawn(time, x, y, angle, enemyType, flightPatterns.get(flightPatternNumber), powerUpDrop);
		spawns.add(spawn);
	}
	
	/**
	 * Updates the level and spawns enemies in the game screen on time.
	 * @param deltaTime The time passed since the last update.
	 */
	public void update(float deltaTime) {
		if ( currentSpawn < spawns.size() ) {
			currentTime += (int)deltaTime;
			spawn = spawns.get(currentSpawn);
			while (currentTime >= spawn.time){
				gameScreen.spawnEnemy(spawn.x, spawn.y, spawn.angle, spawn.enemyType, spawn.flightPattern, spawn.powerUpDrop);
				currentSpawn++;
				if (currentSpawn >= spawns.size() ) return;
				spawn = spawns.get(currentSpawn);
			}
		}
	}
	
	private static final String FLIGHT_PATTERNS = "flight patterns", MOVEMENTS = "movements", 
			ANGLE = "angle", DURATION = "duration", DIRECTION = "direction";

	/**
	 * Loads the flight patterns from a JSON file.
	 * @param fileContents The JSON file with the patterns.
	 */
	public static void loadFlightPatterns(String fileContents) {
		try {
			JSONObject json = new JSONObject(fileContents);
			JSONArray patterns;
			patterns = json.getJSONArray(FLIGHT_PATTERNS);
			for (int i = 0; i < patterns.length(); i++) {
				FlightPattern flightPattern = new FlightPattern();
				JSONObject pattern = patterns.getJSONObject(i);
				JSONArray movements = pattern.getJSONArray(MOVEMENTS);
				for (int j = 0; j < movements.length(); j++) {
					JSONObject movement = movements.getJSONObject(j);
					int angle = movement.getInt(ANGLE);
					int duration = movement.getInt(DURATION);
					Direction direction = Direction.getDirection(movement.getString(DIRECTION));
					flightPattern.addMovement(angle, duration, direction);
				}
				addFlightPattern(flightPattern);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private static final String SPAWNS = "spawns", TIME = "time", 
			X = "x", Y = "y", TYPE = "type", PATTERN = "pattern", POWERUP = "drop";
	
	/**
	 * Loads the level spawns from a JSON file. 
	 * @param levelContents The JSON file contents.
	 */
	public void loadSpawns(String levelContents){
		try {
			JSONObject json = new JSONObject(levelContents);
			JSONArray spawns;
			spawns = json.getJSONArray(SPAWNS);
			for (int i = 0; i < spawns.length(); i++) {
				JSONObject spawn = spawns.getJSONObject(i);
				int time = spawn.getInt(TIME);
				int x = spawn.getInt(X);
				int y = spawn.getInt(Y);
				float angle = (float)spawn.getDouble(ANGLE);
				Enemy.Type enemyType = Enemy.Type.getType(spawn.getString(TYPE));
				int flightPatternNumber = spawn.getInt(PATTERN);
				PowerUp.Type powerUpType = PowerUp.Type.getType(spawn.getString(POWERUP));
				addSpawn(time, x, y, angle, enemyType, flightPatternNumber, powerUpType);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return The high score of the level.
	 */
	public int getHighscore(){
		return highScore;
	}
	
	/**
	 * Resets the current high score to 0.
	 */
	public void resetHighscore() {
		highScore = 0;
	}
	
	/**
	 * Checks if the current score is better than the high score and updates it.
	 * @param currentScore The current player score.
	 */
	public void updateHighscore(int currentScore){
		if (currentScore > highScore)
			highScore = currentScore;
	}
	
	/**
	 * @return If a level is over (no more enemies will spawn).
	 */
	public boolean levelOver() {
		return currentSpawn >= spawns.size();
	}

	/**
	 * A spawn is the set of information necessary to make an enemy appear 
	 * on screen in a timed and organized way.
	 */
	public class Spawn {
		public int time;
		public int x, y;
		public float angle;
		public Enemy.Type enemyType;
		public FlightPattern flightPattern;
		public PowerUp.Type powerUpDrop;
		
		/**
		 * Creates a new spawn.
		 * @param time Time when the enemy will spawn (1000 -> 1 second).
		 * @param x The starting X coordinate.
		 * @param y The starting Y coordinate.
		 * @param angle The starting angle.
		 * @param enemyType The enemy type to spawn.
		 * @param flightPatternNumber The flight pattern of the enemy (null if none).
		 * @param powerUpDrop The power up that the enemy will drop on death (null if none).
		 */
		public Spawn(int time, int x, int y, float angle, Enemy.Type enemyType, FlightPattern flightPattern, PowerUp.Type powerUpDrop) {
			this.time = time;
			this.x = x;
			this.y = y;
			this.angle = angle;
			this.enemyType = enemyType;
			this.flightPattern = flightPattern;
			this.powerUpDrop = powerUpDrop;
		}
	}
}