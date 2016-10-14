package io.chark.undead_ninja_cop.core.exception;

public class EntityNotFoundException extends GameException {

    public EntityNotFoundException(String message, Object... args) {
        super(message, args);
    }
}