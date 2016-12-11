package io.chark.undead_ninja_cop.engine.component;

import io.chark.undead_ninja_cop.core.Component;

public class Pickup implements Component {

    public enum Type {
        HEALTH,
        POINTS
    }

    private final float value;
    private final Type type;

    public Pickup(float value, Type type) {
        this.value = value;
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }
}