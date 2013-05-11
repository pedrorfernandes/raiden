package com.raiden.game;

import com.raiden.framework.Screen;
import com.raiden.framework.implementation.AndroidGame;

public class Raiden extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this); 
    }
    
    @Override
    public void onBackPressed() {
    	getCurrentScreen().backButton();
    }
    
    
}