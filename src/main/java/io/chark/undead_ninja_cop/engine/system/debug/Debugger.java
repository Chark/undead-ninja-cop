package io.chark.undead_ninja_cop.engine.system.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.StringBuilder;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.ResourceLoader;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.core.config.Gameplay;
import io.chark.undead_ninja_cop.core.config.Settings;
import io.chark.undead_ninja_cop.util.Math;

import java.text.DecimalFormat;

public class Debugger {

    private static final Settings SETTINGS = Configuration.getInstance().getSettings();
    private static final Gameplay GAMEPLAY = Configuration.getInstance().getGameplay();

    private static final float ZOOM_SPEED = 1;
    private static final float SPEED = 500;

    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    private Texture crosshair;
    private Texture xyCross;
    private BitmapFont font;

    public void load(ResourceLoader resourceLoader) {
        this.crosshair = resourceLoader.getTexture("crosshair.png");
        this.xyCross = resourceLoader.getTexture("xy_cross.png");
        this.font = resourceLoader.getDefaultFont();
    }

    public void handleRendering(EntityManager entityManager,
                                SpriteBatch spriteBatch,
                                OrthographicCamera camera,
                                World world) {

        float width = SETTINGS.getScreenWidth();
        float height = SETTINGS.getScreenHeight();
        float ppm = GAMEPLAY.getPpm();

        Matrix4 debugMatrix = new Matrix4(camera.combined);
        debugMatrix.scale(ppm, ppm, 0);
        debugRenderer.render(world, debugMatrix);

        Matrix4 hudMatrix = new Matrix4(camera.combined);
        hudMatrix.setToOrtho2D(0, 0, width, height);

        spriteBatch.begin();

        // (0, 0) coordinate for reference.
        spriteBatch.draw(xyCross, 0, 0);

        // HUD stuff.
        spriteBatch.setProjectionMatrix(hudMatrix);
        font.draw(spriteBatch, getDebugText(entityManager, camera), 0, height);
        spriteBatch.draw(crosshair, width / 2, height / 2);

        spriteBatch.end();
    }

    public void handleInput(OrthographicCamera camera,
                            float dt) {

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

    public boolean isEnabled() {
        return Configuration.getInstance().getSettings().isDebug();
    }

    private String getDebugText(EntityManager entityManager,
                                OrthographicCamera camera) {

        Vector2 mousePos = Math.getMousePosition(camera);

        // @formatter:off
        return new StringBuilder("")
            .append("\ncamera: (")
                .append(decimalFormat.format(camera.position.x))
                .append(", ")
                .append(decimalFormat.format(camera.position.y))
                .append(")")
            .append("\nmouse: (")
                .append((int) mousePos.x)
                .append(", ")
                .append((int) mousePos.y)
                .append(")")
            .append("\nentities: ")
                .append(entityManager.getEntityCount())
            .append("\nfps: ")
                .append(Gdx.graphics.getFramesPerSecond())
            .toString();
        // @formatter:on
    }
}