package io.chark.undead_ninja_cop.core;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {

    private static final String SPRITE_PATH = "textures/";
    public static final String TEST_TEXTURE = "test.png";

    private final Map<String, Texture> textures = new HashMap<>();

    /**
     * Get texture by its name.
     *
     * @param name texture name.
     * @return texture.
     */
    public Texture getTexture(String name) {
        Texture texture = textures.get(name);
        if (texture == null) {
            texture = new Texture(SPRITE_PATH + name);
            textures.put(name, texture);
        }
        return texture;
    }
}