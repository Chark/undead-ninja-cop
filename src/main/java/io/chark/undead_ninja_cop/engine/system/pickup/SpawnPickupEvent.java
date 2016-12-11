package io.chark.undead_ninja_cop.engine.system.pickup;

import io.chark.undead_ninja_cop.core.event.Event;
import io.chark.undead_ninja_cop.engine.component.Pickup;

public class SpawnPickupEvent implements Event {

    private final float x;
    private final float y;
    private final Pickup.Type type;

    public SpawnPickupEvent(float x, float y, Pickup.Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Pickup.Type getType() {
        return type;
    }
}