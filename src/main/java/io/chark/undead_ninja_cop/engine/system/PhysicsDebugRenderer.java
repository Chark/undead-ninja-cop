package io.chark.undead_ninja_cop.engine.system;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;

public class PhysicsDebugRenderer extends BaseGameSystem {

    private final Box2DDebugRenderer debugRenderer;
    private final Matrix4 debugMatrix;

    private final SpriteBatch spriteBatch;
    private final Camera camera;
    private final World world;

    public PhysicsDebugRenderer(SpriteBatch spriteBatch,
                                Camera camera,
                                World world) {

        this.spriteBatch = spriteBatch;
        this.camera = camera;
        this.world = world;

        this.debugMatrix = new Matrix4(camera.combined);
//        debugMatrix.scale(0.02f, 0.02f, 0);
        this.debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void renderEntities(float dt) {

        spriteBatch.begin();
        debugRenderer.render(world, debugMatrix);
        spriteBatch.end();
    }
}