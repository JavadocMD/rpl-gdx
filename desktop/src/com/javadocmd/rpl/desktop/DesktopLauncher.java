package com.javadocmd.rpl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.javadocmd.rpl.RplGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1024;
		config.height = 768;
		//config.x = 2600;
		//config.y = 400;
		config.resizable = false;
		config.title = "REPUBLIK POWER & LIGHT - Command Console";
		
		new LwjglApplication(new RplGame(), config);
	}
}
