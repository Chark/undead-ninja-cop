package io.chark.undead_ninja_cop.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.game.object.GameObjectRenderer;
import io.chark.undead_ninja_cop.game.object.GameObjectUpdater;
import io.chark.undead_ninja_cop.game.resource.GameResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LevelFactory.class);

    private final GameResourceManager resourceManager;
    private final GameObjectRenderer renderer;
    private final GameObjectUpdater updater;

    public LevelFactory(SpriteBatch spriteBatch) {
        this.resourceManager = new GameResourceManager();
        this.renderer = new GameObjectRenderer(spriteBatch);
        this.updater = new GameObjectUpdater();
    }

    public Level create(String name) {
        LOGGER.debug("Creating level: {}", name);

        return new Level(resourceManager,
                renderer,
                updater);
    }
}