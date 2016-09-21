package io.chark.undead_ninja_cop.game.object.zombie;

import io.chark.undead_ninja_cop.game.object.GameObjectVisitor;

public class NullZombie implements Zombie {

    @Override
    public void accept(GameObjectVisitor visitor) {
    }

    @Override
    public void navigate() {
    }
}