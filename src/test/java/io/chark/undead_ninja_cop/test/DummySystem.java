package io.chark.undead_ninja_cop.test;

import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.EntityManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DummySystem extends BaseGameSystem {

    // Dummy system requires two components.
    private static final Set<Class<? extends Component>> TYPES =
            new HashSet<>(Arrays.asList(Dummy.class, Coordinate.class));

    public DummySystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected void update(Entity entity) {
        // In our tests the dummy system should never be used.
        throw new IllegalArgumentException("");
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}