package io.chark.undead_ninja_cop.engine.system.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.core.GameSystem;
import io.chark.undead_ninja_cop.core.GameSystemFactory;

public class BasicRenderingSystemFactory implements GameSystemFactory {

    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;

    public BasicRenderingSystemFactory(OrthographicCamera camera,
                                       SpriteBatch spriteBatch) {

        this.camera = camera;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public GameSystem create() {
        return new BasicRenderingSystem(camera, spriteBatch);
    }
}