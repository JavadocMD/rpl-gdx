package com.javadocmd.rpl.script;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.terminal.Puppetteer;

public class UnknownScript implements Script {

	public static final UnknownScript INSTANCE = new UnknownScript();
	
	@Override
	public Action play(Puppetteer t) {
		return Actions.sequence(
			t.println("UNKNOWN DIREkTIVE - YOU MAY ASK FOR [BLUE]HELP[] OR [BLUE]RETURN[]"),
			t.ready());
	}
}
