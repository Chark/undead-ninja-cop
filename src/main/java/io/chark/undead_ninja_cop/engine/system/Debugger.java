package io.chark.undead_ninja_cop.engine.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;

public class Debugger extends BaseGameSystem {

    private static final float SPEED = 500;
    private static final float ZOOM_SPEED = 1;

    private final Box2DDebugRenderer debugRenderer;
    private final OrthographicCamera camera;
    private final World world;

    public Debugger(OrthographicCamera camera,
                    World world) {

        this.debugRenderer = new Box2DDebugRenderer();
        this.camera = camera;
        this.world = world;
    }

    @Override
    public void renderEntities(float dt) {
        float ppm = CONFIG.getGameplay().getPpm();

        Matrix4 debugMatrix = new Matrix4(camera.combined);
        debugMatrix.scale(ppm, ppm, 0);

        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void updateEntities(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-SPEED * dt, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(SPEED * dt, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, SPEED * dt, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -SPEED * dt, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += ZOOM_SPEED * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= ZOOM_SPEED * dt;
        }
    }
}