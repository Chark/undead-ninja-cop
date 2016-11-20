package io.chark.undead_ninja_cop.engine.component.physics;

import com.badlogic.gdx.physics.box2d.*;
import io.chark.undead_ninja_cop.core.config.Configuration;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public final class PhysicsBuilder {

    private static final float MPP = Configuration
            .getInstance()
            .getGameplay()
            .getMpp();

    private final List<Pair<Shape, Float>> shapePairs = new ArrayList<>();
    private final List<Pair<Object, FixtureDef>> fixtureDetails = new ArrayList<>();

    private final World world;

    private BodyDef.BodyType type = BodyDef.BodyType.StaticBody;

    private boolean fixedRotation = false;
    private boolean bullet = false;

    private float x = 0;
    private float y = 0;

    private Object userData;

    private PhysicsBuilder(World world) {
        this.world = world;
    }

    public PhysicsBuilder dynamic() {
        this.type = BodyDef.BodyType.DynamicBody;
        return this;
    }

    public PhysicsBuilder fixedRotation() {
        this.fixedRotation = true;
        return this;
    }

    public PhysicsBuilder bullet() {
        this.bullet = true;
        return this;
    }

    public PhysicsBuilder position(float x, float y) {
        this.x = x * MPP;
        this.y = y * MPP;
        return this;
    }

    public PhysicsBuilder addFixture(FixtureDef fixtureDef) {
        return addFixture(null, fixtureDef);
    }

    public PhysicsBuilder addFixture(Object userData, FixtureDef fixtureDef) {
        this.fixtureDetails.add(Pair.create(userData, fixtureDef));
        return this;
    }

    public PhysicsBuilder userData(Object userData) {
        this.userData = userData;
        return this;
    }

    public PhysicsBuilder shape(Shape shape, float density) {
        this.shapePairs.add(new Pair<>(shape, density));
        return this;
    }

    public Physics build() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);
        body.setFixedRotation(fixedRotation);
        body.setBullet(bullet);
        body.setUserData(userData);

        for (Pair<Object, FixtureDef> pair : fixtureDetails) {
            FixtureDef fixtureDef = pair.getValue();
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData(pair.getKey());

            fixtureDef.shape.dispose();
        }

        for (Pair<Shape, Float> pair : shapePairs) {
            body.createFixture(pair.getFirst(), pair.getSecond());
        }
        return new Physics(body);
    }

    public static PhysicsBuilder usingWorld(World world) {
        return new PhysicsBuilder(world);
    }
}