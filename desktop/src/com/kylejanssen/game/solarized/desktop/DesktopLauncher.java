package com.kylejanssen.game.solarized.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kylejanssen.game.solarized.SolarizedGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Solarized Game";
        config.width = 1280;
        config.height = 720;
        config.resizable = false;
		new LwjglApplication(new SolarizedGame(), config);
	}
}
