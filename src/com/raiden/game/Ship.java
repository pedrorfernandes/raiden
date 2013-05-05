package com.raiden.game;

public class Ship {

	// ship speed
	private int moveSpeed = 10;
	
	// image sprite half size
	private int halfSizeX = 78;
	private int halfSizeY = 63;

	// initial starting point
	private int centerX = GameScreen.screenSize.x / 2;
	private int centerY = GameScreen.screenSize.y - halfSizeY * 6;
	
	private boolean readyToFire = true;

	// position to move next
	private int newX = centerX;
	private int newY = centerY;
	
	// box which the player can move in
	private int minX = 0;
	private int minY = 0;
	private int maxX = GameScreen.screenSize.x - 1;
	private int maxY = GameScreen.screenSize.y - 1;
	
    final int LOW_THRESHOLD = 0;
    final int HIGH_THRESHOLD = 7;
    private int turningThreshold = HIGH_THRESHOLD;

	public void update() {
		if (newX < centerX) {
			if ( (centerX - newX) < moveSpeed)
				centerX -= (centerX - newX);
			else
				centerX -= moveSpeed;
		} 
		else if (newX > centerX){
			if ( (newX - centerX) < moveSpeed)
				centerX += (newX - centerX);
			else
				centerX += moveSpeed;
		}
		
		if (newY < centerY) {
			if ( (centerY - newY) < moveSpeed)
				centerY -= (centerY - newY);
			else
				centerY -= moveSpeed;
		} 
		else if (newY > centerY) {
			if ( (newY - centerY) < moveSpeed)
				centerY += (newY - centerY);
			else
				centerY += moveSpeed;
		}
	}
	
	public void move(int x, int y){
		newX += x;
		newY += y;
		if (newX < minX)
			newX = minX;
		if (newY < minY)
			newY = minY;
		if (newX > maxX)
			newX = maxX;
		if (newY > maxY)
			newY = maxY;
	}
	
	public int getX(){
		return centerX-halfSizeX;
	}
	
	public int getY(){
		return centerY-halfSizeY;
	}
	
	public boolean isMoving(){
		return(centerX == newX && centerY == newY);
	}
	
	public boolean isMovingLeft(){
		if ( (newX - centerX) < 0 && Math.abs(newX - centerX) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			return true;
		}
		turningThreshold = HIGH_THRESHOLD;
		return false;
	}
	
	public boolean isMovingRight(){
		if ( (newX - centerX) > 0 && Math.abs(newX - centerX) > turningThreshold ){
			turningThreshold = LOW_THRESHOLD;
			return true;
		}
		turningThreshold = HIGH_THRESHOLD;
		return false;
	}
}
