package io.chark.undead_ninja_cop.core;

import java.util.*;

/**
 * Base implementation of entity system.
 */
public abstract class BaseEntitySystem implements EntitySystem {

    /**
     * Set of entities that this system works with.
     */
    protected final Set<Entity> entities = new HashSet<>();

    /**
     * Main entity manager.
     */
    protected final EntityManager entityManager;

    public BaseEntitySystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void removeEntities() {
        entities.clear();
    }
}