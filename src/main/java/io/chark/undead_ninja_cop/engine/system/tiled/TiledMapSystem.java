package io.chark.undead_ninja_cop.engine.system.tiled;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.exception.GameException;
import io.chark.undead_ninja_cop.engine.component.BasicRenderable;
import io.chark.undead_ninja_cop.engine.component.Pickup;
import io.chark.undead_ninja_cop.engine.component.SpawnPoint;
import io.chark.undead_ninja_cop.engine.component.Transform;
import io.chark.undead_ninja_cop.engine.component.physics.FixtureBuilder;
import io.chark.undead_ninja_cop.engine.component.physics.PhysicsBuilder;
import io.chark.undead_ninja_cop.engine.system.pickup.SpawnPickupEvent;

import java.util.Arrays;
import java.util.List;

public class TiledMapSystem extends BaseGameSystem {

    /**
     * Collision layer name.
     */
    private static final String COLLISION = "collision";

    /**
     * Entity layer name.
     */
    private static final String ENTITIES = "entities";

    /**
     * Player spawn point.
     */
    private static final String PLAYER = "player";

    /**
     * Pickup which grants points.
     */
    private static final String POINT_PICKUP = "points";

    /**
     * Pickup which grants health points.
     */
    private static final String HEALTH_PICKUP = "health";

    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final World world;

    private TiledMapRenderer tiledMapRenderer;
    private TiledMap tiledMap;

    private boolean first = true;

    public TiledMapSystem(OrthographicCamera camera,
                          SpriteBatch spriteBatch,
                          World world) {

        this.camera = camera;
        this.spriteBatch = spriteBatch;
        this.world = world;
    }

    @Override
    public void create() {
        this.tiledMap = resourceLoader.getTiledMap("test.tmx");
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, spriteBatch);
    }

    @Override
    public void updateEntities(float dt) {
        if (first) {
            initializeCollision();
            initializeEntities();

            first = false;
        }
    }

    @Override
    public void renderEntities(float dt) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    /**
     * Initialize tiled map entities.
     */
    private void initializeEntities() {
        MapLayer layer = tiledMap.getLayers().get(ENTITIES);

        for (MapObject obj : layer.getObjects()) {

            // Handle boxies.
            if (obj instanceof RectangleMapObject) {
                initializeBoxie(((RectangleMapObject) obj).getRectangle());
                continue;
            }

            // Handle spawn points and such.
            if (!(obj instanceof EllipseMapObject)) {
                continue;
            }

            String name = obj.getName();

            Ellipse circle = ((EllipseMapObject) obj).getEllipse();
            Transform transform = new Transform();
            transform.setX(circle.x + circle.width / 2);
            transform.setY(circle.y + circle.height / 2);

            // Create player spawn point.
            if (PLAYER.equals(name)) {
                entityManager.createEntity(Arrays.asList(
                        new SpawnPoint(SpawnPoint.Type.PLAYER),
                        transform));

            } else if (POINT_PICKUP.equals(name) || HEALTH_PICKUP.equals(name)) {
                entityManager.dispatch(new SpawnPickupEvent(
                        transform.getX(),
                        transform.getY(),
                        POINT_PICKUP.equals(name)
                                ? Pickup.Type.POINTS
                                : Pickup.Type.HEALTH));
            }
        }
    }

    /**
     * Initializes a dynamic box.
     */
    private void initializeBoxie(Rectangle rectangle) {
        Texture texture = resourceLoader.getTexture("boxy.png");

        Transform transform = new Transform(
                rectangle.getWidth() / texture.getWidth() * 2,
                rectangle.getHeight() / texture.getHeight() * 2);

        transform.setX(rectangle.getX());
        transform.setY(rectangle.getY());

        entityManager.createEntity(Arrays.asList(
                new BasicRenderable(texture),
                transform,
                PhysicsBuilder
                        .usingWorld(world)
                        .dynamic()
                        .position(rectangle.getX(), rectangle.getY())
                        .addFixture(FixtureBuilder.builder()
                                .density(1)
                                .dimensions(rectangle.getWidth(), rectangle.getHeight())
                                .build(Shape.Type.Polygon))
                        .build()));
    }

    /**
     * Initialize physics entities for currently loaded tile map.
     */
    private void initializeCollision() {
        MapLayer layer = tiledMap.getLayers().get(COLLISION);

        if (layer == null) {
            throw new GameException("Could not get %s layer", COLLISION);
        }

        List<Shape> shapes = new ShapeBuilder()
                .build(layer.getObjects());

        for (Shape shape : shapes) {
            entityManager.createEntity(Arrays.asList(
                    new Transform(),
                    PhysicsBuilder.usingWorld(world)
                            .shape(shape, 1)
                            .build()));

            shape.dispose();
        }
    }
}