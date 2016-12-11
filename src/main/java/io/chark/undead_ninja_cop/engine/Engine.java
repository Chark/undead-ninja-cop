package io.chark.undead_ninja_cop.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.GameEntityManager;
import io.chark.undead_ninja_cop.core.ResourceLoader;
import io.chark.undead_ninja_cop.engine.system.GameSystemCreator;
import io.chark.undead_ninja_cop.engine.system.player.PlayerSystem;
import io.chark.undead_ninja_cop.engine.system.rendering.BackgroundRenderingSystem;
import io.chark.undead_ninja_cop.engine.system.rendering.BasicRenderingSystem;
import io.chark.undead_ninja_cop.engine.system.debug.DebugSystem;
import io.chark.undead_ninja_cop.engine.system.physics.PhysicsSystem;
import io.chark.undead_ninja_cop.engine.system.GameSystemFactory;
import io.chark.undead_ninja_cop.engine.system.spawn.SpawnPointSystem;
import io.chark.undead_ninja_cop.engine.system.tiled.TiledMapSystem;

/**
 * Main game initializer class.
 */
public class Engine implements Disposable {

    private static final Configuration CONFIG = Configuration
            .getInstance();

    private final ResourceLoader resourceLoader;
    private final EntityManager entityManager;
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final World world;

    public Engine() {
        this.resourceLoader = new ResourceLoader();
        this.spriteBatch = new SpriteBatch();

        // Setup camera.
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,
                CONFIG.getSettings().getScreenWidth(),
                CONFIG.getSettings().getScreenHeight());

        this.world = new World(new Vector2(0, -10), true);
        this.entityManager = new GameEntityManager(resourceLoader);

        // Finally initialize all systems.
        initializeSystems();
    }

    public void render() {
        camera.update();

        // Update all entities.
        entityManager.updateSystems();

        // Exit game.
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Enable or disable debug.
        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            DebugSystem debug = entityManager.getSystem(DebugSystem.class);
            debug.setEnabled(!debug.isEnabled());
            CONFIG.getSettings().setDebug(debug.isEnabled());
        }

        // Draw after updating.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        entityManager.renderSystems();
    }

    @Override
    public void dispose() {
        entityManager.removeEntities();
        resourceLoader.dispose();
        spriteBatch.dispose();
    }

    /**
     * Initializes all game systems.
     */
    private void initializeSystems() {
        GameSystemCreator systemCreator = new GameSystemCreator(
                new GameSystemFactory(spriteBatch, camera, world),
                entityManager);

        systemCreator.create(BackgroundRenderingSystem.class);
        systemCreator.create(PhysicsSystem.class);
        systemCreator.create(TiledMapSystem.class);
        systemCreator.create(SpawnPointSystem.class);
        systemCreator.create(PlayerSystem.class);
        systemCreator.create(BasicRenderingSystem.class);
        systemCreator.create(DebugSystem.class);
    }
}