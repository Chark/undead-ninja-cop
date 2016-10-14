package io.chark.undead_ninja_cop.core;

import java.util.Set;

/**
 * Works with entities which have a specific set of components.
 */
public interface EntitySystem {

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
     */
    void updateEntities();

    /**
     * Get set of component types which are used in this entity system.
     *
     * @return set of component types.
     */
    Set<Class<? extends Component>> getComponentTypes();
}