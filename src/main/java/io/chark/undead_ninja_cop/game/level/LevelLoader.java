package io.chark.undead_ninja_cop.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import io.chark.undead_ninja_cop.config.Configuration;
import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.ResourceLoader;
import io.chark.undead_ninja_cop.engine.component.BasicRenderable;
import io.chark.undead_ninja_cop.engine.component.Physics;
import io.chark.undead_ninja_cop.engine.component.Transform;

import java.util.Arrays;

/**
 * Loads various type levels.
 */
public class LevelLoader {

    private static final String LEVEL_DATA = "data.level";
    private static final String LEVEL_DIR = "levels/";

    private static final int TILE_SIZE = 64;
    private static final char AIR = ' ';

    private final ResourceLoader resourceLoader;
    private final EntityManager entityManager;
    private final World world;

    public LevelLoader(ResourceLoader resourceLoader,
                       EntityManager entityManager,
                       World world) {

        this.resourceLoader = resourceLoader;
        this.entityManager = entityManager;
        this.world = world;
    }

    /**
     * Load a level by name.
     *
     * @param dir level directory name.
     * @return level instance.
     */
    public Level load(String dir) {
        String path = LEVEL_DIR
                + (dir.contains("/") ? dir : dir + "/")
                + LEVEL_DATA;

        String[] data = Gdx.files.internal(path)
                .readString()
                .split("\n");

        int y = data.length;
        float mpp = Configuration.getInstance().getGameplay().getMpp();
        for (String line : data) {

            int x = 0;
            for (char c : line.toCharArray()) {

                if (c != AIR) {
                    BasicRenderable renderable = new BasicRenderable(resourceLoader
                            .getTexture(ResourceLoader.TEST_TEXTURE));

                    Transform transform = new Transform(1, 1);
                    transform.setX(x * TILE_SIZE);
                    transform.setY(y * TILE_SIZE);

                    // Create physics bodies for our tiles.
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(transform.getX() * mpp, transform.getY() * mpp);

                    Body body = world.createBody(bodyDef);

                    PolygonShape shape = new PolygonShape();
                    shape.setAsBox(renderable.getTexture().getWidth() / 2 * mpp,
                            renderable.getTexture().getHeight() / 2 * mpp);

                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 1f;
                    body.createFixture(fixtureDef);

                    Physics physics = new Physics(body);

                    entityManager.createEntity(Arrays.asList(renderable, transform, physics));
                }
                x++;
            }

            // From top to bottom.
            y--;
        }
        return new TestLevel(entityManager);
    }
}