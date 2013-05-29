package com.raiden.game;

import com.raiden.framework.Screen;
import com.raiden.framework.implementation.AndroidGame;

public class RaidenGame extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new SplashLoadingScreen(this); 
    }
    
    @Override
    public void onBackPressed() {
    	getCurrentScreen().backButton();
    }
    
    
}