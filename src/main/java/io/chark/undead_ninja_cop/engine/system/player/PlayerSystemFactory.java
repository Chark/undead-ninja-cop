package io.chark.undead_ninja_cop.engine.system.player;

import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.GameSystem;
import io.chark.undead_ninja_cop.core.GameSystemFactory;

public class PlayerSystemFactory implements GameSystemFactory{

    private final World world;

    public PlayerSystemFactory(World world) {
        this.world = world;
    }

    @Override
    public GameSystem create() {
        return new PlayerSystem(world);
    }
}