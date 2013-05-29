package com.raiden.game;

import com.raiden.framework.Screen;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class ScreenButton {
	
	public final static String GAME_FONT = "MYRIADPRO-REGULAR.OTF";
	public final static int MENU_BUTTONS_FONT_SIZE = 100;
	public final static int GAME_FONT_COLOR = Color.rgb(255, 195, 0);
	
	public final static int MAIN_MENU_PLAY_X = 0;
	public final static int MAIN_MENU_PLAY_Y = 580;
	
	public final static int BUTTON_HEIGHT = 160;
	public final static int BUTTON_WIDTH = 800;
	
	public final static int MAIN_MENU_STR_XDIST = 450;
	public final static int MAIN_MENU_STR_YDIST = 117;
	
	public final static int DIST_BETWEEN_BUTTONS = BUTTON_HEIGHT + 97;
	
	public Rect hitbox;
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public String label;
	public int labelX;
	public int labelY;
	Paint paint;
	
	public Screen nextScreen;
	
	
	public ScreenButton(int x, int y, int width, int height, String label, int labelX, int labelY, Paint paint, Screen nextScreen) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.label = label;
		this.labelX = labelX;
		this.labelY = labelY;
		this.paint = paint;
		this.nextScreen = nextScreen;
		
		hitbox = new Rect(x, y, x + width, y + height);
	}

}
