package com.ilya.busyElevator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.ilya.busyElevator.ElevatorMain;

public class DesktopLauncher {
    private static boolean rebuildAtlas = false;
    private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {
        if (rebuildAtlas) {
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.maxWidth = 1024;
            settings.maxHeight = 1024;
            settings.duplicatePadding = false;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "raw-images", "images", "elevator.pack");
            TexturePacker.process(settings,"faces","images","elevatorFaces.pack");
        }
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Elevator";
        config.width = 640;
        config.height = 960;
        config.x = 1920/2-400;
        config.y = 0;
		new LwjglApplication(new ElevatorMain(new DesktopAd()), config);
	}
}
