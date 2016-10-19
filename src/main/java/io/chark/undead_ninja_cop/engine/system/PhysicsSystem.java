package io.chark.undead_ninja_cop.engine.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.Physics;
import io.chark.undead_ninja_cop.engine.component.Transform;

import java.util.Set;

public class PhysicsSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Transform.class, Physics.class);

    private final World world;

    public PhysicsSystem() {
        this.world = new World(new Vector2(0, -10), true);
    }

    @Override
    public void updateEntities(float dt) {
        world.step(dt, 6, 2);
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}