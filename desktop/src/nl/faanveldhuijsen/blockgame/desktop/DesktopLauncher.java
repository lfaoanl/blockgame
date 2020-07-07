package nl.faanveldhuijsen.blockgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.faanveldhuijsen.blockgame.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "BlockGame";
        config.width = 720;
        config.height = 480;
        config.fullscreen = true;

		new LwjglApplication(new Main(), config);
	}
}
