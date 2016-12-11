package io.chark.undead_ninja_cop.engine.system.pickup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.event.EventListener;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.BasicRenderable;
import io.chark.undead_ninja_cop.engine.component.Pickup;
import io.chark.undead_ninja_cop.engine.component.Transform;
import io.chark.undead_ninja_cop.engine.component.physics.FixtureBuilder;
import io.chark.undead_ninja_cop.engine.component.physics.Physics;
import io.chark.undead_ninja_cop.engine.component.physics.PhysicsBuilder;
import io.chark.undead_ninja_cop.engine.system.physics.MarkedForDestroy;

import java.util.*;

public class PickupSystem extends BaseGameSystem {

    private static final String POINTS_TEXTURE_NAME = "points.png";
    private static final String HEALTH_TEXTURE_NAME = "health.png";
    private final World world;

    public PickupSystem(World world) {
        this.world = world;
    }

    @Override
    public void create() {
        entityManager.register(new EventListener<SpawnPickupEvent>() {

            @Override
            public void onEvent(SpawnPickupEvent event) {

                // Transform same for all pickup types.
                Transform transform = new Transform();
                transform.setX(event.getX());
                transform.setY(event.getY());

                // Decide on texture based on type.
                Texture texture = resourceLoader.getTexture(event.getType() == Pickup.Type.POINTS
                        ? POINTS_TEXTURE_NAME
                        : HEALTH_TEXTURE_NAME);

                // Decide on other pickup details.
                Pickup pickup = new Pickup(event.getType() == Pickup.Type.POINTS ? 5f : 20f, event.getType());
                entityManager.createEntity(Arrays.asList(
                        pickup,
                        new BasicRenderable(texture),
                        transform,
                        PhysicsBuilder
                                .usingWorld(world)
                                .type(BodyDef.BodyType.StaticBody)
                                .position(transform.getX(), transform.getY())
                                .addFixture(pickup, FixtureBuilder.builder()
                                        .dimensions(2, 2)
                                        .build(Shape.Type.Polygon))
                                .build()
                ));
            }
        });

        entityManager.register(new EventListener<TouchPickupEvent>() {

            @Override
            public void onEvent(TouchPickupEvent event) {
                for (Entity entity : entities) {
                    Pickup pickup = entityManager.getComponent(entity, Pickup.class);

                    // Remove the pickup.
                    if (pickup == event.getPickup()) {
                        Physics physics = entityManager.getComponent(entity, Physics.class);
                        physics.getBody().setUserData(MarkedForDestroy.getInstance());

                        entityManager.removeEntity(entity);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return Components.toSet(Pickup.class, Physics.class);
    }
}