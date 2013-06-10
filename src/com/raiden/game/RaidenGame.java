package com.raiden.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;

import com.raiden.framework.Screen;
import com.raiden.framework.implementation.AndroidGame;

/**
 * The main game class that initializes the application.
 */
public class RaidenGame extends AndroidGame {
	
	public String flightPatterns;
	public ArrayList<Level> levels;
	public ArrayList<Integer> highScores;
	private static final String HIGHSCORES_TXT = "raidenHighscores.txt";
	private static final String LEVEL_FILE = "level";
	private MusicController musicController;
	
    @Override
    public Screen getInitScreen() {
    	
    	highScores = new ArrayList<Integer>();
    	
    	musicController = new MusicController();
    	
    	levels = getLevels();
    	
    	Assets.resources = getResources();
    	
    	Level.loadFlightPatterns(flightPatterns);
        
    	return new SplashLoadingScreen(this);
    }
    
    @Override
    public void onBackPressed() {
    	getCurrentScreen().backButton();
    }
    
    public MusicController getMusicController(){
    	return musicController;
    }
    
    /**
     * Reads the flight patterns file, and checks for all level files (through reflections)
     * and loads them.
     * @return The list of levels.
     */
    private ArrayList<Level> getLevels(){
    	// load the flight patterns for the levels
    	InputStream is = getResources().openRawResource(R.raw.flightpatterns);
    	flightPatterns = convertStreamToString(is);
    	
    	// load the high scores
    	this.loadHighscores();
    	
    	// get each level file by reflections
    	ArrayList<Integer> levelFiles = new ArrayList<Integer>();
    	Field[] fields = R.raw.class.getFields();
    	
    	int levelNumber = 1;
    	String levelFilename = LEVEL_FILE + Integer.toString(levelNumber);
    	
    	for(int i = 0; i < fields.length; i++)
    		try {
    			Field field = fields[i];
    			if ( field.getName().equals(levelFilename) ){
    				levelFiles.add(field.getInt(null));
    				i = 0; // restart scan
    				levelNumber++;
    				levelFilename = LEVEL_FILE + Integer.toString(levelNumber);
    			}
    		} catch (IllegalArgumentException e) {	
    		} catch (IllegalAccessException e) { }
    	
    	ArrayList<Level> loadedLevels = new ArrayList<Level>();
    	
    	// load each level
    	int i = 0;
    	for (Integer file : levelFiles) {
    		is = getResources().openRawResource(file);
        	String levelContents = convertStreamToString(is);
        	int highscore = getHighscore(i); i++;
        	loadedLevels.add( new Level(levelContents, highscore) );
		}

    	try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return loadedLevels;
    }
    
    /**
     * @param levelIndex The index of a level.
     * @return The high score of the level.
     */
    public int getHighscore(int levelIndex){
    	if (levelIndex >= highScores.size() ) return 0;
    	return highScores.get(levelIndex);
    }
    
    /**
     * @param levelNumber The number of the level (starting at 1).
     * @return The specified level.
     */
    public Level getLevel(int levelNumber){
    	if (levelNumber < 1 || levelNumber > levels.size() ) return null;
    	return levels.get(levelNumber-1); // level 1 is index 0
    }
    
    /**
     * Saves the high scores of all the levels to the root of the SD card.
     */
    public void saveHighscores() {
    	BufferedWriter out = null;
    	try {

    		// Writes a file to the SD Card
    		out = new BufferedWriter(new OutputStreamWriter(fileIO.writeFile(HIGHSCORES_TXT)));

    		for (int i = 0; i < levels.size(); i++) {
    			out.write(Integer.toString(levels.get(i).getHighscore()));
    			out.write("\n");
    		}


    	} catch (IOException e) {
    	} finally {
    		try {
    			if (out != null)
    				out.close();
    		} catch (IOException e) {
    		}
    	}
    }

    /**
     * Loads all the high scores from a file in the root of the SD card
     * and updates every level with this information.
     */
    public void loadHighscores() {
    	BufferedReader in = null;
    	try {
    		// Reads file
    		in = new BufferedReader(new InputStreamReader(fileIO.readFile(HIGHSCORES_TXT)));

    		highScores.clear();
    		String line;
    		while( ( line = in.readLine() ) != null ){
    			highScores.add( Integer.parseInt(line) );
    	    }

    	} catch (IOException e) {
    		// Catches errors. Default values are used.
    	} catch (NumberFormatException e) {
    		// Catches errors. Default values are used.
    	} finally {
    		try {
    			if (in != null)
    				in.close();
    		} catch (IOException e) {
    		}
    	}
    }
    
    /**
     * Converts the contents of a stream to a string.
     * @param is A input stream
     * @return A string with the contents
     */
    private static String convertStreamToString(InputStream is) {
    	Writer writer = new StringWriter();
    	char[] buffer = new char[1024];
    	try {
    	    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    	    int n;
    	    while ((n = reader.read(buffer)) != -1) {
    	        writer.write(buffer, 0, n);
    	    }
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    	String jsonString = writer.toString();
		return jsonString;
    }
}