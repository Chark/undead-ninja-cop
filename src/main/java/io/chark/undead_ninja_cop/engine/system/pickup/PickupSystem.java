package io.chark.undead_ninja_cop.engine.system.pickup;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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
import io.chark.undead_ninja_cop.engine.system.pickup.chain.ProcessHealthPickup;
import io.chark.undead_ninja_cop.engine.system.pickup.chain.ProcessNothing;
import io.chark.undead_ninja_cop.engine.system.pickup.chain.ProcessPointsPickup;
import io.chark.undead_ninja_cop.engine.system.pickup.chain.TouchPickupEventChain;

import java.util.Arrays;
import java.util.Set;

public class PickupSystem extends BaseGameSystem {

    private static final String POINTS_TEXTURE_NAME = "points.png";
    private static final String HEALTH_TEXTURE_NAME = "health.png";

    private static final String POINTS_PICKUP_SOUND = "points.wav";
    private static final String HEALTH_PICKUP_SOUND = "health.wav";

    private final World world;

    private TouchPickupEventChain pickupEventChain;

    public PickupSystem(World world) {
        this.world = world;
    }

    @Override
    public void create() {
        this.pickupEventChain = createChain();
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
                Pickup pickup = new Pickup(event.getType() == Pickup.Type.POINTS ? 5 : 20, event.getType());
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

                    // Process taken pickup.
                    if (pickup == event.getPickup()) {

                        // Run through pickup event processing chain.
                        pickupEventChain.process(event);

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

    /**
     * Creates event chain for processing pickups.
     */
    private TouchPickupEventChain createChain() {
        Sound processPoints = resourceLoader.getSound(POINTS_PICKUP_SOUND);
        Sound healthSound = resourceLoader.getSound(HEALTH_PICKUP_SOUND);

        TouchPickupEventChain processNothing = new ProcessNothing();
        TouchPickupEventChain processHealth = new ProcessHealthPickup(healthSound, processNothing);
        return new ProcessPointsPickup(processPoints, processHealth);
    }
}