package io.chark.undead_ninja_cop.engine.system;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.GameSystem;
import io.chark.undead_ninja_cop.core.GameSystemFactory;
import io.chark.undead_ninja_cop.engine.system.debug.DebugSystem;
import io.chark.undead_ninja_cop.engine.system.debug.DebugSystemFactory;
import io.chark.undead_ninja_cop.engine.system.physics.PhysicsSystem;
import io.chark.undead_ninja_cop.engine.system.physics.PhysicsSystemFactory;
import io.chark.undead_ninja_cop.engine.system.player.PlayerSystem;
import io.chark.undead_ninja_cop.engine.system.player.PlayerSystemFactory;
import io.chark.undead_ninja_cop.engine.system.rendering.BasicRenderingSystem;
import io.chark.undead_ninja_cop.engine.system.rendering.BasicRenderingSystemFactory;
import io.chark.undead_ninja_cop.engine.system.spawn.SpawnPointSystem;
import io.chark.undead_ninja_cop.engine.system.spawn.SpawnPointSystemFactory;
import io.chark.undead_ninja_cop.engine.system.tiled.TiledMapSystem;
import io.chark.undead_ninja_cop.engine.system.tiled.TiledSystemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates game systems.
 */
public class GameSystemCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameSystemCreator.class);

    private final OrthographicCamera orthographicCamera;
    private final EntityManager entityManager;
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final World world;

    public GameSystemCreator(OrthographicCamera orthographicCamera,
                             EntityManager entityManager,
                             OrthographicCamera camera,
                             SpriteBatch spriteBatch,
                             World world) {

        this.orthographicCamera = orthographicCamera;
        this.entityManager = entityManager;
        this.camera = camera;
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
                ? new BasicRenderingSystemFactory(camera, spriteBatch)
                : type.equals(DebugSystem.class)
                ? new DebugSystemFactory(orthographicCamera, spriteBatch, world)
                : type.equals(PhysicsSystem.class)
                ? new PhysicsSystemFactory(world)
                : type.equals(TiledMapSystem.class)
                ? new TiledSystemFactory(camera, spriteBatch, world)
                : type.equals(PlayerSystem.class)
                ? new PlayerSystemFactory(world)
                : type.equals(SpawnPointSystem.class)
                ? new SpawnPointSystemFactory(world)
                : null;

        if (factory == null) {
            LOGGER.debug("Skipping creation of: {} game system", type.getSimpleName());
        } else {
            entityManager.createSystem(factory);
        }
    }
}