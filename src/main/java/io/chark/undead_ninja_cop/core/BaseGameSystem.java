package io.chark.undead_ninja_cop.core;

import io.chark.undead_ninja_cop.config.Configuration;
import io.chark.undead_ninja_cop.core.exception.GameException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Base implementation of entity system.
 */
public abstract class BaseGameSystem implements GameSystem {

    /**
     * Main game configuration settings.
     */
    protected final Configuration configuration = Configuration
            .getInstance();

    /**
     * Set of entities that this system works with.
     */
    protected final Set<Entity> entities = new HashSet<>();

    /**
     * Main resource loader for loading resources.
     */
    protected ResourceLoader resourceLoader;

    /**
     * Main entity manager.
     */
    protected EntityManager entityManager;

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
    public void init() {
    }

    @Override
    public void updateEntities(float dt) {
    }

    @Override
    public void renderEntities(float dt) {
    }

    /**
     * Inject main game entity manager,
     */
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Inject resource loader.
     */
    void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}