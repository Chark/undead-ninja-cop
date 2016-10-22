package io.chark.undead_ninja_cop.core;

import io.chark.undead_ninja_cop.core.config.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Base implementation of entity system.
 */
public abstract class BaseGameSystem implements GameSystem {

    /**
     * Main game configuration settings.
     */
    protected static final Configuration CONFIG = Configuration
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

    /**
     * Is this system enabled or not.
     */
    protected boolean enabled = true;

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
    public void updateEntities(float dt) {
    }

    @Override
    public void renderEntities(float dt) {
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return Collections.emptySet();
    }

    @Override
    public void create() {
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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