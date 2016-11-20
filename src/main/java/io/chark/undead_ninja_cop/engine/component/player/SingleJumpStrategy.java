package io.chark.undead_ninja_cop.engine.component.player;

import com.badlogic.gdx.physics.box2d.Body;

public class SingleJumpStrategy extends DoubleJumpStrategy {

    @Override
    public JumpStrategy jump(Body body) {
        super.jump(body);
        return new NoJumpStrategy();
    }
}