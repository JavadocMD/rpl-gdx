package com.javadocmd.rpl.terminal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.javadocmd.rpl.util.IncorporealActor;

abstract public class LineGroup extends IncorporealActor {

	public static interface Faktory {
		public LineGroup create(Label[] labels);
	}

	private List<Label> labels;
	private Set<Label> neverStaged;

	public LineGroup(Label[] labels) {
		this.labels = new ArrayList<Label>();
		this.neverStaged = new HashSet<Label>();
		for (Label l : labels) {
			this.labels.add(l);
			this.neverStaged.add(l);
		}
	}

	@Override
	public void act(float delta) {
		updateLifecycle(delta);
		for (ListIterator<Label> i = labels.listIterator(); i.hasNext();) {
			Label l = i.next();
			if (l.getStage() != null) {
				neverStaged.remove(l);
				update(l);
			} else if (!neverStaged.contains(l)) {
				i.remove();
				// Gdx.app.log("LineGroup", "Removed line.");
			}
		}

		if (labels.size() == 0) {
			remove();
			// Gdx.app.log("LineGroup", "Removed group.");
		}
	}

	abstract protected void updateLifecycle(float delta);

	abstract protected void update(Label label);
}
