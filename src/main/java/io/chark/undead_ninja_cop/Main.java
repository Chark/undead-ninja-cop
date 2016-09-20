package io.chark.undead_ninja_cop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.chark.undead_ninja_cop.config.Configuration;
import io.chark.undead_ninja_cop.config.Gameplay;
import io.chark.undead_ninja_cop.config.Settings;
import io.chark.undead_ninja_cop.game.UndeadNinjaCop;

public class Main {

    public static void main(String[] arg) {
        Configuration gameConfig = Configuration
                .getInstance();

        LwjglApplicationConfiguration configuration =
                new LwjglApplicationConfiguration();

        Settings settings = gameConfig.getSettings();
        configuration.fullscreen = settings.isFullScreen();
        configuration.width = settings.getScreenWidth();
        configuration.height = settings.getScreenHeight();

        Gameplay gameplay = gameConfig.getGameplay();
        configuration.foregroundFPS = gameplay.getMaxFps();

        new LwjglApplication(new UndeadNinjaCop(), configuration);
    }
}