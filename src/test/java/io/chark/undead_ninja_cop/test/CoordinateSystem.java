package io.chark.undead_ninja_cop.test;

import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.util.Components;

import java.util.Set;

/**
 * Component system for testing.
 */
public class CoordinateSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Coordinate.class);

    @Override
    public void updateEntities(float dt) {
        for (Entity entity : entities) {
            Coordinate coordinates = entityManager.getComponent(entity, Coordinate.class);
            coordinates.setX(0);
            coordinates.setY(0);
        }
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}