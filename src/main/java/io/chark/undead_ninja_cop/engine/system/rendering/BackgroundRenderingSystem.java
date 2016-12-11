package io.chark.undead_ninja_cop.engine.system.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.core.BaseGameSystem;

public class BackgroundRenderingSystem extends BaseGameSystem {

    private static final int WIDTH = CONFIG.getSettings().getScreenWidth();
    private static final int HEIGHT = CONFIG.getSettings().getScreenHeight();

    private final OrthographicCamera stationaryCamera = new OrthographicCamera();
    private final SpriteBatch spriteBatch;

    private Texture background;

    public BackgroundRenderingSystem(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void create() {
        this.background = resourceLoader.getTexture("default_background.png");
        stationaryCamera.setToOrtho(false, WIDTH, HEIGHT);
    }

    @Override
    public void renderEntities(float dt) {
        spriteBatch.setProjectionMatrix(stationaryCamera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, WIDTH, HEIGHT);
        spriteBatch.end();
    }
}