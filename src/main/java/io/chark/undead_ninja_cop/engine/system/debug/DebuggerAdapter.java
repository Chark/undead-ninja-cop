package io.chark.undead_ninja_cop.engine.system.debug;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;

/**
 * Adapts debugger to a game system.
 */
public class DebuggerAdapter extends BaseGameSystem {

    private final SpriteBatch spriteBatch;
    private final OrthographicCamera camera;
    private final World world;

    private final Debugger debugger;

    public DebuggerAdapter(SpriteBatch spriteBatch,
                           OrthographicCamera camera,
                           World world,
                           Debugger debugger) {

        this.spriteBatch = spriteBatch;
        this.camera = camera;
        this.world = world;
        this.debugger = debugger;
        this.enabled = debugger.isEnabled();
    }

    @Override
    public void updateEntities(float dt) {
        debugger.handleInput(camera, dt);
    }

    @Override
    public void create() {
        debugger.load(resourceLoader);
    }

    @Override
    public void renderEntities(float dt) {
        debugger.handleRendering(entityManager, spriteBatch, camera, world);
    }
}