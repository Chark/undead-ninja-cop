package io.chark.undead_ninja_cop.game.level;

import io.chark.undead_ninja_cop.core.EntityManager;

public abstract class Level {

    private final EntityManager entityManager;

    Level(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}