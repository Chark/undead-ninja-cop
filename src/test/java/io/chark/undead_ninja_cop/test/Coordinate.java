package io.chark.undead_ninja_cop.test;

import io.chark.undead_ninja_cop.core.Component;

/**
 * Component for testing.
 */
public class Coordinate implements Component {

    private float x;
    private float y;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
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