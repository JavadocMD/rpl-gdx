package com.javadocmd.rpl.terminal;

import static com.javadocmd.rpl.script.ScriptConfig.*;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.javadocmd.rpl.Resources;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class TerminalFaktory {

	public static final int MARGIN = 10;
	public static final int LINE_HEIGHT = 25;
	
	public final float w;
	public final float h;
	
	public TerminalFaktory(Terminal terminal) {
		this.w = terminal.getActor().getWidth();
		this.h = terminal.getActor().getHeight();
	}
	
	public InputLine createInputLine(String leader) {
		Label label = createLine("?", Alignment.LEFT);
		InputLine inputLine = new InputLine(label, leader);
		return inputLine;
	}
	
	public Label[] createLines(String message, Alignment alignment) {
		String[] linesArray = message.split("\n");
		Label[] labels = new Label[linesArray.length];
		
		for (int i = 0; i < labels.length; i++) {
			labels[i] = createLine(linesArray[i], alignment);
		}
		
		return labels;
	}
	
	public Label createLine(String message, Alignment alignment) {
		if (!message.isEmpty()) {
			message = message.replaceAll("\\[BLUE\\]", BLUE);
			message = message.replaceAll("\\[GREEN\\]", GREEN);
			message = message.replaceAll("\\[RED\\]", RED);
		}
		
		Label label = new Label(GREEN + message, Resources.INSTANCE.labelStyle);

		switch (alignment) {
			case CENTER:
				label.setAlignment(Align.bottom, Align.center);
				break;
			case LEFT:
				label.setAlignment(Align.bottomLeft, Align.left);
				break;
		}

		label.setWrap(true);

		label.setWidth(w - MARGIN * 2);
		if (!message.isEmpty()) {
			/*
			 * This little dance appears to be the only way to get the height
			 * and width set appropriately. Without pack(), the label doesn't
			 * know its height. After pack(), width is set to 0 and the wrap is
			 * no longer in effect.
			 */
			label.pack();
			float h = label.getHeight();
			label.setSize(w - MARGIN * 2, h);
		} else {
			label.setSize(w - MARGIN * 2, LINE_HEIGHT);
		}
		label.setPosition(MARGIN, MARGIN);
		return label;
	}
}
