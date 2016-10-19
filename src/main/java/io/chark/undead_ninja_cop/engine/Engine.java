package io.chark.undead_ninja_cop.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.config.Configuration;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.GameEntityManager;
import io.chark.undead_ninja_cop.core.ResourceLoader;
import io.chark.undead_ninja_cop.engine.system.BasicRenderingSystem;
import io.chark.undead_ninja_cop.engine.system.PhysicsDebugRenderer;
import io.chark.undead_ninja_cop.engine.system.PhysicsSystem;
import io.chark.undead_ninja_cop.game.level.LevelLoader;
import io.chark.undead_ninja_cop.util.log.UncaughtExceptionLogger;

/**
 * Main game initializer class.
 */
public class Engine extends ApplicationAdapter {

    private static final Configuration CONFIGURATION = Configuration
            .getInstance();

    private final EntityManager entityManager;
    private final LevelLoader levelLoader;
    private final OrthographicCamera camera;
    private final World world;

    private SpriteBatch spriteBatch;

    /**
     * Initialize main game components.
     */
    public Engine() {
        ResourceLoader resourceLoader = new ResourceLoader();

        this.entityManager = new GameEntityManager(resourceLoader);
        this.camera = new OrthographicCamera();
        this.world = new World(new Vector2(0, -10), true);
        this.levelLoader = new LevelLoader(resourceLoader, entityManager, world);
    }

    @Override
    public void create() {
        UncaughtExceptionLogger.init();

        spriteBatch = new SpriteBatch();
        initializeSystems();

        // Initialize camera.
        float width = CONFIGURATION.getSettings().getScreenWidth();
        float height = CONFIGURATION.getSettings().getScreenHeight();

        camera.setToOrtho(false, width, height);
        camera.update();

        levelLoader.load("test");
    }

    @Override
    public void render() {

        // Update all entities.
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        entityManager.updateSystems();

        // Draw after updating.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        entityManager.addSystem(new PhysicsSystem(world));
        entityManager.addSystem(new BasicRenderingSystem(spriteBatch));
        entityManager.addSystem(new PhysicsDebugRenderer(spriteBatch, camera, world));
    }
}