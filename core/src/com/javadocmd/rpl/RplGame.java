package com.javadocmd.rpl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.javadocmd.rpl.terminal.Direktor;
import com.javadocmd.rpl.util.TiledBackgroundActor;

public class RplGame extends ApplicationAdapter {

	private Stage stage;
	private ShaderProgram shader;
	private Direktor direktor;

	private float u_time = 0f;

	@Override
	public void create() {
		float w = Gdx.app.getGraphics().getWidth();
		float h = Gdx.app.getGraphics().getHeight();

		Resources.load();
		Resources res = Resources.INSTANCE;

		stage = new Stage();

		shader = new ShaderProgram(res.vertexShader, res.fragmentShader);
		if (!shader.isCompiled()) {
			Gdx.app.log("Shader", shader.getLog());
			Gdx.app.exit();
		}

		stage.getBatch().setShader(shader);

		TiledBackgroundActor bg = new TiledBackgroundActor(w, h,
				res.screenLines);
		stage.addActor(bg);

		direktor = new Direktor(w, h, stage);
		direktor.begin();
	}

	// private FPSLogger fpsLogger = new FPSLogger();

	@Override
	public void render() {
		// fpsLogger.log();
		u_time += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shader.begin();
		shader.setUniformf("u_time", u_time);
		shader.setUniformf("u_flickerStrength", direktor.getFlickerStrength());
		shader.setUniformf("u_staticStrength", direktor.getStaticStrength());

		stage.act();
		stage.draw();

		shader.end();
	}

	@Override
	public void dispose() {
		stage.dispose();
		Resources.INSTANCE.dispose();
	}
}
