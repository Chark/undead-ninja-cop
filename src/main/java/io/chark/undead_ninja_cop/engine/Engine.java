package io.chark.undead_ninja_cop.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.GameEntityManager;
import io.chark.undead_ninja_cop.engine.system.BasicRenderingSystem;
import io.chark.undead_ninja_cop.engine.system.PhysicsSystem;
import io.chark.undead_ninja_cop.game.level.LevelLoader;
import io.chark.undead_ninja_cop.core.ResourceLoader;
import io.chark.undead_ninja_cop.util.log.UncaughtExceptionLogger;

/**
 * Main game initializer class.
 */
public class Engine extends ApplicationAdapter {

    private final ResourceLoader resourceLoader;
    private final EntityManager entityManager;
    private final LevelLoader levelLoader;

    private SpriteBatch spriteBatch;

    /**
     * Initialize main game components.
     */
    public Engine() {
        this.resourceLoader = new ResourceLoader();
        this.entityManager = new GameEntityManager(resourceLoader);
        this.levelLoader = new LevelLoader(resourceLoader, entityManager);
    }

    @Override
    public void create() {
        UncaughtExceptionLogger.init();

        spriteBatch = new SpriteBatch();
        initializeSystems();

        levelLoader.load("test");
    }

    @Override
    public void render() {

        // Might be useful in the future to separate updating and rendering.
        entityManager.updateSystems();
        entityManager.renderSystems();
    }

    @Override
    public void dispose() {
        entityManager.removeEntities();
        spriteBatch.dispose();
    }

    /**
     * Initializes all game systems.
     */
    private void initializeSystems() {
        entityManager.addSystem(new BasicRenderingSystem(spriteBatch));
        entityManager.addSystem(new PhysicsSystem());
    }
}