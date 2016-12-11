package io.chark.undead_ninja_cop.engine.system.pickup;

import io.chark.undead_ninja_cop.core.event.Event;
import io.chark.undead_ninja_cop.engine.component.Pickup;

public class TouchPickupEvent implements Event {

    private final Pickup pickup;

    public TouchPickupEvent(Pickup pickup) {
        this.pickup = pickup;
    }

    public Pickup getPickup() {
        return pickup;
    }
}