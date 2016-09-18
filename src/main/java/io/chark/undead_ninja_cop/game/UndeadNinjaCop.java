package io.chark.undead_ninja_cop.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.game.audio.MusicPlayer;
import io.chark.undead_ninja_cop.util.log.UncaughtExceptionLogger;

import static com.badlogic.gdx.Gdx.input;

public class UndeadNinjaCop extends ApplicationAdapter {

    private final MusicPlayer player = MusicPlayer.getInstance();

    private SpriteBatch batch;
    private Texture img;

    @Override
    public void create() {
        UncaughtExceptionLogger.init();

        batch = new SpriteBatch();
        img = new Texture("test.jpg");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Testing music.
        if (input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.play("test.mp3");
        } else if (input.isKeyJustPressed(Input.Keys.NUM_2)) {
            player.stop();
        }

        // Testing music volume.
        if (input.isKeyJustPressed(Input.Keys.NUM_3)) {
            player.changeVolume(player.getVolume() - 0.1f);
        } else if (input.isKeyJustPressed(Input.Keys.NUM_4)) {
            player.changeVolume(player.getVolume() + 0.1f);
        }

        // Draw entities after updating.
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}