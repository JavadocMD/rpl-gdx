package com.javadocmd.rpl.terminal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.javadocmd.rpl.util.IncorporealActor;

abstract public class LineGroup extends IncorporealActor {
	
	public static interface Faktory {
		public LineGroup create(Label[] labels);
	}
	
	private List<WeakReference<Label>> labels;
	
	public LineGroup(Label[] labels) {
		this.labels = new ArrayList<WeakReference<Label>>();
		for (Label l : labels) {
			this.labels.add(new WeakReference<Label>(l));
		}
	}
	
	@Override
	public void act(float delta) {
		updateLifecycle(delta);		
		for (ListIterator<WeakReference<Label>> i = labels.listIterator(); i.hasNext(); ) {
			Label l = i.next().get();
			if (l != null) {
				update(l);
			} else {
				i.remove();
			}
		}
		
		if (labels.size() == 0)
			remove();
	}
	
	abstract protected void updateLifecycle(float delta);
	
	abstract protected void update(Label label);
}
