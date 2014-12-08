package com.javadocmd.rpl.terminal;

import com.badlogic.gdx.scenes.scene2d.Event;

public class CommandEvent extends Event {

	private String command;

	public CommandEvent(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
}
