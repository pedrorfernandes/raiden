package com.raiden.game;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Level {
	private ArrayList<Spawn> spawns;
	static private ArrayList<FlightPattern> flightPatterns;
	private int currentTime, currentSpawn;
	private GameScreen gameScreen;
	
	// iterating variables
	private static Spawn spawn;
	
	static {
		flightPatterns = new ArrayList<FlightPattern>();
		flightPatterns.add(null); // flightPattern 0 means the spawn has no pattern
	}

	public Level(GameScreen gameScreen, String level) {

		spawns = new ArrayList<Spawn>();
		
		currentTime = 0; currentSpawn = 0;
		this.gameScreen = gameScreen;
		loadSpawns(level);
	}

	public static void addFlightPattern(FlightPattern flightPattern){
		flightPatterns.add(flightPattern);
	}
	
	public static void setFlightPatterns(ArrayList<FlightPattern> flightPatterns){
		Level.flightPatterns = flightPatterns;
	}
	
	public void addSpawn(int time, int x, int y, float angle, Enemy.Type enemyType, int flightPatternNumber){
		Spawn spawn = new Spawn(time, x, y, angle, enemyType, flightPatterns.get(flightPatternNumber));
		spawns.add(spawn);
	}
	
	public void update(float deltaTime) {
		if ( currentSpawn < spawns.size() ) {
			currentTime += (int)deltaTime;
			spawn = spawns.get(currentSpawn);
			while (currentTime >= spawn.time){
				gameScreen.spawnEnemy(spawn.x, spawn.y, spawn.angle, spawn.enemyType, spawn.flightPattern);
				currentSpawn++;
				if (currentSpawn >= spawns.size() ) return;
				spawn = spawns.get(currentSpawn);
			}
		}
	}
	
	private static final String FLIGHT_PATTERNS = "flight patterns", MOVEMENTS = "movements", 
			ANGLE = "angle", DURATION = "duration", DIRECTION = "direction";

	public static void loadFlightPatterns() {
		try {
			String contents = RaidenGame.flightPatterns;
			JSONObject json = new JSONObject(contents);
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
	
	private static final String SPAWNS = "spawns", TIME = "time", X = "x", Y = "y", TYPE = "type", PATTERN = "pattern";
	
	public void loadSpawns(String level){
		try {
			JSONObject json = new JSONObject(level);
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
				addSpawn(time, x, y, angle, enemyType, flightPatternNumber);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public class Spawn {
		public int time;
		public int x, y;
		public float angle;
		public Enemy.Type enemyType;
		public FlightPattern flightPattern;
		
		public Spawn(int time, int x, int y, float angle, Enemy.Type enemyType, FlightPattern flightPattern) {
			this.time = time;
			this.x = x;
			this.y = y;
			this.angle = angle;
			this.enemyType = enemyType;
			this.flightPattern = flightPattern;
		}
	}
}