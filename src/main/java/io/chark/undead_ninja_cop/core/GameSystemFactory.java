package io.chark.undead_ninja_cop.core;

public interface GameSystemFactory {

    /**
     * Creates a game system instance.
     *
     * @return game system instance.
     */
    GameSystem create();
}