package io.chark.undead_ninja_cop.core;

import com.badlogic.gdx.Gdx;
import io.chark.undead_ninja_cop.core.event.Event;
import io.chark.undead_ninja_cop.core.event.EventListener;
import io.chark.undead_ninja_cop.core.exception.EntityNotFoundException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class GameEntityManager implements EntityManager {

    /**
     * Map of all available entities and their components.
     */
    private final Map<Entity, Map<Class<? extends Component>, Component>> entities =
            new HashMap<>();

    /**
     * Map of available entity systems.
     */
    private final Map<Class<? extends GameSystem>, GameSystem> systems =
            new LinkedHashMap<>();

    /**
     * Map of event listeners.
     */
    private final Map<Class<? extends Event>, Collection<EventListener>> listeners =
            new LinkedHashMap<>();

    /**
     * Base game resource loader.
     */
    private final ResourceLoader resourceLoader;

    public GameEntityManager(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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
        systems.values()
                .stream()
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
        systems.values()
                .stream()
                .filter(s -> removed.keySet().containsAll(s.getComponentTypes()))
                .forEach(s -> s.removeEntity(entity));
    }

    @Override
    public void removeEntities() {
        entities.clear();
        systems.values()
                .forEach(GameSystem::removeEntities);
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
    public void addSystem(GameSystem system) {
        if (system == null) {
            throw new IllegalArgumentException("Entity system must not be null");
        }

        // This is a non-custom system, inject some common objects.
        if (system instanceof BaseGameSystem) {
            BaseGameSystem base = ((BaseGameSystem) system);
            base.setEntityManager(this);
            base.setResourceLoader(resourceLoader);
        }
        systems.put(system.getClass(), system);
        system.create();
    }

    @Override
    public <T extends GameSystem> T getSystem(Class<T> type) {
        return type.cast(systems.get(type));
    }

    @Override
    public void updateSystems() {
        float dt = Gdx.graphics.getDeltaTime();

        for (GameSystem system : systems.values()) {
            if (system.isEnabled()) {
                system.updateEntities(dt);
            }
        }
    }

    @Override
    public void renderSystems() {
        float dt = Gdx.graphics.getDeltaTime();

        for (GameSystem system : systems.values()) {
            if (system.isEnabled()) {
                system.renderEntities(dt);
            }
        }
    }

    @Override
    public int getEntityCount() {
        return entities.size();
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

    @Override
    @SuppressWarnings("unchecked")
    public void dispatch(Event event) {

        // Notify all listeners interested in this event.
        for (EventListener listener : listeners.getOrDefault(
                event.getClass(),
                Collections.emptyList())) {

            listener.onEvent(event);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void register(EventListener<? extends Event> listener) {
        for (Type type : listener.getClass().getGenericInterfaces()) {

            if (type instanceof ParameterizedType) {
                ParameterizedType parametrized = (ParameterizedType) type;

                // Check if parameter is of required type.
                if (EventListener.class.isAssignableFrom((Class) parametrized.getRawType())) {
                    Class<Event> eventType = (Class<Event>) parametrized
                            .getActualTypeArguments()[0];

                    // Parameter type matches, register listener.
                    Collection<EventListener> specificListeners = listeners.get(eventType);
                    if (specificListeners == null) {
                        specificListeners = new ArrayList<>();
                        listeners.put(eventType, specificListeners);
                    }
                    specificListeners.add(listener);
                }
            }
        }
    }
}