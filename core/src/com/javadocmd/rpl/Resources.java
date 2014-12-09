package com.javadocmd.rpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Disposable;

public class Resources implements Disposable {

	public static Resources INSTANCE = null;

	public static void load() {
		INSTANCE = new Resources();
	}

	public final TextureAtlas atlas;
	public final BitmapFont font;
	public final LabelStyle labelStyle;
	public final TextureRegion screenLines;
	public final TextureRegion pixel;
	
	public final FileHandle vertexShader;
	public final FileHandle fragmentShader;

	private Resources() {
		atlas = new TextureAtlas(Gdx.files.internal("rpl.pack"));
		font = new BitmapFont(Gdx.files.internal("bpd-diamond-20-bold.fnt"),
				atlas.findRegion("bpd-diamond-20-bold"));
		font.setMarkupEnabled(true);
		labelStyle = new LabelStyle(font, Color.WHITE);
		screenLines = atlas.findRegion("screen-lines");
		pixel = atlas.findRegion("pixel");
		
		vertexShader = Gdx.files.internal("basic-vertex.glsl");
		fragmentShader = Gdx.files.internal("fragment.glsl");
	}

	public void dispose() {
		atlas.dispose();
		font.dispose();
	}
}
