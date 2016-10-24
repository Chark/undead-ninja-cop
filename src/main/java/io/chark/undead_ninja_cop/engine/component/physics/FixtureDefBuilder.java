package io.chark.undead_ninja_cop.engine.component.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.chark.undead_ninja_cop.core.config.Configuration;

public final class FixtureDefBuilder {

    private static final float MPP = Configuration
            .getInstance()
            .getGameplay()
            .getMpp();

    private float density = 0;
    private float radius = 0;

    private float width = 0;
    private float height = 0;

    private float x = 0;
    private float y = 0;

    private FixtureDefBuilder() {
    }

    public FixtureDefBuilder position(float x, float y) {
        this.x = x * MPP;
        this.y = y * MPP;
        return this;
    }

    public FixtureDefBuilder dimensions(float width, float height) {
        this.width = width * MPP;
        this.height = height * MPP;
        return this;
    }

    public FixtureDefBuilder density(float density) {
        this.density = density;
        return this;
    }

    public FixtureDefBuilder radius(float radius) {
        this.radius = radius * MPP;
        return this;
    }

    public FixtureDef build(Shape.Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;

        Shape shape;
        switch (type) {
            case Circle:
                shape = getCircle();
                break;
            case Polygon:
                shape = getPolygon();
                break;
            default:
                throw new IllegalArgumentException("Unsupported shape type");
        }

        fixtureDef.shape = shape;
        return fixtureDef;
    }

    public static FixtureDefBuilder builder() {
        return new FixtureDefBuilder();
    }

    private Shape getCircle() {
        CircleShape circleShape = new CircleShape();
        circleShape.setPosition(new Vector2(x, y));
        circleShape.setRadius(radius);
        return circleShape;
    }

    private Shape getPolygon() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);
        return polygonShape;
    }
}