package io.chark.undead_ninja_cop.core.event;

public interface EventDispatcher {

    /**
     * Register a new event listener.
     *
     * @param listener event listener to register.
     */
    void register(EventListener<? extends Event> listener);

    /**
     * Dispatch a generic event.
     *
     * @param event event to dispatch.
     */
    void dispatch(Event event);
}