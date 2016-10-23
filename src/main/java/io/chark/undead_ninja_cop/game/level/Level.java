package io.chark.undead_ninja_cop.game.level;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.config.Configuration;
import io.chark.undead_ninja_cop.game.audio.MusicPlayer;
import io.chark.undead_ninja_cop.game.object.GameObject;
import io.chark.undead_ninja_cop.game.object.GameObjectVisitor;
import io.chark.undead_ninja_cop.game.object.pickup.NullPickup;
import io.chark.undead_ninja_cop.game.object.player.NullPlayer;
import io.chark.undead_ninja_cop.game.object.weapon.NullWeapon;
import io.chark.undead_ninja_cop.game.object.zombie.NullZombie;
import io.chark.undead_ninja_cop.game.resource.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;

public class Level {

    private final ResourceManager resourceManager;
    private final GameObjectVisitor renderer;
    private final GameObjectVisitor updater;

    private final List<GameObject> gameObjects = new ArrayList<>();

    private final MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private final Texture img = new Texture("undead-ninja-cop-logo.png");

    private final Configuration gameConfig = Configuration.getInstance();

    // todo - camera, box2d stuff, other services which handle HUD, HP, Time, Score

    Level(ResourceManager resourceManager,
          GameObjectVisitor renderer,
          GameObjectVisitor updater) {

        this.resourceManager = resourceManager;
        this.renderer = renderer;
        this.updater = updater;

        gameObjects.add(new NullZombie());
        gameObjects.add(new NullPickup());
        gameObjects.add(new NullPlayer());
        gameObjects.add(new NullWeapon());
    }

    /**
     * Update level entities.
     */
    public void update() {

        // Update each game object.
        for (GameObject object : gameObjects) {
            object.accept(updater);
        }

        // Testing music.
        if (input.isKeyJustPressed(Input.Keys.NUM_1)) {
            musicPlayer.play("io.chark.undead_ninja_cop.test.mp3");
        } else if (input.isKeyJustPressed(Input.Keys.NUM_2)) {
            musicPlayer.stop();
        }

        // Testing music volume.
        if (input.isKeyJustPressed(Input.Keys.NUM_3)) {
            musicPlayer.changeVolume(musicPlayer.getVolume() - 0.1f);
        } else if (input.isKeyJustPressed(Input.Keys.NUM_4)) {
            musicPlayer.changeVolume(musicPlayer.getVolume() + 0.1f);
        }
    }

    /**
     * Render level and level entities.
     *
     * @param batch sprite batch used in rendering.
     */
    public void render(SpriteBatch batch) {

        // Render each game object.
        for (GameObject object : gameObjects) {
            object.accept(renderer);
        }
        float x = gameConfig.getSettings().getScreenWidth() / 2 - img.getWidth() / 2;
        float y = gameConfig.getSettings().getScreenHeight() / 2 - img.getHeight() / 2;
        batch.draw(img, x, y);
    }

    /**
     * Clear level resources.
     */
    public void dispose() {
        img.dispose();
    }
}