package io.chark.undead_ninja_cop.engine.system.pickup;

import io.chark.undead_ninja_cop.core.event.Event;
import io.chark.undead_ninja_cop.engine.component.Pickup;
import io.chark.undead_ninja_cop.engine.component.player.Player;

public class TouchPickupEvent implements Event {

    private final Player player;
    private final Pickup pickup;

    public TouchPickupEvent(Player player, Pickup pickup) {
        this.player = player;
        this.pickup = pickup;
    }

    public Player getPlayer() {
        return player;
    }

    public Pickup getPickup() {
        return pickup;
    }
}