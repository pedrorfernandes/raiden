package com.raiden.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.raiden.framework.Screen;
import com.raiden.framework.implementation.AndroidGame;

public class RaidenGame extends AndroidGame {
	
	public static String flightPatterns;
	public static String level1;
	
    @Override
    public Screen getInitScreen() {
    	
    	InputStream is1 = getResources().openRawResource(R.raw.flightpatterns);
    	flightPatterns = convertStreamToString(is1);
    	InputStream is2 = getResources().openRawResource(R.raw.level1);
    	level1 = convertStreamToString(is2);

    	try {
			is1.close(); is2.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        return new LoadingScreen(this); 
    }
    
    @Override
    public void onBackPressed() {
    	getCurrentScreen().backButton();
    }
    
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