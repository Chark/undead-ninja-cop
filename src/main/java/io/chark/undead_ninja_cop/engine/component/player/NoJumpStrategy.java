package io.chark.undead_ninja_cop.engine.component.player;

import com.badlogic.gdx.physics.box2d.Body;

public class NoJumpStrategy implements JumpStrategy {

    @Override
    public JumpStrategy jump(Body body) {
        return this;
    }
}