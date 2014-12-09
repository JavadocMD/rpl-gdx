package com.javadocmd.rpl.command;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class BlinkCommand implements Command {

	@Override
	public boolean matches(String command) {
		return "BLINK".equals(command);
	}

	@Override
	public Script getScript(String command, final Direktor direktor) {
		return SCRIPT;
	}

	private static final Script SCRIPT = new Script() {
		@Override
		public Action play(Puppetteer t) {
			return Actions.sequence(
				t.crawlEmpty(SHORT, 1),
				t.printBlink(SHORT, Alignment.LEFT, 
					" DON'T BLINK           DON'T BLINK           DON'T BLINK    \n" +
					"            DON'T BLINK           DON'T BLINK               \n" +
					" DON'T BLINK           DON'T BLINK           DON'T BLINK    \n" +
					"            DON'T BLINK           DON'T BLINK               \n" +
					" DON'T BLINK           DON'T BLINK           DON'T BLINK    \n" +
					"            DON'T BLINK           DON'T BLINK               "
				),
				t.crawlEmpty(SHORT, 1),
				t.ready());
		}
	};
}
