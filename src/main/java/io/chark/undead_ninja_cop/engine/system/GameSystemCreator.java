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

    private final BasicRenderingSystemFactory basicRenderingSystemFactory;
    private final SpawnPointSystemFactory spawnPointSystemFactory;
    private final PhysicsSystemFactory physicsSystemFactory;
    private final PlayerSystemFactory playerSystemFactory;
    private final TiledSystemFactory tiledSystemFactory;
    private final DebugSystemFactory debugSystemFactory;

    private final EntityManager entityManager;

    public GameSystemCreator(EntityManager entityManager,
                             SpriteBatch spriteBatch,
                             OrthographicCamera camera,
                             World world) {

        this.basicRenderingSystemFactory = new BasicRenderingSystemFactory(camera, spriteBatch);
        this.spawnPointSystemFactory = new SpawnPointSystemFactory(world);
        this.physicsSystemFactory = new PhysicsSystemFactory(world);
        this.playerSystemFactory = new PlayerSystemFactory(world);
        this.tiledSystemFactory = new TiledSystemFactory(camera, spriteBatch, world);
        this.debugSystemFactory = new DebugSystemFactory(camera, spriteBatch, world);
        this.entityManager = entityManager;
    }

    /**
     * Create a game system of a specific type and add it to entity manager.
     *
     * @param type game system type.
     */
    public void create(Class<? extends GameSystem> type) {
        GameSystemFactory factory = type.equals(BasicRenderingSystem.class)
                ? basicRenderingSystemFactory
                : type.equals(DebugSystem.class)
                ? debugSystemFactory
                : type.equals(PhysicsSystem.class)
                ? physicsSystemFactory
                : type.equals(TiledMapSystem.class)
                ? tiledSystemFactory
                : type.equals(PlayerSystem.class)
                ? playerSystemFactory
                : type.equals(SpawnPointSystem.class)
                ? spawnPointSystemFactory
                : null;

        if (factory == null) {
            LOGGER.debug("Skipping creation of: {} game system", type.getSimpleName());
        } else {
            entityManager.createSystem(factory);
        }
    }
}