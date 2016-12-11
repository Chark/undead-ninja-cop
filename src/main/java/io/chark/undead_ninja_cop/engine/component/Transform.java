package io.chark.undead_ninja_cop.engine.component;

import io.chark.undead_ninja_cop.core.Component;

/**
 * Location and size of a component.
 */
public class Transform implements Component {

    private final float scaleX;
    private final float scaleY;

    private float angle = 0;
    private float x;
    private float y;

    public Transform() {
        this(1, 1);
    }

    public Transform(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    /**
     * Get transform rotation angle in radians.
     *
     * @return rotation angle in radians.
     */
    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}