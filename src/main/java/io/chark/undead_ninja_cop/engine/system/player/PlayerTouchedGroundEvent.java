package io.chark.undead_ninja_cop.engine.system.player;

import io.chark.undead_ninja_cop.core.event.Event;
import io.chark.undead_ninja_cop.engine.component.player.Player;

/**
 * Sent when a player touches the ground.
 */
public class PlayerTouchedGroundEvent implements Event {

    private final Player player;

    public PlayerTouchedGroundEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}