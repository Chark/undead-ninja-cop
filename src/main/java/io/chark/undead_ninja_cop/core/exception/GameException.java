package io.chark.undead_ninja_cop.core.exception;

/**
 * Base game exception.
 */
public class GameException extends RuntimeException {

    public GameException(String message, Object... args) {
        super(String.format(message, args));
    }
}