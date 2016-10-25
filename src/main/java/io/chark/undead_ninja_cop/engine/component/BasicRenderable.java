package io.chark.undead_ninja_cop.engine.component;

import com.badlogic.gdx.graphics.Texture;
import io.chark.undead_ninja_cop.core.Component;

/**
 * A basic component that has enough data to be rendered.
 */
public class BasicRenderable implements Component {

    private final Texture texture;

    public BasicRenderable(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}