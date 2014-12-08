package com.javadocmd.rpl.command;

import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;

public interface Command {

	public boolean matches(String command);
	
	public Script getScript(String command, final Direktor direktor);
}
