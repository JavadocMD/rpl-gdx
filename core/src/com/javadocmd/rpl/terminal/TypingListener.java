package com.javadocmd.rpl.terminal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class TypingListener extends InputAdapter {

	private Terminal terminal;

	public TypingListener(Terminal terminal) {
		this.terminal = terminal;
	}

	private static final Pattern INPUT = Pattern
			.compile("^(Numpad\\s)?([A-Za-z0-9]{1})$");

	@Override
	public boolean keyDown(int keycode) {
		String s = Input.Keys.toString(keycode);

		Matcher matcher = INPUT.matcher(s);
		if (matcher.matches()) {
			int iSpace = s.indexOf(" ");
			String character = (iSpace > -1) ? s.substring(iSpace + 1, s.length()): s;
			terminal.type(character);
			return true;

		} else {
			switch (keycode) {
				case Input.Keys.BACKSPACE:
					terminal.backspace();
					return true;
				case Input.Keys.SPACE:
					terminal.type(" ");
					return true;
				case Input.Keys.ENTER:
					terminal.enter();
					return true;
				case Input.Keys.ESCAPE:
					Gdx.app.exit();
					return true;
				default:
					return false;
			}
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.A:
			case Input.Keys.B:
			case Input.Keys.C:
			case Input.Keys.D:
			case Input.Keys.E:
			case Input.Keys.F:
			case Input.Keys.G:
			case Input.Keys.H:
			case Input.Keys.I:
			case Input.Keys.J:
			case Input.Keys.K:
			case Input.Keys.L:
			case Input.Keys.M:
			case Input.Keys.N:
			case Input.Keys.O:
			case Input.Keys.P:
			case Input.Keys.Q:
			case Input.Keys.R:
			case Input.Keys.S:
			case Input.Keys.T:
			case Input.Keys.U:
			case Input.Keys.V:
			case Input.Keys.W:
			case Input.Keys.X:
			case Input.Keys.Y:
			case Input.Keys.Z:
			case Input.Keys.NUM_0:
			case Input.Keys.NUM_1:
			case Input.Keys.NUM_2:
			case Input.Keys.NUM_3:
			case Input.Keys.NUM_4:
			case Input.Keys.NUM_5:
			case Input.Keys.NUM_6:
			case Input.Keys.NUM_7:
			case Input.Keys.NUM_8:
			case Input.Keys.NUM_9:
			case Input.Keys.NUMPAD_0:
			case Input.Keys.NUMPAD_1:
			case Input.Keys.NUMPAD_2:
			case Input.Keys.NUMPAD_3:
			case Input.Keys.NUMPAD_4:
			case Input.Keys.NUMPAD_5:
			case Input.Keys.NUMPAD_6:
			case Input.Keys.NUMPAD_7:
			case Input.Keys.NUMPAD_8:
			case Input.Keys.NUMPAD_9:
			case Input.Keys.SPACE:
			case Input.Keys.BACKSPACE:
			case Input.Keys.ENTER:
			case Input.Keys.ESCAPE:
				return true;
			default:
				return false;
		}
	}

}
