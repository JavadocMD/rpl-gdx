package com.javadocmd.rpl.terminal;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Terminal {

	public static enum Alignment {
		LEFT, CENTER;
	}

	private final Group group;
	private final float h;

	private InputLine input = null;

	public Terminal(float w, float h) {
		this.h = h;
		group = new Group();
		group.setSize(w, h);
		group.setPosition(0, 0);
	}

	// -- Actor Management --//
	public Actor getActor() {
		return group;
	}

	public void clear() {
		group.clearChildren();
	}

	public void addAction(Action action) {
		group.addAction(action);
	}

	public void addActor(Actor actor) {
		group.addActor(actor);
	}

	// -- Typing Handling --//
	public void type(String letter) {
		if (input != null)
			input.type(letter);
	}

	public void backspace() {
		if (input != null)
			input.backspace();
	}

	public void enter() {
		if (input == null || input.getValue().isEmpty())
			return;

		String command = input.getValue();
		cancelInput();

		group.fire(new CommandEvent(command));
	}

	public void cancelInput() {
		if (input != null) {
			input.freeze();
			input.remove();
			input = null;
		}
	}

	public InputLine getInput() {
		return input;
	}

	// -- Output Manipulation --//
	public void print(Label label) {
		scroll(label.getHeight());
		group.addActor(label);
	}

	public void print(InputLine inputLine) {
		input = inputLine;
		Label label = inputLine.getLabel();
		scroll(label.getHeight());
		group.addActor(label);
		group.addActor(inputLine);
	}

	public void scroll(float distance) {
		List<Actor> toRemove = new LinkedList<Actor>();
		for (Iterator<Actor> i = group.getChildren().iterator(); i.hasNext();) {
			Actor a = i.next();
			float newY = a.getY() + distance;
			if (newY < h) {
				a.setY(newY);
			} else {
				// Gdx.app.log("Terminal", "removed line");
				toRemove.add(a);
			}
		}

		for (Actor a : toRemove) {
			a.remove();
		}
	}
}
