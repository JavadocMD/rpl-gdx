package com.javadocmd.rpl.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.javadocmd.rpl.Resources;
import com.javadocmd.rpl.mode.Mode;
import com.javadocmd.rpl.terminal.Terminal.Alignment;
import com.javadocmd.rpl.util.SolidBackgroundActor;

public class Puppetteer {

	private final Direktor d;
	private final Terminal t;
	private final TerminalFaktory f;

	public Puppetteer(Direktor d, Terminal t) {
		this.d = d;
		this.t = t;
		this.f = new TerminalFaktory(t);
	}

	public Action delay(final float duration) {
		return Actions.delay(duration);
	}

	public Action println(final String message) {
		return println(message, Alignment.LEFT);
	}
	
	public Action println(final String message, final Alignment alignment) {
		return println(f.createLine(message, alignment));
	}
	
	public Action println(final Label label) {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				t.print(label);
			}
		});
	}
	
	public Action printUnder(final String message, final Alignment alignment) {
		final Label label = f.createLine(message, alignment);
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				t.print(label);
				label.setY(label.getY() + TerminalFaktory.LINE_HEIGHT);
				InputLine input = t.getInput();
				if (input != null) {
					Label inputLabel = input.getLabel();
					inputLabel.setY(inputLabel.getY() - label.getHeight());
				}
			}
		});
	}

	public Action printBlink(final float delay, final Alignment alignment, final String message) {
		Label[] labels = f.createLines(message, alignment);
		BlinkingLineGroup blink = new BlinkingLineGroup(labels);
		
		t.addActor(blink);
		return crawl(delay, labels);
	}
	
	public Action crawl(final float delay, final Alignment alignment,
			final String lines) {
		return crawl(delay, f.createLines(lines, alignment));
	}
	
	public Action crawl(final float delay, final Label[] labels) {
		Action[] seq = new Action[labels.length * 2];

		for (int i = 0; i < labels.length; i++) {
			seq[i * 2] = println(labels[i]);
			seq[i * 2 + 1] = delay(delay);
		}

		return Actions.sequence(seq);
	}

	public Action crawlEmpty(final float delay, final int lines) {
		Action[] seq = new Action[lines * 2];

		for (int i = 0; i < lines; i++) {
			seq[i * 2] = emptyln(1);
			seq[i * 2 + 1] = delay(delay);
		}

		return Actions.sequence(seq);
	}

	public Action emptyln(final int lines) {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				if (lines > 0)
					t.scroll(TerminalFaktory.LINE_HEIGHT * lines);
			}
		});
	}

	public Action ready() {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				InputLine inputLine = f.createInputLine(d.getMode().getLeader());
				t.print(inputLine);
			}
		});
	}

	public Action enterMode(final Mode mode) {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				d.enter(mode);
			}
		});
	}

	public Action freezeGame() {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				d.freezeGame();
				t.cancelInput();
			}
		});
	}
	
	public Action adjustFx(final float duration, final Interpolation interpolation, 
			final float staticFrom, final float staticTo, final float flickerFrom, final float flickerTo) {
		
		return Actions.parallel(
			new TemporalAction(duration, interpolation) {
				@Override
				protected void update(float percent) {
					float value = staticFrom + percent * (staticTo - staticFrom);
					d.setStaticStrength(value);
				}
			},
			new TemporalAction(duration, interpolation) {
				@Override
				protected void update(float percent) {
					float value = flickerFrom + percent * (flickerTo - flickerFrom);
					d.setFlickerStrength(value);
				}
			}
		);
	}
	
	public Action cancelInput() {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				t.cancelInput();
			}
		});
	}
	
	public Action screenFuzzOut(final float duration, final Color color) {
		final Actor fuzz = new SolidBackgroundActor(f.w, f.h, Resources.INSTANCE.pixel, color);
		
		Color c = fuzz.getColor();
		c.a = 0f;
		fuzz.setColor(c);
		
		d.addActor(fuzz);
		
		return Actions.addAction(Actions.fadeIn(duration, Interpolation.pow5In), fuzz);
	}
	
	public Action endTextFadeIn(final float duration, final String color) {
		long tics = d.getSim().getTics();
		
		String message = "[" + color + "]You survived " + tics + " minutes.[]"; 
		
		final Label label = f.createLine(message, Alignment.CENTER);
		
		Color c = label.getColor();
		c.a = 0f;
		label.setColor(c);
		
		d.addActor(label);
		
		return Actions.addAction(Actions.fadeIn(duration, Interpolation.pow5In), label);
	}

	public Action exit() {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				Gdx.app.exit();
			}
		});
	}
}
