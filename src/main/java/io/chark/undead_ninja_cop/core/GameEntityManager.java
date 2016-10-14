package io.chark.undead_ninja_cop.core;

import io.chark.undead_ninja_cop.core.exception.EntityNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class GameEntityManager implements EntityManager {

    /**
     * Map of all available entities and their components.
     */
    private final Map<Entity, Map<Class<? extends Component>, Component>> entities = new HashMap<>();

    /**
     * Collection of available entity systems.
     */
    private final Collection<EntitySystem> systems = new ArrayList<>();

    @Override
    public Entity createEntity(Collection<Component> components) {
        if (components == null || components.isEmpty()) {
            throw new IllegalArgumentException("Components must not be null or empty");
        }

        Entity entity = createEntity();

        // Need to know used component types for this entity.
        Map<Class<? extends Component>, Component> created = components
                .stream()
                .collect(Collectors.toMap(Component::getClass, e -> e));

        // Add entity to entity list and to systems that work with it.
        entities.put(entity, created);
        systems.stream()
                .filter(s -> created.keySet().containsAll(s.getComponentTypes()))
                .forEach(s -> s.addEntity(entity));

        return entity;
    }

    @Override
    public void removeEntity(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }

        // Remove entity from main entity list and get it's component types.
        Map<Class<? extends Component>, Component> removed = entities.remove(entity);

        // Remove entity from systems that work with it.
        systems.stream()
                .filter(s -> removed.keySet().containsAll(s.getComponentTypes()))
                .forEach(s -> s.removeEntity(entity));
    }

    @Override
    public void removeEntities() {
        entities.clear();
        systems.forEach(EntitySystem::removeEntities);
    }

    @Override
    public Collection<Component> getComponents(Entity entity) {
        return entities.get(entity).values();
    }

    @Override
    public <T extends Component> T getComponent(Entity entity, Class<T> type) {
        Map<Class<? extends Component>, Component> components = entities.get(entity);
        if (components == null) {
            throw new EntityNotFoundException("Entity is deleted");
        }
        return type.cast(components.get(type));
    }

    @Override
    public void addSystem(EntitySystem system) {
        if (system == null) {
            throw new IllegalArgumentException("Entity system must not be null");
        }
        systems.add(system);
    }

    @Override
    public void updateSystems() {
        systems.forEach(EntitySystem::updateEntities);
    }

    /**
     * Generates an instance of a valid entity.
     *
     * @return new entity instance.
     */
    private Entity createEntity() {
        Entity entity = new Entity(UUID.randomUUID());

        // Super low chance to get a duplicate.
        while (entities.containsKey(entity)) {
            entity = new Entity(UUID.randomUUID());
        }
        return entity;
    }
}