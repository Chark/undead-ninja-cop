package io.chark.undead_ninja_cop.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Base implementation of entity system.
 */
public abstract class BaseGameSystem implements GameSystem {

    /**
     * Set of entities that this system works with.
     */
    protected final Set<Entity> entities = new HashSet<>();

    /**
     * Main entity manager.
     */
    protected final EntityManager entityManager;

    public BaseGameSystem(EntityManager entityManager) {
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

    @Override
    public void updateEntities() {
        preUpdate();
        entities.forEach(this::update);
        postUpdate();
    }

    /**
     * Hook method which is called before updating entities.
     */
    protected void preUpdate() {
    }

    /**
     * Hooke method which is called when updating the entities.
     *
     * @param entity entity which is being updated.
     */
    protected void update(Entity entity) {
    }

    /**
     * Hook method which is called after updating entities.
     */
    protected void postUpdate() {
    }
}