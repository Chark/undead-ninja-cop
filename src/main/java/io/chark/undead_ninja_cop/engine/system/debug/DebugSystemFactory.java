package io.chark.undead_ninja_cop.engine.system.debug;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.GameSystem;
import io.chark.undead_ninja_cop.core.GameSystemFactory;

public class DebugSystemFactory implements GameSystemFactory {

    private final OrthographicCamera orthographicCamera;
    private final SpriteBatch spriteBatch;
    private final World world;

    public DebugSystemFactory(OrthographicCamera orthographicCamera,
                              SpriteBatch spriteBatch,
                              World world) {

        this.orthographicCamera = orthographicCamera;
        this.spriteBatch = spriteBatch;
        this.world = world;
    }

    @Override
    public GameSystem create() {
        return new DebugSystem(orthographicCamera, spriteBatch, world);
    }
}