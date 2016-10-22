package io.chark.undead_ninja_cop.engine.system.factory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.GameSystem;
import io.chark.undead_ninja_cop.core.GameSystemFactory;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.engine.system.BasicRenderingSystem;
import io.chark.undead_ninja_cop.engine.system.DebugSystem;
import io.chark.undead_ninja_cop.engine.system.PhysicsSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates game systems.
 */
public class GameSystemCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameSystemCreator.class);

    private final boolean debug = Configuration
            .getInstance()
            .getSettings()
            .isDebug();

    private final OrthographicCamera orthographicCamera;
    private final EntityManager entityManager;
    private final SpriteBatch spriteBatch;
    private final World world;

    public GameSystemCreator(OrthographicCamera orthographicCamera,
                             EntityManager entityManager,
                             SpriteBatch spriteBatch,
                             World world) {

        this.orthographicCamera = orthographicCamera;
        this.entityManager = entityManager;
        this.spriteBatch = spriteBatch;
        this.world = world;
    }

    /**
     * Create a game system of a specific type and add it to entity manager.
     *
     * @param type game system type.
     */
    public void create(Class<? extends GameSystem> type) {
        GameSystemFactory factory = type.equals(BasicRenderingSystem.class)
                ? new BasicRenderingSystemFactory(spriteBatch)
                : debug && type.equals(DebugSystem.class)
                ? new DebugSystemFactory(orthographicCamera, spriteBatch, world)
                : type.equals(PhysicsSystem.class)
                ? new PhysicsSystemFactory(world)
                : null;

        if (factory == null) {
            LOGGER.debug("Skipping creation of: {} game system", type.getSimpleName());
        } else {
            entityManager.createSystem(factory);
        }
    }
}