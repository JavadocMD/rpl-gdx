package com.javadocmd.rpl.command;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;

public class ClockCommand implements Command {

	@Override
	public boolean matches(String command) {
		return "CLOCK".matches(command) || "TIME".matches(command);
	}

	@Override
	public Script getScript(String command, Direktor direktor) {
		final long tics = direktor.getSim().getTics();
		return new Script() {
			@Override
			public Action play(Puppetteer t) {
				return Actions.sequence(
					t.println("CURRENT SESSION: " + tics + " minutes. WORK HARD FOR OUR LEADER."),
					t.ready()
				);
			}
		};
	}
}
