package io.chark.undead_ninja_cop.test;

import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.EntityManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Component system for testing.
 */
public class CoordinateSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES =
            new HashSet<>(Collections.singletonList(Coordinate.class));

    public CoordinateSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected void update(Entity entity) {
        Coordinate coordinates = entityManager.getComponent(entity, Coordinate.class);
        coordinates.setX(0);
        coordinates.setY(0);
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}