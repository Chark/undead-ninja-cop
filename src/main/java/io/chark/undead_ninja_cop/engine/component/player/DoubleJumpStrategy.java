package io.chark.undead_ninja_cop.engine.component.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


public class DoubleJumpStrategy implements JumpStrategy {

    @Override
    public JumpStrategy jump(Body body) {
        Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();

        body.setLinearVelocity(vel.x, 0);
        body.setTransform(pos.x, pos.y + JUMP_IMPULSE, 0);
        body.applyLinearImpulse(0, JUMP_IMPULSE, pos.x, pos.y, true);

        return new SingleJumpStrategy();
    }
}