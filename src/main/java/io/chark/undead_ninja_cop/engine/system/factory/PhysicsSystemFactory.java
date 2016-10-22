package io.chark.undead_ninja_cop.engine.system.factory;

import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.GameSystem;
import io.chark.undead_ninja_cop.core.GameSystemFactory;
import io.chark.undead_ninja_cop.engine.system.PhysicsSystem;

public class PhysicsSystemFactory implements GameSystemFactory {

    private final World world;

    public PhysicsSystemFactory(World world) {
        this.world = world;
    }

    @Override
    public GameSystem create() {
        return new PhysicsSystem(world);
    }
}