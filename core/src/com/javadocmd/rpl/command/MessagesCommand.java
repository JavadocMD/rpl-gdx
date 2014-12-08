package com.javadocmd.rpl.command;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class MessagesCommand implements Command {

	@Override
	public boolean matches(String command) {
		return "MESSAGES".equals(command) || "MESSAGE".equals(command);
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
					"*** MESSAGE 1 (OLD)\n" +
					"*** From: COMRADE Coordinator #2118-55C\n" +
					"*** To: COMRADE Operator #4788-77N\n" +
					"*** Subjekt: ASSIGNMENT WELCOME AND DIREkTIVES\n\n" +
					"Welcome COMRADE to your new posting at this Republik Power & Light Nuclear\n" +
					"Fusion Reaktive Power Plant MARK VII. I should not need to stress the\n" +
					"importance of keeping this plant operating at full capacity and within\n" +
					"acceptable safety limits. Failure will compromise OUR LEADER'S New Economic\n" +
					"Direktive and this will not be tolerated.\n\n" +
					"Keep a vigilant eye upon the Plant operating [BLUE]STATUS[] and perform as you have\n" +
					"been trained to correkt for emerging conditions. Current guidelines published\n" +
					"by Republik scientists declare airborne RADIOAKTIVITY safe below\n" +
					"[RED]0.100 GilliRads (GiR)[]. So do not hesitate to vent gas into the surrounding\n" +
					"Distrikt (POPULATION 4.218M) to prevent meltdown.\n\n" +
					"Perform well and your service shall be honored by OUR LEADER himself.\n\n" +
					"*** END MESSAGE 1"
				),
				t.crawlEmpty(SHORT, 1),
				t.ready());
		}
	};
}
