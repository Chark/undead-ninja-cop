package io.chark.undead_ninja_cop.engine.system.tiled;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.engine.component.Physics;
import io.chark.undead_ninja_cop.engine.component.SpawnPoint;
import io.chark.undead_ninja_cop.engine.component.Transform;

import java.util.Arrays;
import java.util.Collections;
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

    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final World world;

    private TiledMapRenderer tiledMapRenderer;
    private TiledMap tiledMap;

    private boolean first = true;

    TiledMapSystem(OrthographicCamera camera,
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

            // Create player spawn point.
            if (PLAYER.equals(obj.getName()) && obj instanceof EllipseMapObject) {
                Ellipse circle = ((EllipseMapObject) obj).getEllipse();

                Transform transform = new Transform(0, 0);
                transform.setX(circle.x);
                transform.setY(circle.y);

                entityManager.createEntity(Arrays.asList(
                        new SpawnPoint(SpawnPoint.Type.PLAYER),
                        transform));
            }
        }
    }

    /**
     * Initialize physics entities for currently loaded tile map.
     */
    private void initializeCollision() {
        MapLayer layer = tiledMap.getLayers().get(COLLISION);

        List<Shape> shapes = new ShapeBuilder()
                .build(layer.getObjects());

        for (Shape shape : shapes) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;

            Body body = world.createBody(bodyDef);
            body.createFixture(shape, 1);

            Transform transform = new Transform(0, 0);
            Physics physics = new Physics(body);

            entityManager.createEntity(Arrays.asList(transform, physics));

            shape.dispose();
        }
    }
}