package com.javadocmd.rpl.mode;

import java.util.List;

import com.javadocmd.rpl.command.Command;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;

public interface Mode {

	public String getLeader();
	
	public List<Command> getAvailableCommands();

	public Script entryScript(final Direktor direktor);
}
