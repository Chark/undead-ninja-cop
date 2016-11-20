package io.chark.undead_ninja_cop.engine.component;

import com.badlogic.gdx.graphics.Texture;
import io.chark.undead_ninja_cop.core.Component;

/**
 * A basic component that has enough data to be rendered.
 */
public class BasicRenderable implements Component {

    private final Texture texture;
    private final float offsetX;
    private final float offsetY;

    public BasicRenderable(Texture texture) {
        this(texture, 0, 0);
    }

    public BasicRenderable(Texture texture, float offsetX, float offsetY) {
        this.texture = texture;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }
}