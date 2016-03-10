package com.mbostic.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.mbostic.game.JakaIgrcaMain;


public class DesktopLauncher {

		private static boolean rebuildAtlas = true;
		private static boolean drawDebugOutline = false;
		public static void main (String[] arg) {
			if (rebuildAtlas) {
				Settings settings = new Settings();
				settings.maxWidth = 1024;
				settings.maxHeight = 1024;
				settings.duplicatePadding = false;
				settings.debug = drawDebugOutline;
				TexturePacker.process(settings, "desktop/images", "desktop/images", "game.pack");
			}
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width = 800;
			config.height = 480;
			//new LwjglApplication(new JakaIgrcaMain(), config);
		}
	}

