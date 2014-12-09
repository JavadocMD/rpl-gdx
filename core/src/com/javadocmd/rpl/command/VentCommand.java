package com.javadocmd.rpl.command;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.sim.PlantSimulation;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class VentCommand implements Command {

	private static Pattern COMMAND_PATTERN = Pattern.compile("VENT ([0-9]{1,3})");
	
	@Override
	public boolean matches(String command) {
		return command.startsWith("VENT");
	}

	@Override
	public Script getScript(String command, final Direktor direktor) {
		Matcher m = COMMAND_PATTERN.matcher(command);
		if (m.matches()) {
			int iSpace = command.indexOf(" ");
			String valueString = (iSpace > -1) ? command.substring(iSpace + 1, command.length()): command;
			Integer value = Integer.parseInt(valueString);
			if (value >= 0 && value <= 100) {
				return vent(value, direktor);
			}
		}
		
		return HELP;
	}

	private Script vent(final Integer value, final Direktor direktor) {
		PlantSimulation sim = direktor.getSim();
		final int vented = (int) (sim.vent(value / 100f) * 100f);
		final int after = (int) (sim.getPressure() * 100f);
		
		return new Script() {
			
			@Override
			public Action play(Puppetteer t) {
				return Actions.sequence(t.crawl(SHORT, Alignment.LEFT,
					vented + "% OF MAXIMUM PRESSURE TOLERANCE VENTED.\n"+
					"CONTAINMENT PRESSURE NOW " + after + "%"),
					t.ready());
			}
		};
	}
	
	private static Script HELP = new Script() {
		
		@Override
		public Action play(Puppetteer t) {
			return Actions.sequence(t.crawl(SHORT, Alignment.LEFT,
				"[BLUE]VENT value[] -- VENTS CONTAINED RADIOAkTIVE GASES INTO THE ATMOSPHERE\n" +
				"  CONTAINMENT PRESSURE WILL BE REDUCED BY value\n" +
				"  value MUST BE BETWEEN 0 AND 100\n" +
				"  example usage: [BLUE]VENT 57[]"),
				t.ready());
		}
	};
}
