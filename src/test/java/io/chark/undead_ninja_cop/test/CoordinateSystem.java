package io.chark.undead_ninja_cop.test;

import io.chark.undead_ninja_cop.core.BaseEntitySystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.EntityManager;

import java.util.*;

public class CoordinateSystem extends BaseEntitySystem {

    private static final Set<Class<? extends Component>> TYPES =
            new HashSet<>(Collections.singletonList(Coordinate.class));

    public CoordinateSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void updateEntities() {
        entities.forEach(e -> {
            Coordinate coordinates = entityManager.getComponent(e, Coordinate.class);
            coordinates.setX(0);
            coordinates.setY(0);
        });
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}