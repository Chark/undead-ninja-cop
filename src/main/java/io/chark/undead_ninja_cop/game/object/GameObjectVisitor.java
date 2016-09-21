package io.chark.undead_ninja_cop.game.object;

import io.chark.undead_ninja_cop.game.object.pickup.Pickup;
import io.chark.undead_ninja_cop.game.object.player.Player;
import io.chark.undead_ninja_cop.game.object.weapon.Weapon;
import io.chark.undead_ninja_cop.game.object.zombie.Zombie;

public interface GameObjectVisitor {

    void visit(GameObject gameObject);

    void visit(Weapon weapon);

    void visit(Player player);

    void visit(Zombie zombie);

    void visit(Pickup item);
}