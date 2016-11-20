package io.chark.undead_ninja_cop.engine.component.player;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Determines how a body should jump.
 */
public interface JumpStrategy {

    float JUMP_IMPULSE = 0.1f;

    /**
     * Make the body jump.
     *
     * @param body body which should jump.
     * @return next jump strategy, after the jumping has occurred.
     */
    JumpStrategy jump(Body body);
}