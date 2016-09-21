package io.chark.undead_ninja_cop.game.object.weapon;

import io.chark.undead_ninja_cop.game.object.GameObjectVisitor;

public class NullWeapon implements Weapon {

    @Override
    public void accept(GameObjectVisitor visitor) {
    }
}