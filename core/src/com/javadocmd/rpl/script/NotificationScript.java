package com.javadocmd.rpl.script;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.terminal.NotificationEvent;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class NotificationScript implements Script {

	private final NotificationEvent e;

	public NotificationScript(NotificationEvent e) {
		this.e = e;
	}

	@Override
	public Action play(Puppetteer t) {
		if (!e.isEndGame()) {
			return t.printUnder(e.getMessage(), Alignment.LEFT);
		}
			
		final float fuzzDuration;
		final Color fuzzColor;
		final String textColor;
		switch (e.getEndGame()) {
			case MELTDOWN:
				fuzzDuration = 2f;
				fuzzColor = ScriptConfig.MELTDOWN;
				textColor = "BLACK";
				break;
			default:
			case NONE:
			case TERMINATION:
				fuzzDuration = 1f;
				fuzzColor = ScriptConfig.TERMINATED;
				textColor = "WHITE";
				break;
		}
		
		return Actions.sequence(
			t.freezeGame(),
			t.printBlink(ScriptConfig.SHORT, Alignment.LEFT, e.getMessage()),
			t.delay(6f),
			t.screenFuzzOut(fuzzDuration, fuzzColor),
			t.adjustFx(fuzzDuration, Interpolation.pow5In, .2f, .04f, .08f, .04f),
			t.delay(3f),
			t.endTextFadeIn(2f, textColor));
	}
}
