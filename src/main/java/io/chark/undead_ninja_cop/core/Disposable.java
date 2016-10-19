package io.chark.undead_ninja_cop.core;

public interface Disposable {

    /**
     * Initialize all required components for the object to function.
     */
    void create();

    /**
     * Dispose the object clearing resources.
     */
    void dispose();
}