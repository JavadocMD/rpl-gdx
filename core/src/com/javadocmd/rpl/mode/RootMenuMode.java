package com.javadocmd.rpl.mode;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.command.Command;
import com.javadocmd.rpl.command.MessagesCommand;
import com.javadocmd.rpl.command.StatusCommand;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class RootMenuMode extends BaseMode {

	@Override
	public String getLeader() {
		return "ROOT";
	}
	
	@Override
	public List<Command> getAvailableCommands() {
		List<Command> commands = super.getAvailableCommands();
		commands.add(new MessagesCommand());
		commands.add(new StatusCommand());
		return commands;
	}
	
	@Override
	public Script entryScript(final Direktor direktor) {
		return new Script() {
			
			@Override
			public Action play(Puppetteer t) {
				return Actions.sequence( 
					t.crawl(SHORT, Alignment.LEFT, 
						"GREETINGS COMRADE Operator #4788-77N\n" +
						"YOU HAVE 1 [BLUE]MESSAGES[] - PLANT [BLUE]STATUS[]: " + direktor.getSim().getSummary()),
					t.ready());
			}
		};
	}
}
