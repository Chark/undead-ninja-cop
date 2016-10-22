package io.chark.undead_ninja_cop.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.GameEntityManager;
import io.chark.undead_ninja_cop.core.ResourceLoader;
import io.chark.undead_ninja_cop.engine.system.BasicRenderingSystem;
import io.chark.undead_ninja_cop.engine.system.DebugSystem;
import io.chark.undead_ninja_cop.engine.system.PhysicsSystem;
import io.chark.undead_ninja_cop.engine.system.factory.GameSystemCreator;
import io.chark.undead_ninja_cop.game.level.LevelLoader;

/**
 * Main game initializer class.
 */
public class Engine {

    private static final Configuration CONFIG = Configuration
            .getInstance();

    private final EntityManager entityManager;
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final World world;

    public Engine() {
        this.spriteBatch = new SpriteBatch();

        // Setup camera.
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,
                CONFIG.getSettings().getScreenWidth(),
                CONFIG.getSettings().getScreenHeight());

        this.world = new World(new Vector2(0, -10), true);

        ResourceLoader resourceLoader = new ResourceLoader();
        this.entityManager = new GameEntityManager(resourceLoader);

        // Finally initialize all systems and load the level.
        initializeSystems();
        new LevelLoader(resourceLoader, entityManager, world)
                .load("test");
    }

    public void render() {
        camera.update();

        // Update all entities.
        entityManager.updateSystems();

        // Draw after updating.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        entityManager.renderSystems();
    }

    public void dispose() {
        entityManager.removeEntities();
        spriteBatch.dispose();
    }

    /**
     * Initializes all game systems.
     */
    private void initializeSystems() {
        GameSystemCreator systemCreator = new GameSystemCreator(camera,
                entityManager,
                camera, spriteBatch,
                world);

        systemCreator.create(PhysicsSystem.class);
        systemCreator.create(BasicRenderingSystem.class);
        systemCreator.create(DebugSystem.class);
    }
}