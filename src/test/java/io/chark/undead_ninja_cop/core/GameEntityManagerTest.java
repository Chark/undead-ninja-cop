package io.chark.undead_ninja_cop.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import io.chark.undead_ninja_cop.core.event.EventListener;
import io.chark.undead_ninja_cop.core.exception.EntityNotFoundException;
import io.chark.undead_ninja_cop.test.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class GameEntityManagerTest {

    private static final String INVALID_COMPONENTS_ERROR = "Components must not be null or empty";
    private static final String ENTITY_NOT_FOUND_ERROR = "Entity is deleted";
    private static final String NULL_ENTITY_ERROR = "Entity must not be null";
    private static final String NULL_SYSTEM_ERROR = "Entity system must not be null";

    private static final float X = 10;
    private static final float Y = 10;

    private GameEntityManager entityManager;

    @Before
    public void setUp() {
        entityManager = new GameEntityManager(null);

        entityManager.addSystem(new CoordinateSystem());
        entityManager.addSystem(new DummySystem());

        Graphics graphics = Mockito.mock(Graphics.class);
        when(graphics.getDeltaTime()).thenReturn(0.9f);

        Gdx.graphics = graphics;
    }

    @After
    public void tearDown() {
        Gdx.graphics = null;
    }

    @Test
    public void createEntity() {
        Entity entity = entityManager.createEntity(create());
        assertThat(entity.getId()).isNotNull();
    }

    @Test
    public void createEntityInvalidComponents() {
        assertThatThrownBy(() -> entityManager.createEntity(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_COMPONENTS_ERROR);

        assertThatThrownBy(() -> entityManager.createEntity(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_COMPONENTS_ERROR);
    }

    @Test
    public void createEntityGetComponents() {
        Entity entity = entityManager.createEntity(create());

        Collection<Component> components = entityManager.getComponents(entity);

        Coordinate coordinate = (Coordinate) components
                .stream().findFirst().get();

        assertThat(coordinate.getX()).isEqualTo(X);
        assertThat(coordinate.getY()).isEqualTo(Y);
    }

    @Test
    public void createEntityGetComponent() {
        Entity entity = entityManager.createEntity(create());

        Coordinate coordinate = entityManager.getComponent(entity, Coordinate.class);

        assertThat(coordinate.getX()).isEqualTo(X);
        assertThat(coordinate.getY()).isEqualTo(Y);
    }

    @Test
    public void addSystemAndUpdateEntities() {
        Coordinate coordinate = entityManager.getComponent(entityManager
                .createEntity(create()), Coordinate.class);

        entityManager.updateSystems();

        assertThat(coordinate.getX()).isNotEqualTo(X);
        assertThat(coordinate.getY()).isNotEqualTo(Y);
    }

    @Test
    public void addNullSystem() {
        assertThatThrownBy(() -> entityManager.addSystem(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NULL_SYSTEM_ERROR);
    }

    @Test
    public void removeEntity() {
        Entity entity = entityManager.createEntity(Arrays.asList(
                new Coordinate(X, Y),
                new Dummy()));

        entityManager.removeEntity(entity);
        entityManager.updateSystems();

        assertThatThrownBy(() -> entityManager.getComponent(entity, Coordinate.class))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ENTITY_NOT_FOUND_ERROR);

        assertThatThrownBy(() -> entityManager.getComponent(entity, Dummy.class))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ENTITY_NOT_FOUND_ERROR);
    }

    @Test
    public void addNullEntity() {
        assertThatThrownBy(() -> entityManager.removeEntity(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NULL_ENTITY_ERROR);
    }

    @Test
    public void removeEntities() {
        Entity entity = entityManager.createEntity(Arrays.asList(
                new Coordinate(X, Y),
                new Dummy()));

        entityManager.removeEntities();
        entityManager.updateSystems();

        assertThatThrownBy(() -> entityManager.getComponent(entity, Coordinate.class))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ENTITY_NOT_FOUND_ERROR);

        assertThatThrownBy(() -> entityManager.getComponent(entity, Dummy.class))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(ENTITY_NOT_FOUND_ERROR);
    }

    @Test
    public void eventDispatching() {

        AtomicReference<AnotherEvent> anotherEventReference = new AtomicReference<>();
        entityManager.register(new EventListener<AnotherEvent>() {

            @Override
            public void onEvent(AnotherEvent event) {
                anotherEventReference.set(event);
            }
        });

        BlankEventListener blankEventListener = new BlankEventListener();
        entityManager.register(blankEventListener);
        entityManager.updateSystems();

        assertThat(anotherEventReference.get()).isNotNull();
        assertThat(blankEventListener.getEvent()).isNotNull();
    }

    private Collection<Component> create() {
        return Collections.singletonList(new Coordinate(X, Y));
    }

    private static class BlankEventListener implements EventListener<BlankEvent> {

        private BlankEvent event;

        @Override
        public void onEvent(BlankEvent event) {
            this.event = event;
        }

        BlankEvent getEvent() {
            return event;
        }
    }
}