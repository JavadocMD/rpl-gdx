package com.javadocmd.rpl.command;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class HelpCommand implements Command {

	@Override
	public boolean matches(String command) {
		return "HELP".equals(command);
	}

	@Override
	public Script getScript(String command, final Direktor direktor) {
		return SCRIPT;
	}
	
	private static final Script SCRIPT = new Script() {
		
		@Override
		public Action play(Puppetteer t) {
			return Actions.sequence(
				t.crawl(SHORT, Alignment.LEFT,
					"***************************************************************\n" +
					"*** HELP FEATURE DISABLED BY PARTY ORDER 47-A-301928 SEC 21 ***\n" +
					"***   CONTAkT YOUR IMMEDIATE SUPERVISOR IF YOU ARE UNABLE   ***\n" +
					"***   TO PERFORM YOUR DUTIES AS DIREkTED                    ***\n" +
					"***************************************************************"),
				t.ready());
		}
	};
}
