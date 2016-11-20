package io.chark.undead_ninja_cop.engine.system.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.event.EventListener;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.Transform;
import io.chark.undead_ninja_cop.engine.component.physics.Physics;
import io.chark.undead_ninja_cop.engine.component.player.DoubleJumpStrategy;
import io.chark.undead_ninja_cop.engine.component.player.JumpStrategy;
import io.chark.undead_ninja_cop.engine.component.player.Player;

import java.util.Set;

public class PlayerSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Transform.class, Physics.class, Player.class);

    private static final float WALK_IMPULSE = 0.01f;
    private static final float MAX_VELOCITY = 1f;

    @Override
    public void create() {

        // Listen when player touches ground.
        entityManager.register(new EventListener<PlayerTouchedGroundEvent>() {

            @Override
            public void onEvent(PlayerTouchedGroundEvent event) {
                event.getPlayer().setJumpStrategy(new DoubleJumpStrategy());
            }
        });
    }

    @Override
    public void updateEntities(float dt) {
        for (Entity entity : entities) {

            Player player = entityManager.getComponent(entity, Player.class);
            Body body = entityManager.getComponent(entity, Physics.class)
                    .getBody();

            Vector2 vel = body.getLinearVelocity();

            // Cap max velocity on x.
            if (Math.abs(vel.x) > MAX_VELOCITY) {
                vel.x = Math.signum(vel.x) * MAX_VELOCITY;
                body.setLinearVelocity(vel.x, vel.y);
            }

            // Walk around.
            Vector2 pos = body.getPosition();
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                body.applyLinearImpulse(-WALK_IMPULSE, 0, pos.x, pos.y, true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                body.applyLinearImpulse(WALK_IMPULSE, 0, pos.x, pos.y, true);
            }

            // Jumping.
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

                // Resulting jump strategy after jump.
                JumpStrategy result = player.getJumpStrategy().jump(body);

                // Override old strategy using the result.
                player.setJumpStrategy(result);
            }
        }
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}