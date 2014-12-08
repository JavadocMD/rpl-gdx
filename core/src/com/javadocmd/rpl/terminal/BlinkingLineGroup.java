package com.javadocmd.rpl.terminal;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class BlinkingLineGroup extends LineGroup {

	public static Faktory FAkTORY = new Faktory() {
		@Override
		public LineGroup create(Label[] labels) {
			return new BlinkingLineGroup(labels);
		}
	};

	private static final float PERIOD = 1f;
	private static final float DUTY = 0.1f;
	private float lifetime = DUTY;
	private boolean on = true;

	public BlinkingLineGroup(Label[] labels) {
		super(labels);
	}

	@Override
	protected void updateLifecycle(float delta) {
		lifetime += delta;
		if (lifetime > PERIOD)
			lifetime -= PERIOD;
		on = (lifetime > DUTY);
	}

	@Override
	protected void update(Label label) {
		if (on) {
			label.setVisible(true);
		} else {
			label.setVisible(false);
		}
	}
}
