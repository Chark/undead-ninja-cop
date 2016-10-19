package io.chark.undead_ninja_cop.engine.component;

import com.badlogic.gdx.physics.box2d.BodyDef;
import io.chark.undead_ninja_cop.core.Component;

public class Physics implements Component {

    private final BodyDef bodyDef;

    public Physics(BodyDef bodyDef) {
        this.bodyDef = bodyDef;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }
}