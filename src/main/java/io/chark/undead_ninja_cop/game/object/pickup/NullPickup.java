package io.chark.undead_ninja_cop.game.object.pickup;

import io.chark.undead_ninja_cop.game.object.GameObjectVisitor;

public class NullPickup implements Pickup {

    @Override
    public void accept(GameObjectVisitor visitor) {
    }
}