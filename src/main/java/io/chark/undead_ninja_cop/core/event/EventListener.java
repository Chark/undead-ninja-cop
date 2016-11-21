package io.chark.undead_ninja_cop.core.event;

@FunctionalInterface
public interface EventListener<T extends Event> {

    /**
     * Receive an event of a specific type.
     *
     * @param event event from another or the same system.
     */
    void onEvent(T event);
}