package com.javadocmd.rpl.mode;

import static com.javadocmd.rpl.script.ScriptConfig.SHORT;
import static com.javadocmd.rpl.util.StringFormat.*;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.javadocmd.rpl.command.CapacityCommand;
import com.javadocmd.rpl.command.Command;
import com.javadocmd.rpl.command.CoolantCommand;
import com.javadocmd.rpl.command.StatusCommand;
import com.javadocmd.rpl.command.VentCommand;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.sim.PlantSimulation;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.terminal.Puppetteer;
import com.javadocmd.rpl.terminal.Terminal.Alignment;

public class StatusMode extends BaseMode {

	@Override
	public String getLeader() {
		return "STAT";
	}
	
	@Override
	public List<Command> getAvailableCommands() {
		List<Command> commands = super.getAvailableCommands();
		commands.add(new StatusCommand());
		commands.add(new CapacityCommand());
		commands.add(new CoolantCommand());
		commands.add(new VentCommand());
		return commands;
	}
	
	@Override
	public Script entryScript(final Direktor direktor) {
		return new Script() {
			
			@Override
			public Action play(Puppetteer t) {
				String text = 
					"~~~ NUCLEAR FUSION REAkTIVE POWER PLANT MARK VII OPERATIONAL [BLUE]STATUS[] REPORT\n" +
					"  |         Summary:    SSS  \n" +
					"  |    Power Demand:   DDD%    \n" +
					"  |  Power [BLUE]CAPACITY[]:   CCC%    \n" +
					"  |       Core Heat:   HHH%    \n" +
					"  | Applied [BLUE]COOLANT[]:   OOO%    \n" +
					"  |    Gas Pressure:   PPP%    [BLUE]VENT[]\n" +
					"  | Distrikt \n" +
					"  |   Radioaktivity:  0.RRR GiR\n" +
					"  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
				
				final PlantSimulation sim = direktor.getSim();
				text = text.replaceAll("SSS", sim.getSummary());
				text = text.replaceAll("DDD", percent(sim.getDemand()));
				text = text.replaceAll("CCC", percent(sim.getCapacity()));
				text = text.replaceAll("HHH", percent(sim.getHeat()));
				text = text.replaceAll("OOO", percent(sim.getCoolantUsed()));
				text = text.replaceAll("PPP", percent(sim.getPressure()));
				text = text.replaceAll("RRR", decimal(sim.getRadioaktivity()));
				
				return Actions.sequence(
					Actions.run(new Runnable() {
						@Override
						public void run() {
							direktor.beginSimluation();
						}
					}),
					t.crawlEmpty(SHORT, 1),
					t.crawl(SHORT, Alignment.LEFT, text),
					t.ready());
			}
		};
	}
}
