package io.chark.undead_ninja_cop.core;

import java.util.Set;

/**
 * Works with entities which have a specific set of components.
 */
public interface GameSystem {

    /**
     * Add entity to entity system.
     *
     * @param entity entity to add.
     */
    void addEntity(Entity entity);

    /**
     * Remove entity from entity system.
     *
     * @param entity entity to remove.
     */
    void removeEntity(Entity entity);

    /**
     * Remove all entities from entity system.
     */
    void removeEntities();

    /**
     * Update all entities on entity system.
     *
     * @param dt delta time.
     */
    void updateEntities(float dt);

    /**
     * Render all entities on entity system.
     *
     * @param dt delta time.
     */
    void renderEntities(float dt);

    /**
     * Called after this game system has been fully constructed.
     */
    void create();

    /**
     * Enable or disable this game system.
     *
     * @param enabled should the system be enabled or disabled.
     */
    void setEnabled(boolean enabled);

    /**
     * Is this game system enabled.
     *
     * @return true if the game system is enabled or false otherwise.
     */
    boolean isEnabled();

    /**
     * Get set of component types which are used in this entity system.
     *
     * @return set of component types.
     */
    Set<Class<? extends Component>> getComponentTypes();
}