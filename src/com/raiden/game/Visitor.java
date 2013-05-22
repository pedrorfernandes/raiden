package com.raiden.game;

public interface Visitor {
	public void visit(Ship ship);
	public void visit(Bullet bullet);
}