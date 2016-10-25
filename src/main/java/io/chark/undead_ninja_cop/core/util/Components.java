package io.chark.undead_ninja_cop.core.util;

import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.exception.GameException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to work with components.
 */
public final class Components {

    /**
     * Create a set from component type array.
     *
     * @param types component type array.
     * @return component type set.
     */
    @SafeVarargs
    public static Set<Class<? extends Component>> toSet(Class<? extends Component>... types) {
        if (types == null) {
            throw new GameException("Type array must not be null");
        }
        return new HashSet<>(Arrays.asList(types));
    }
}