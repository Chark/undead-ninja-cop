package io.chark.undead_ninja_cop.game.object;

/**
 * Describes a dynamic game object, which can be drawn and interacted with.
 */
public interface GameObject {

    /**
     * Accept game object visitor and perform an action using that visitor.
     *
     * @param visitor visitor to perform the action with.
     */
    void accept(GameObjectVisitor visitor);
}