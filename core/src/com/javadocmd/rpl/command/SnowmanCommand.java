package com.javadocmd.rpl.command;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class SnowmanCommand implements Command {

	@Override
	public boolean matches(String command) {
		return "SNOWMAN".equals(command) || "SNOWWOMAN".equals(command) || "SNOWPERSON".equals(command);
	}

	@Override
	public Script getScript(String command, final Direktor direktor) {
		return SCRIPT;
	}

	private static final Script SCRIPT = new Script() {
		@Override
		public Action play(Puppetteer t) {
			return Actions.sequence(
				t.crawlEmpty(SHORT, 1),
				t.crawl(SHORT, Alignment.LEFT, 
					"  .         .        .         .     .     .           .    \n" +
					"     .          .          .  .        .        .           \n" +
					"       .      . IN NUCLEAR WINTER   .       .     .     . . \n" +
					"  .              .    .   A SNOWMAN IS YOU     .            \n" +
					"      .      .           .             .              .     \n" +
					"                  .          .              .               "
				),
				t.crawlEmpty(SHORT, 1),
				t.ready());
		}
	};
}
