package com.javadocmd.rpl.terminal;

import com.badlogic.gdx.scenes.scene2d.Event;

public class NotificationEvent extends Event {

	public static enum EndGameType {
		NONE, MELTDOWN, TERMINATION;
	}
	
	final private EndGameType endGame;
	final private String message;

	public NotificationEvent(EndGameType endGame, String message) {
		this.endGame = endGame;
		this.message = message;
	}

	public boolean isEndGame() {
		return endGame != EndGameType.NONE;
	}
	
	public EndGameType getEndGame() {
		return endGame;
	}
	
	public String getMessage() {
		return message;
	}
}
