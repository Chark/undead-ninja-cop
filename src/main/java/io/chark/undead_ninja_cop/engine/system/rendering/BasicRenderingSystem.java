package io.chark.undead_ninja_cop.engine.system.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;

    public BasicRenderingSystem(OrthographicCamera camera,
                                SpriteBatch spriteBatch) {

        this.camera = camera;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void renderEntities(float dt) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for (Entity entity : entities) {

            BasicRenderable basicRenderable = entityManager
                    .getComponent(entity, BasicRenderable.class);

            Transform transform = entityManager
                    .getComponent(entity, Transform.class);

            Texture texture = basicRenderable.getTexture();

            // Scale texture size according to transform parameters.
            float width = texture.getWidth() * transform.getScaleX();
            float height = texture.getHeight() * transform.getScaleY();

            Sprite sprite = new Sprite(texture);
            sprite.setX(basicRenderable.getOffsetX() + transform.getX() - width / 2);
            sprite.setY(basicRenderable.getOffsetY() + transform.getY() - height / 2);
            sprite.setSize(width, height);
            sprite.rotate((float) Math.toDegrees(transform.getAngle()));

            sprite.setOrigin(width / 2, height / 2);

            sprite.draw(spriteBatch);

            // Is using this is less performance intensive than initializing sprites?
            // spriteBatch.draw(texture,
            //         basicRenderable.getOffsetX() + transform.getX() - width / 2,
            //         basicRenderable.getOffsetY() + transform.getY() - height / 2,
            //         width,
            //         height);
        }
        spriteBatch.end();
    }

    @Override
    public Set<Class<? extends Component>> getComponentTypes() {
        return TYPES;
    }
}