package com.javadocmd.rpl.util;

import com.badlogic.gdx.scenes.scene2d.Actor;

abstract public class IncorporealActor extends Actor {

	@Override
	public float getX() {
		return 0f;
	}
	
	@Override
	public float getY() {
		return 0f;
	}
	
	@Override
	public float getWidth() {
		return 0f;
	}
	
	@Override
	public float getHeight() {
		return 0f;
	}
}
