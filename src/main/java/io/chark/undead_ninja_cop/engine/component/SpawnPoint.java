package io.chark.undead_ninja_cop.engine.component;

import io.chark.undead_ninja_cop.core.Component;

public class SpawnPoint implements Component {

    public enum Type {
        ZOMBIE,
        PLAYER,
        WEAPON,
        HEALTH
    }

    private final Type type;

    public SpawnPoint(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}