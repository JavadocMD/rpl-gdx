package com.javadocmd.rpl.terminal;

import static com.javadocmd.rpl.script.ScriptConfig.GREEN;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.javadocmd.rpl.util.IncorporealActor;

public class InputLine extends IncorporealActor {

	private static final String TRAILER = "_";
	private final Label label;
	private final String leader;
	private String value = "";

	private static final float PERIOD = 2f;
	private static final float DUTY = 1f;
	private float lifetime = DUTY;

	public InputLine(Label label, String leader) {
		this.label = label;
		this.leader = GREEN + leader + "> ";
		label.setText(this.leader + TRAILER);
	}

	@Override
	public void act(float delta) {
		lifetime += delta;
		if (lifetime > PERIOD)
			lifetime -= PERIOD;
		if (lifetime > DUTY) {
			label.setText(leader + value + TRAILER);
		} else {
			label.setText(leader + value);
		}
	}

	public boolean type(String letter) {
		if (value.length() == 60)
			return false;
		value += letter;
		return true;
	}

	public boolean backspace() {
		if (value.length() == 0)
			return false;
		value = value.substring(0, value.length() - 1);
		return true;
	}

	public boolean freeze() {
		label.setText(leader + value);
		lifetime = Float.NEGATIVE_INFINITY;
		return true;
	}

	public String getValue() {
		return value;
	}
	
	public Label getLabel() {
		return label;
	}
}
