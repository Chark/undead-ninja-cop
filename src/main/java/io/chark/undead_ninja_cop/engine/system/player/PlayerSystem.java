package io.chark.undead_ninja_cop.engine.system.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.Physics;
import io.chark.undead_ninja_cop.engine.component.Player;
import io.chark.undead_ninja_cop.engine.component.Transform;

import java.util.Set;

public class PlayerSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Transform.class, Physics.class, Player.class);

    private static final float WALK_IMPULSE = 0.01f;
    private static final float JUMP_IMPULSE = 0.1f;
    private static final float MAX_VELOCITY = 1f;

    private final World world;

    public PlayerSystem(World world) {
        this.world = world;
    }

    @Override
    public void updateEntities(float dt) {
        for (Entity entity : entities) {

            Player player = entityManager.getComponent(entity, Player.class);
            Body body = entityManager.getComponent(entity, Physics.class)
                    .getBody();

            player.setAllowedToJump(true);
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
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.isAllowedToJump()) {
                body.setLinearVelocity(vel.x, 0);
                body.setTransform(pos.x, pos.y + JUMP_IMPULSE, 0);
                body.applyLinearImpulse(0, JUMP_IMPULSE, pos.x, pos.y, true);
            }
        }
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}