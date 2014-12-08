package com.javadocmd.rpl.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SolidBackgroundActor extends Actor {

	private TextureRegion tex;

	public SolidBackgroundActor(float w, float h, TextureRegion texture, Color color) {
		this.tex = texture;
		setColor(color);
		setSize(w, h);
		setPosition(0, 0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = getColor().cpy();
		c.a = c.a * parentAlpha;
		batch.setColor(c);
		batch.draw(tex, getX(), getY(), getWidth(), getHeight());
	}
}
