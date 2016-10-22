package io.chark.undead_ninja_cop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.core.config.Gameplay;
import io.chark.undead_ninja_cop.core.config.Settings;
import io.chark.undead_ninja_cop.engine.Engine;
import io.chark.undead_ninja_cop.util.log.UncaughtExceptionLogger;

public class Application extends ApplicationAdapter {

    private Engine engine;

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

        new LwjglApplication(new Application(), configuration);
    }

    @Override
    public void create() {

        // Avoid using create in engine, this way we can keep using final fields in
        // our classes instead of having to initialize them in create method.
        this.engine = new Engine();
        UncaughtExceptionLogger.init();
    }

    @Override
    public void render() {
        engine.render();
    }

    public void dispose() {
        engine.dispose();
    }
}