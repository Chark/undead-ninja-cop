package io.chark.undead_ninja_cop.core;

import io.chark.undead_ninja_cop.core.exception.EntityNotFoundException;
import io.chark.undead_ninja_cop.test.Coordinate;
import io.chark.undead_ninja_cop.test.CoordinateSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class GameEntityManagerTest {

    private static final float X = 10;
    private static final float Y = 10;


    private GameEntityManager entityManager;
    private CoordinateSystem system;

    @Before
    public void setUp() {
        entityManager = new GameEntityManager();
        system = new CoordinateSystem(entityManager);
    }

    @Test
    public void createEntity() {
        Entity entity = entityManager.createEntity(create());
        assertThat(entity.getId()).isNotNull();
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
        entityManager.addSystem(system);

        Coordinate coordinate = entityManager.getComponent(entityManager
                .createEntity(create()), Coordinate.class);

        entityManager.updateSystems();

        assertThat(coordinate.getX()).isNotEqualTo(X);
        assertThat(coordinate.getY()).isNotEqualTo(Y);
    }

    @Test
    public void removeEntity() {
        Entity entity = entityManager.createEntity(create());

        entityManager.removeEntity(entity);
        assertThatThrownBy(() -> entityManager.getComponent(entity, Coordinate.class))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void removeEntities() {
        Entity entity = entityManager.createEntity(create());

        entityManager.removeEntities();
        assertThatThrownBy(() -> entityManager.getComponent(entity, Coordinate.class))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private Collection<Component> create() {
        return Collections.singletonList(new Coordinate(X, Y));
    }
}