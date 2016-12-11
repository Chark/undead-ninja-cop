package io.chark.undead_ninja_cop.engine.system.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.Transform;
import io.chark.undead_ninja_cop.engine.component.physics.Physics;
import io.chark.undead_ninja_cop.engine.component.player.Player;
import io.chark.undead_ninja_cop.engine.system.player.PlayerTouchedGroundEvent;
import io.chark.undead_ninja_cop.util.SimpleContactListener;

import java.util.Set;

public class PhysicsSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Transform.class, Physics.class);

    private final World world;

    public PhysicsSystem(World world) {
        this.world = world;
    }

    @Override
    public void create() {
        world.setContactListener(new SimpleContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Object userData = contact.getFixtureB().getUserData();

                // If the receiving object that just touched some other object is a player,
                // forward this event to other systems.
                if (userData instanceof Player) {
                    entityManager.dispatch(new PlayerTouchedGroundEvent(((Player) userData)));
                }
            }
        });
    }

    @Override
    public void updateEntities(float dt) {
        world.step(dt, 6, 2);

        float ppm = CONFIG.getGameplay().getPpm();
        for (Entity entity : entities) {
            Transform transform = entityManager.getComponent(entity, Transform.class);

            Body body = entityManager
                    .getComponent(entity, Physics.class)
                    .getBody();

            Vector2 pos = body.getPosition();

            transform.setX(pos.x * ppm);
            transform.setY(pos.y * ppm);
        }
    }

    @Override
    public void removeEntities() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            world.destroyBody(body);
        }
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}