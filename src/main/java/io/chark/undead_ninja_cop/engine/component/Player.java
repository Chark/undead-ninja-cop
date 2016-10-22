package io.chark.undead_ninja_cop.engine.component;

import com.badlogic.gdx.physics.box2d.Fixture;
import io.chark.undead_ninja_cop.core.Component;

public class Player implements Component {

    private final Fixture physicsFixture;
    private final Fixture sensorFixture;

    private boolean allowedToJump;

    public Player(Fixture physicsFixture,
                  Fixture sensorFixture) {

        this.physicsFixture = physicsFixture;
        this.sensorFixture = sensorFixture;
    }

    public Fixture getPhysicsFixture() {
        return physicsFixture;
    }

    public Fixture getSensorFixture() {
        return sensorFixture;
    }

    public boolean isAllowedToJump() {
        return allowedToJump;
    }

    public void setAllowedToJump(boolean allowedToJump) {
        this.allowedToJump = allowedToJump;
    }
}