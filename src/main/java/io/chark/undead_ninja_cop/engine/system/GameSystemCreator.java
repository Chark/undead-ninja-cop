package io.chark.undead_ninja_cop.engine.system;

import io.chark.undead_ninja_cop.core.EntityManager;
import io.chark.undead_ninja_cop.core.GameSystem;

public class GameSystemCreator {

    private final GameSystemFactory gameSystemFactory;
    private final EntityManager entityManager;

    public GameSystemCreator(GameSystemFactory gameSystemFactory,
                             EntityManager entityManager) {

        this.gameSystemFactory = gameSystemFactory;
        this.entityManager = entityManager;
    }

    public void create(Class<? extends GameSystem> type) {
        entityManager.addSystem(gameSystemFactory.create(type));
    }
}