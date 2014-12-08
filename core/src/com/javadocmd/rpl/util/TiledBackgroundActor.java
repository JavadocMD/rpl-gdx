package com.javadocmd.rpl.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TiledBackgroundActor extends Actor {

	private TextureRegion tex;
	private float tw, th;

	public TiledBackgroundActor(float w, float h, TextureRegion texture) {
		this.tex = texture;
		tw = texture.getRegionWidth();
		th = texture.getRegionHeight();
		setSize(w, h);
		setPosition(0, 0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Color.WHITE);
		float w = getWidth() + getX();
		float h = getHeight() + getY();

		for (float x = getX(); x < w; x += tw) {
			for (float y = getY(); y < h; y += th) {
				batch.draw(tex, x, y, tw, th);
			}
		}
	}
}
