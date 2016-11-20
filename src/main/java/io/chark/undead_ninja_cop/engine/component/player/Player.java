package io.chark.undead_ninja_cop.engine.component.player;

import io.chark.undead_ninja_cop.core.Component;

public class Player implements Component {

    private JumpStrategy jumpStrategy = new DoubleJumpStrategy();

    public JumpStrategy getJumpStrategy() {
        return jumpStrategy;
    }

    public void setJumpStrategy(JumpStrategy jumpStrategy) {
        this.jumpStrategy = jumpStrategy;
    }
}