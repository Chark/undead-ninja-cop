package io.chark.undead_ninja_cop.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.game.level.Level;
import io.chark.undead_ninja_cop.game.level.LevelFactory;
import io.chark.undead_ninja_cop.util.log.UncaughtExceptionLogger;

public class UndeadNinjaCop extends ApplicationAdapter {

    private Level currentLevel;
    private SpriteBatch batch;

    @Override
    public void create() {
        UncaughtExceptionLogger.init();

        batch = new SpriteBatch();
        currentLevel = new LevelFactory(batch).create("initial");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentLevel.update();

        batch.begin();
        currentLevel.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        currentLevel.dispose();
        batch.dispose();
    }
}