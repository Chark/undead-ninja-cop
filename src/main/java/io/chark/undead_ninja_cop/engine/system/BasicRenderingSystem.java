package io.chark.undead_ninja_cop.engine.system;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.chark.undead_ninja_cop.core.BaseGameSystem;
import io.chark.undead_ninja_cop.core.Component;
import io.chark.undead_ninja_cop.core.Entity;
import io.chark.undead_ninja_cop.core.util.Components;
import io.chark.undead_ninja_cop.engine.component.BasicRenderable;
import io.chark.undead_ninja_cop.engine.component.Transform;

import java.util.Set;

/**
 * Renders basic components.
 */
public class BasicRenderingSystem extends BaseGameSystem {

    private static final Set<Class<? extends Component>> TYPES = Components
            .toSet(Transform.class, BasicRenderable.class);

    private final SpriteBatch spriteBatch;

    public BasicRenderingSystem(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void renderEntities(float dt) {
        spriteBatch.begin();
        for (Entity entity : entities) {

            BasicRenderable basicRenderable = entityManager
                    .getComponent(entity, BasicRenderable.class);

            Transform transform = entityManager
                    .getComponent(entity, Transform.class);

            // todo use ppm here.
            spriteBatch.draw(basicRenderable.getTexture(),
                    transform.getX(),
                    transform.getY());
        }
        spriteBatch.end();
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}