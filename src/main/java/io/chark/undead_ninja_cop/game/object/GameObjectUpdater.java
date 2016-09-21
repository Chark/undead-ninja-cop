package io.chark.undead_ninja_cop.game.object;

import io.chark.undead_ninja_cop.game.object.pickup.Pickup;
import io.chark.undead_ninja_cop.game.object.player.Player;
import io.chark.undead_ninja_cop.game.object.weapon.Weapon;
import io.chark.undead_ninja_cop.game.object.zombie.Zombie;

/**
 * Updates game object states.
 */
public class GameObjectUpdater implements GameObjectVisitor {

    @Override
    public void visit(GameObject gameObject) {
    }

    @Override
    public void visit(Weapon weapon) {
    }

    @Override
    public void visit(Player player) {
    }

    @Override
    public void visit(Zombie zombie) {
    }

    @Override
    public void visit(Pickup item) {
    }
}