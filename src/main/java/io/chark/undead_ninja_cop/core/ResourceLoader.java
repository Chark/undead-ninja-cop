package io.chark.undead_ninja_cop.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.core.config.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);

    private final Settings settings = Configuration
            .getInstance()
            .getSettings();

    public static final String FONT_KONG_TEXT = "kong_text.ttf";
    public static final int FONT_DEFAULT_SIZE = 16;

    public static final String TEST_TEXTURE = "test.png";

    private final Map<String, Texture> textures = new HashMap<>();
    private final Map<String, BitmapFont> fonts = new HashMap<>();

    public BitmapFont getDefaultFont() {
        return getFont(FONT_KONG_TEXT, FONT_DEFAULT_SIZE);
    }

    public BitmapFont getFont(String name, int size) {
        String nameWithSize = String.format("%d_%s", size, name);

        BitmapFont font = fonts.get(nameWithSize);
        if (font == null) {
            LOGGER.debug("Loading Font: {}", nameWithSize);

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx
                    .files.internal(settings.getFontDirectory() + name));

            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = size;

            font = generator.generateFont(parameter);
            fonts.put(nameWithSize, font);
        }
        return font;
    }

    /**
     * Get texture by its name.
     *
     * @param name texture name.
     * @return texture.
     */
    public Texture getTexture(String name) {
        Texture texture = textures.get(name);
        if (texture == null) {
            LOGGER.debug("Loading Texture: {}", name);

            texture = new Texture(settings.getTextureDirectory() + name);
            textures.put(name, texture);
        }
        return texture;
    }
}