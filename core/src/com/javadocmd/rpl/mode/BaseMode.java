package com.javadocmd.rpl.mode;

import java.util.ArrayList;
import java.util.List;

import com.javadocmd.rpl.command.ClockCommand;
import com.javadocmd.rpl.command.Command;
import com.javadocmd.rpl.command.ExitCommand;
import com.javadocmd.rpl.command.HelpCommand;
import com.javadocmd.rpl.command.ReturnCommand;
import com.javadocmd.rpl.command.SnowmanCommand;

abstract public class BaseMode implements Mode {

	@Override
	public List<Command> getAvailableCommands() {
		List<Command> commands = new ArrayList<Command>();
		commands.add(new HelpCommand());
		commands.add(new ExitCommand());
		commands.add(new ReturnCommand());
		commands.add(new SnowmanCommand());
		commands.add(new ClockCommand());
		return commands;
	}
}
