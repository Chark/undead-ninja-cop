package io.chark.undead_ninja_cop.engine.component.player;

import io.chark.undead_ninja_cop.core.Component;

public class Player implements Component {

    // Don't know where to put this.
    public enum Part {
        FEET,
        BODY
    }

    private JumpStrategy jumpStrategy = new DoubleJumpStrategy();
    private int points = 0;
    private int health = 10;

    public JumpStrategy getJumpStrategy() {
        return jumpStrategy;
    }

    public void setJumpStrategy(JumpStrategy jumpStrategy) {
        this.jumpStrategy = jumpStrategy;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}