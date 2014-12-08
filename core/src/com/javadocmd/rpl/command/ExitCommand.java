package com.javadocmd.rpl.command;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.script.ScriptConfig;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class ExitCommand implements Command {

	@Override
	public boolean matches(String command) {
		return "EXIT".equals(command);
	}
	
	@Override
	public Script getScript(String command, final Direktor direktor) {
		return SCRIPT;
	}
	
	private static final Script SCRIPT = new Script() {
		
		@Override
		public Action play(Puppetteer t) {
			return Actions.sequence(
				t.freezeGame(),
				t.printBlink(ScriptConfig.SHORT, Alignment.LEFT, "[RED]EXITING SYSTEM: LEAVING YOUR POST WILL BE REPORTED[]"),
				t.delay(4f),
				t.screenFuzzOut(0.25f, Color.BLACK),
				t.adjustFx(0.25f, Interpolation.pow5In, .2f, .04f, .08f, .04f),
				t.delay(0.25f),
				t.endTextFadeIn(2f, "WHITE"));
		}
	}; 
}
