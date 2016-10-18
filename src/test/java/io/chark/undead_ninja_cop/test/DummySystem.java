package io.chark.undead_ninja_cop.test;

import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.util.Components;

import java.util.Set;

public class DummySystem extends BaseGameSystem {

    // Dummy system requires two components.
    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Dummy.class, Coordinate.class);

    @Override
    public void updateEntities(float dt) {
        if (entities.isEmpty()) {
            return;
        }

        // In our tests the dummy system entities should never be used.
        throw new IllegalArgumentException("");
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}