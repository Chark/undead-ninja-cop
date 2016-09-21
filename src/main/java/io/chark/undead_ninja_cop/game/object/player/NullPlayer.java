package io.chark.undead_ninja_cop.game.object.player;

import io.chark.undead_ninja_cop.game.object.GameObjectVisitor;

public class NullPlayer implements Player {

    @Override
    public void accept(GameObjectVisitor visitor) {
    }
}