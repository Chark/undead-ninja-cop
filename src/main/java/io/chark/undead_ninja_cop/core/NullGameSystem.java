package io.chark.undead_ninja_cop.core;

import java.util.Collections;
import java.util.Set;

public class NullGameSystem implements GameSystem {

    @Override
    public void addEntity(Entity entity) {
    }

    @Override
    public void removeEntity(Entity entity) {
    }

    @Override
    public void removeEntities() {
    }

    @Override
    public void updateEntities(float dt) {
    }

    @Override
    public void renderEntities(float dt) {
    }

    @Override
    public void create() {
    }

    @Override
    public void setEnabled(boolean enabled) {
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return Collections.emptySet();
    }
}