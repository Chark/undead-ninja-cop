package io.chark.undead_ninja_cop.game.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.game.object.pickup.Pickup;
import io.chark.undead_ninja_cop.game.object.player.Player;
import io.chark.undead_ninja_cop.game.object.weapon.Weapon;
import io.chark.undead_ninja_cop.game.object.zombie.Zombie;

/**
 * Draws game objects. Should be called within scope of <code>sb.start()</code> and <code>sb.end()</code>.
 */
public class GameObjectRenderer implements GameObjectVisitor {

    private final SpriteBatch spriteBatch;

    public GameObjectRenderer(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

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