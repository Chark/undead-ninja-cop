package io.chark.undead_ninja_cop.core.exception;

/**
 * Thrown when entity is not found.
 */
public class EntityNotFoundException extends GameException {

    public EntityNotFoundException(String message, Object... args) {
        super(message, args);
    }
}