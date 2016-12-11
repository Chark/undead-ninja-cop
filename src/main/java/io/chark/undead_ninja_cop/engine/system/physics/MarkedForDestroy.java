package io.chark.undead_ninja_cop.engine.system.physics;

public class MarkedForDestroy {

    private static final MarkedForDestroy INSTANCE = new MarkedForDestroy();

    private MarkedForDestroy() {
    }

    public static MarkedForDestroy getInstance() {
        return INSTANCE;
    }
}