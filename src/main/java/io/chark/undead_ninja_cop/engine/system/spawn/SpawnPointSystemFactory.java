package io.chark.undead_ninja_cop.engine.system.spawn;

import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.GameSystem;
import io.chark.undead_ninja_cop.core.GameSystemFactory;

public class SpawnPointSystemFactory implements GameSystemFactory {

    private final World world;

    public SpawnPointSystemFactory(World world) {
        this.world = world;
    }

    @Override
    public GameSystem create() {
        return new SpawnPointSystem(world);
    }
}