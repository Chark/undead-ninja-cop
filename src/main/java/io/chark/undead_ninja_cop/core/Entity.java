package io.chark.undead_ninja_cop.core;

import java.util.UUID;

/**
 * Wrapper for entity id.
 */
public class Entity {

    private final UUID id;

    Entity(UUID id) {
        this.id = id;
    }

    /**
     * Get unique entity id.
     *
     * @return entity id.
     */
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}