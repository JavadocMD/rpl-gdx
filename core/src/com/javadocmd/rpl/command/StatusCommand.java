package com.javadocmd.rpl.command;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.javadocmd.rpl.mode.StatusMode;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;

public class StatusCommand implements Command {

	@Override
	public boolean matches(String command) {
		return "STATUS".equals(command);
	}

	@Override
	public Script getScript(String command, final Direktor direktor) {
		return SCRIPT;
	}

	private static final Script SCRIPT = new Script() {

		@Override
		public Action play(Puppetteer t) {
			return t.enterMode(new StatusMode());
		}
	};
}
