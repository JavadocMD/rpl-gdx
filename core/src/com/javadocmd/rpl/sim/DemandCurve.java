package com.javadocmd.rpl.sim;

import static java.lang.Math.*;

public class DemandCurve {

	private float offset, a1, p1, a2, p2, a3, p3;

	public DemandCurve(float offset, float a1, float p1, float a2, float p2,
			float a3, float p3) {
		this.offset = offset;
		this.a1 = a1;
		this.p1 = p1;
		this.a2 = a2;
		this.p2 = p2;
		this.a3 = a3;
		this.p3 = p3;
	}

	public float apply(float x) {
		// Sinusoidal fluctuation.
		float value = offset + (float)
			(a1 * sin(PI * x / p1) + 
			 a2 * sin(PI * x / p2) + 
			 a3 * sin(PI * x / p3));
		
		// Difficulty ramp-up.
		float ramp = 1.1f - 1 / (x/50 + 1);
		
		value *= ramp;
		
		if (value < 0.1f)
			return 0.1f;
		else if (value > 1f)
			return 1f;
		else
			return value;
	}
}
