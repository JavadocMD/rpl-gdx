package com.javadocmd.rpl.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.javadocmd.rpl.command.Command;
import com.javadocmd.rpl.mode.Mode;
import com.javadocmd.rpl.mode.RootMenuMode;
import com.javadocmd.rpl.script.NotificationScript;
import com.javadocmd.rpl.script.Script;
import com.javadocmd.rpl.script.Startup;
import com.javadocmd.rpl.script.UnknownScript;
import com.javadocmd.rpl.sim.PlantSimulation;

public class Direktor implements EventListener {

	private Stage stage;
	private Terminal terminal;
	private Puppetteer puppet;
	private PlantSimulation sim;

	private boolean initialPause = true;
	private Mode mode;

	// Shader params.
	private float u_staticStrength = 0.2f;
	private float u_flickerStrength = 0.08f;

	public Direktor(float w, float h, Stage stage) {
		this.stage = stage;
		this.terminal = new Terminal(w, h);
		this.puppet = new Puppetteer(this, terminal);
		this.sim = new PlantSimulation();

		Gdx.input.setInputProcessor(new TypingListener(terminal));
		stage.addActor(terminal.getActor());
		stage.addActor(sim);
		stage.addListener(this);
	}

	public void begin() {
		play(new Startup());
		enter(new RootMenuMode());
		
		/* The simulation is initially paused. It is kicked off
		 * when the user first visits the STATUS mode, or after 60 seconds.
		 */
		final Direktor direktor = this;
		stage.addAction(Actions.sequence(
			Actions.delay(60f),
			Actions.run(new Runnable(){
				@Override
				public void run() {
					direktor.beginSimluation();
				}
			})
		));
	}
	
	public void beginSimluation() {
		if (initialPause) {
			initialPause = false;
			sim.setPause(false);
		}
	}

	public void processCommand(CommandEvent e) {
		// Gdx.app.log("Terminal", "command: " + command);
		String commandString = e.getCommand().trim();

		Command command = null;
		for (Command c : mode.getAvailableCommands()) {
			if (c.matches(commandString)) {
				command = c;
				break;
			}
		}

		if (command != null) {
			play(command.getScript(commandString, this));
		} else {
			play(UnknownScript.INSTANCE);
		}
	}

	public void processMessage(NotificationEvent e) {
		play(new NotificationScript(e));
	}

	public void enter(Mode mode) {
		this.mode = mode;
		play(mode.entryScript(this));
	}

	private void play(Script script) {
		terminal.addAction(Actions.after(script.play(puppet)));
	}

	public void addActor(Actor actor) {
		stage.addActor(actor);
	}

	@Override
	public boolean handle(Event event) {
		if (ClassReflection.isInstance(CommandEvent.class, event)) {
			processCommand((CommandEvent) event);
			return true;
		} else if (ClassReflection.isInstance(NotificationEvent.class, event)) {
			processMessage((NotificationEvent) event);
			return true;
		}
		return false;
	}

	public PlantSimulation getSim() {
		return sim;
	}

	public Mode getMode() {
		return mode;
	}

	public void freezeGame() {
		terminal.cancelInput();
		sim.setPause(true);
	}

	public float getStaticStrength() {
		return u_staticStrength;
	}

	public void setStaticStrength(float u_staticStrength) {
		this.u_staticStrength = u_staticStrength;
	}

	public float getFlickerStrength() {
		return u_flickerStrength;
	}

	public void setFlickerStrength(float u_flickerStrength) {
		this.u_flickerStrength = u_flickerStrength;
	}
}
