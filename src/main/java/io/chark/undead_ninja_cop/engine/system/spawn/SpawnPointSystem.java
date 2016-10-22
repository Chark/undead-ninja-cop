package io.chark.undead_ninja_cop.engine.system.spawn;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.Physics;
import io.chark.undead_ninja_cop.engine.component.Player;
import io.chark.undead_ninja_cop.engine.component.SpawnPoint;
import io.chark.undead_ninja_cop.engine.component.Transform;

import java.util.Arrays;
import java.util.Set;

public class SpawnPointSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Transform.class, SpawnPoint.class);

    private static final float HEIGHT = 0.15f;
    private static final float WIDTH = 0.05f;

    private final float mpp = CONFIG.getGameplay().getMpp();
    private final World world;

    // todo use messaging for this stuff.
    private boolean spawned = false;

    public SpawnPointSystem(World world) {
        this.world = world;
    }

    @Override
    public void updateEntities(float dt) {
        if (!spawned) {
            for (Entity entity : entities) {
                SpawnPoint point = entityManager.getComponent(entity, SpawnPoint.class);
                if (SpawnPoint.Type.PLAYER.equals(point.getType())) {
                    Transform spawn = entityManager.getComponent(entity, Transform.class);

                    // Create physics body of the player.
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.DynamicBody;
                    bodyDef.position.set(spawn.getX() * mpp, spawn.getY() * mpp);
                    Body body = world.createBody(bodyDef);
                    body.setUserData("body");

                    PolygonShape poly = new PolygonShape();
                    poly.setAsBox(WIDTH, HEIGHT);
                    Fixture physicsFixture = body.createFixture(poly, 1);
                    poly.dispose();

                    // Drop a circle at the bottom for smooth walking.
                    CircleShape circle = new CircleShape();
                    circle.setRadius(WIDTH);
                    circle.setPosition(new Vector2(0, -HEIGHT));
                    Fixture sensorFixture = body.createFixture(circle, 0);
                    sensorFixture.setUserData("p");

                    circle.dispose();

                    body.setFixedRotation(true);
                    body.setBullet(true);

                    entityManager.createEntity(Arrays.asList(
                            new Transform(0 ,0),
                            new Player(physicsFixture, sensorFixture),
                            new Physics(body)));

                    spawned = true;
                }
            }
        }
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}