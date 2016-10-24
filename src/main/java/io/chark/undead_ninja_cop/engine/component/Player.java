package io.chark.undead_ninja_cop.engine.component;

import io.chark.undead_ninja_cop.core.Component;

public class Player implements Component {

    private boolean allowedToJump;

    public boolean isAllowedToJump() {
        return allowedToJump;
    }

    public void setAllowedToJump(boolean allowedToJump) {
        this.allowedToJump = allowedToJump;
    }
}