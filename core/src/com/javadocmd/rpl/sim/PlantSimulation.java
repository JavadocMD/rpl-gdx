package com.javadocmd.rpl.sim;

import static java.lang.Math.PI;

import com.javadocmd.rpl.terminal.NotificationEvent;
import com.javadocmd.rpl.util.IncorporealActor;

public class PlantSimulation extends IncorporealActor {

	private static final float TIC_PERIOD = 3f;

	private long tics = 0L;
	private float timer = 0f;
	private boolean paused;

	private float capacity;
	private float heat;
	private float coolantUsed;
	private float pressure;

	private DemandCurve demandCurve;
	
	private float demand;
	private int demandFailures = 0;
	private boolean demandFailure = false;
	private int demandFailureTimeout = 0;

	private float radioaktivity;
	private int radioFailures = 0;
	private boolean radioFailure = false;
	private int radioFailureTimeout = 0;

	public PlantSimulation() {
		paused = true;

		capacity = 0.20f;
		heat = 0.1f;
		coolantUsed = 0.05f;
		pressure = 0.0f;

		demandCurve = new DemandCurve(.5f, .15f, -37, .10f, 19, .05f, 3);
		demand = demandCurve.apply(0);

		// -- DEBUG
		// heat = 2f;
		// pressure = 2f;
		// radioaktivity = 1f;
	}

	public void setPause(boolean paused) {
		this.paused = paused;
	}

	public float vent(float amount) {
		float vented = Math.min(pressure, amount);
		pressure -= vented;
		radioaktivity += vented / 10f;
		return vented;
	}

	public void setCapacity(float amount) {
		capacity = amount;
	}

	public void setCoolant(float amount) {
		coolantUsed = amount;
	}

	public void setDemand(float amount) {
		demand = amount;
	}

	@Override
	public void act(float delta) {
		if (paused)
			return;

		timer += delta;
		if (timer >= TIC_PERIOD) {
			timer %= TIC_PERIOD;
			tic();
		}
	}

	private void tic() {
		float heatDelta = (float) (0.05 - 0.05 * Math.sin(PI * capacity + PI
				/ 2));
		float coolantDelta = (float) (-0.01 * (coolantUsed + 1) * Math.log(40 * coolantUsed + 1));
		float pressureDelta = (0.04f * coolantUsed) - 0.008f;

		//Gdx.app.log("Sim", String.format("H: %.3f; C: %.3f; P: %.3f", heatDelta, coolantDelta, pressureDelta));

		heat += heatDelta;
		heat += coolantDelta;
		pressure += pressureDelta;
		radioaktivity -= 0.00025f;

		if (pressure < 0f)
			pressure = 0f;
		if (heat < 0f)
			heat = 0f;
		if (radioaktivity < 0f)
			radioaktivity = 0f;

		// Watch for meltdown.
		if (heat > 1.0f) {
			meltdown();
			return;
		}

		// Watch for gas explosion.
		if (pressure > 1.0f) {
			explode();
			return;
		}

		// Track radioaktivity failures. 
		if (radioFailure) {
			radioFailureTimeout -= 1;
			if (radioaktivity <= 0.1f || radioFailureTimeout == 0) {
				radioFailure = false;
				radioFailureTimeout = 0;
			}
		} else if (radioaktivity > 0.1f) {
			radioFailure = true;
			radioFailureTimeout = 10;
			radioFailures += 1;
			if (radioFailures == 3) {
				radioFailure();
				return;
			} else {
				radioWarning();
			}
		}

		// Track demand failures.
		if (demandFailure) {
			demandFailureTimeout -= 1;
			if (capacity >= demand || demandFailureTimeout == 0) {
				demandFailure = false;
				demandFailureTimeout = 0;
			}
		} else if (capacity < demand) {
			demandFailure = true;
			demandFailureTimeout = 10;
			demandFailures += 1;
			if (demandFailures == 3) {
				demandFailure();
				return;
			} else {
				demandWarning();
			}
		}
		
		tics += 1;
		
		if (tics % 5 == 0) {
			demand = demandCurve.apply(tics);
			//Gdx.app.log("Sim", String.format("D: %.3f, tics: %d", demand, tics));
		}
	}

	private void explode() {
		fire(new NotificationEvent(
				NotificationEvent.EndGameType.MELTDOWN,
				  "[RED]****DANGER***DANGER***DANGER***DANGER***DANGER***DANGER****\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***      GAS CONTAINMENT EXPLOSION IS IMMINENT          ***\n"
				+ "[RED]*** EVACUATION ORDER IS NOT GIVEN, REMAIN AT YOUR POST  ***\n"
				+ "[RED]***      GAS CONTAINMENT EXPLOSION IS IMMINENT          ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***********************************************************"));
	}

	private void meltdown() {
		fire(new NotificationEvent(
				NotificationEvent.EndGameType.MELTDOWN,
				  "[RED]****DANGER***DANGER***DANGER***DANGER***DANGER***DANGER****\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***             PLANT MELTDOWN IS IMMINENT              ***\n"
				+ "[RED]*** EVACUATION ORDER IS NOT GIVEN, REMAIN AT YOUR POST  ***\n"
				+ "[RED]***             PLANT MELTDOWN IS IMMINENT              ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***********************************************************"));
	}

	private void demandWarning() {
		fire(new NotificationEvent(
				NotificationEvent.EndGameType.NONE,
				  "[RED]***********************************************************\n"
				+ "[RED]*** COMRADE Operator #4788-77N                          ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]*** POWER GENERATION HAS FALLEN BEHIND DEMAND. THIS     ***\n"
				+ "[RED]*** INCIDENT HAS BEEN LOGGED. DO NOT FAIL OUR LEADER.   ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***********************************************************"));
	}

	private void demandFailure() {
		fire(new NotificationEvent(
				NotificationEvent.EndGameType.TERMINATION,
				  "[RED]***********************************************************\n"
				+ "[RED]*** COMRADE Operator #4788-77N                          ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]*** YOU HAVE FAILED TO KEEP UP WITH OUR LEADER'S DEMAND ***\n"
				+ "[RED]*** FOR POWER. YOU WERE WARNED. REMAIN AT YOUR SCREEN.  ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***                TERMINATION IMMINENT                 ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***********************************************************"));
	}

	private void radioWarning() {
		fire(new NotificationEvent(
				NotificationEvent.EndGameType.NONE,
				  "[RED]***********************************************************\n"
				+ "[RED]*** COMRADE Operator #4788-77N                          ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]*** POTENTIALLY DANGEROUS LEVELS OF RADIOAkTIVITY HAVE  ***\n"
				+ "[RED]*** BEEN DETEkTED IN THE DISTRIkT AND PEOPLE ARE        ***\n"
				+ "[RED]*** REPORTING ILLNESSES AT HIGHER RATES. THIS INCIDENT  ***\n"
				+ "[RED]*** HAS BEEN LOGGED. DO NOT FAIL OUR LEADER.            ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***********************************************************"));
	}

	private void radioFailure() {
		fire(new NotificationEvent(
				NotificationEvent.EndGameType.TERMINATION,
				  "[RED]***********************************************************\n"
				+ "[RED]*** COMRADE Operator #4788-77N                          ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]*** YOU HAVE FAILED TO PROTEkT THE HEALTH OF YOUR       ***\n"
				+ "[RED]*** COMRADES IN THE DISTRIkT. YOU WERE WARNED. REMAIN   ***\n"
				+ "[RED]*** AT YOUR SCREEN.                                     ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***                TERMINATION IMMINENT                 ***\n"
				+ "[RED]***                                                     ***\n"
				+ "[RED]***********************************************************"));
	}

	public long getTics() {
		return tics;
	}
	
	public float getCapacity() {
		return capacity;
	}

	public float getHeat() {
		return heat;
	}

	public float getCoolantUsed() {
		return coolantUsed;
	}

	public float getPressure() {
		return pressure;
	}

	public float getDemand() {
		return demand;
	}

	public float getRadioaktivity() {
		return radioaktivity;
	}

	public String getSummary() {
		if (heat > 0.9f || pressure > 0.9f) {
			return "[RED]DANGER[]";
		} else if (heat > 0.6f || pressure > 0.6f || radioaktivity > 0.08f) {
			return "[RED]WARNING[]";
		} else {
			return "NORMAL";
		}
	}
}
